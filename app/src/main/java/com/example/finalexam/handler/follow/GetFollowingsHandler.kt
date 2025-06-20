package com.example.finalexam.handler.follow


import com.example.finalexam.data.response.BaseResponse
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.FollowIntent
import com.example.finalexam.result.FollowResult
import com.example.finalexam.usecase.follow.GetFollowingsUseCase
import com.google.gson.Gson
import retrofit2.HttpException

class GetFollowingsHandler : IntentHandler<FollowIntent, FollowResult> {
    private val getFollowingsUseCase = GetFollowingsUseCase()
    override fun canHandle(intent: FollowIntent): Boolean = intent is FollowIntent.GetFollowings

    override suspend fun handle(
        intent: FollowIntent,
        setResult: (FollowResult) -> Unit
    ) {
        setResult(FollowResult.Loading)
        try {
            var result = getFollowingsUseCase.invoke()
            setResult(FollowResult.GetFollowings(result))
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