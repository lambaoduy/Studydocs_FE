package com.example.finalexam.handler.follow

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.FollowIntent
import com.example.finalexam.result.FollowResult
import com.example.finalexam.usecase.follow.ToggleNotifyEnableUseCase

class ToggleNotifyEnableHandler : IntentHandler<FollowIntent, FollowResult> {
    private val toggleNotifyEnableUseCase = ToggleNotifyEnableUseCase()
    
    override fun canHandle(intent: FollowIntent): Boolean =
        intent is FollowIntent.ToggleNotifyEnable

    override suspend fun handle(
        intent: FollowIntent,
        setResult: (FollowResult) -> Unit
    ) {
        setResult(FollowResult.Loading)
        val toggleNotifyEnableIntent = intent as FollowIntent.ToggleNotifyEnable
        toggleNotifyEnableUseCase.invoke(
            toggleNotifyEnableIntent.followingId,
            toggleNotifyEnableIntent.notifyEnable
        ).onSuccess {
            setResult(
                FollowResult.ToggleNotifyEnableResult(
                    toggleNotifyEnableIntent.followingId,
                    toggleNotifyEnableIntent.notifyEnable
                )
            )
        }.onFailure { error ->
            setResult(FollowResult.Error(error.message ?: "An unknown error occurred."))
        }
    }
} 