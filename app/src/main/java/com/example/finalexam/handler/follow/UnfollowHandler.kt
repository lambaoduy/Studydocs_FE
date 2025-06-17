package com.example.finalexam.handler.follow

import com.example.finalexam.data.response.BaseResponse
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.FollowIntent
import com.example.finalexam.result.FollowResult
import com.example.finalexam.usecase.follow.UnfollowUseCase
import com.google.gson.Gson
import retrofit2.HttpException

class UnfollowHandler : IntentHandler<FollowIntent, FollowResult> {
    private val unfollowUseCase = UnfollowUseCase()


    override fun canHandle(intent: FollowIntent): Boolean = intent is FollowIntent.Unfollow

    override suspend fun handle(
        intent: FollowIntent,
        setResult: (FollowResult) -> Unit
    ) {
        setResult(FollowResult.Loading)
        val unfollowIntent = intent as FollowIntent.Unfollow
        try {
            var result = unfollowUseCase.invoke(unfollowIntent.followingId)
            setResult(FollowResult.UnFollowActionResult(result))
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