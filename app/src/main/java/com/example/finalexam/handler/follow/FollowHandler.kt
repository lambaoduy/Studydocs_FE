package com.example.finalexam.handler.follow

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.FollowIntent
import com.example.finalexam.result.FollowResult
import com.example.finalexam.usecase.follow.FollowUseCase

class FollowHandler : IntentHandler<FollowIntent, FollowResult> {
    private val followUseCase = FollowUseCase()

    override fun canHandle(intent: FollowIntent): Boolean = intent is FollowIntent.Follow

    override suspend fun handle(
        intent: FollowIntent,
        setResult: (FollowResult) -> Unit
    ) {
        setResult(FollowResult.Loading)
        val followIntent = intent as FollowIntent.Follow
        followUseCase.invoke(followIntent.targetId, followIntent.targetType)
            .onSuccess {
                setResult(FollowResult.FollowActionResult)
            }
            .onFailure { error ->
                setResult(FollowResult.Error(error.message ?: "An unknown error occurred."))
            }
    }
} 