package com.example.finalexam.handler.follow

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.FollowIntent
import com.example.finalexam.result.FollowResult
import com.example.finalexam.usecase.follow.ToggleNotifyEnableUseCase

class ToggleNotifyEnableHandler : IntentHandler<FollowIntent, FollowResult> {
    private val toggleNotifyEnableHandler = ToggleNotifyEnableUseCase()
    override fun canHandle(intent: FollowIntent): Boolean =
        intent is FollowIntent.ToggleNotifyEnable

    override suspend fun handle(
        intent: FollowIntent,
        setResult: (FollowResult) -> Unit
    ) {
        setResult(FollowResult.Loading)
        try {
            val toggleNotifyEnableIntent = intent as FollowIntent.ToggleNotifyEnable
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
        } catch (e: Exception) {
            setResult(FollowResult.Error(e.message ?: "Unknown error"))
        }

    }
}