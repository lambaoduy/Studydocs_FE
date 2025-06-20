package com.example.finalexam.handler.follow

import android.util.Log
import com.example.finalexam.data.response.BaseResponse
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.FollowIntent
import com.example.finalexam.result.FollowResult
import com.example.finalexam.usecase.follow.GetFollowersUseCase
import com.google.gson.Gson
import retrofit2.HttpException

class GetFollowersHandler : IntentHandler<FollowIntent, FollowResult> {
    private val getFollowersUseCase = GetFollowersUseCase()
    override fun canHandle(intent: FollowIntent): Boolean = intent is FollowIntent.GetFollowers


    override suspend fun handle(
        intent: FollowIntent,
        setResult: (FollowResult) -> Unit
    ) {
        setResult(FollowResult.Loading)
        val getFollowersIntent = intent as FollowIntent.GetFollowers
        try {
            var result = getFollowersUseCase.invoke(
                getFollowersIntent.targetId,
                getFollowersIntent.targetType
            )
            setResult(FollowResult.GetFollowers(result))
            Log.e("GetFollowersHandler", "Get followings success ${result.size}")
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val message = try {
                val errorResponse = Gson().fromJson(errorBody, BaseResponse::class.java)
                errorResponse?.message ?: "Unknown error"
            } catch (ex: Exception) {
                "Unknown error"
            }
            setResult(FollowResult.Error(message))
        }
    }
}