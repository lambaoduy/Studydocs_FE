package com.example.finalexam.handler.follow

import com.example.finalexam.data.response.BaseResponse
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.FollowIntent
import com.example.finalexam.result.FollowResult
import com.example.finalexam.usecase.follow.ToggleNotifyEnableUseCase
import com.google.gson.Gson
import retrofit2.HttpException

class ToggleNotifyHandler : IntentHandler<FollowIntent, FollowResult> {
    private val toggleNotifyEnableHandler = ToggleNotifyEnableUseCase()
    override fun canHandle(intent: FollowIntent): Boolean =
        intent is FollowIntent.ToggleNotifyEnable

    override suspend fun handle(
        intent: FollowIntent,
        setResult: (FollowResult) -> Unit
    ) {
        setResult(FollowResult.Loading)
        val toggleNotifyEnableIntent = intent as FollowIntent.ToggleNotifyEnable
        try {
            toggleNotifyEnableHandler.invoke(
                toggleNotifyEnableIntent.followingId,
                toggleNotifyEnableIntent.notifyEnable
            )
            setResult(
                FollowResult.ToggleNotifyEnableResult(
                    toggleNotifyEnableIntent.followingId,
                    toggleNotifyEnableIntent.notifyEnable
                )
            )
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