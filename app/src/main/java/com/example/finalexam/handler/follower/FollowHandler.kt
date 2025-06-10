package com.example.finalexam.handler.follower

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.FollowerIntent
import com.example.finalexam.result.FollowerResult
import com.example.finalexam.usecase.follower.FollowActionUseCase

class FollowHandler : IntentHandler<FollowerIntent, FollowerResult> {
    private val followActionUseCase = FollowActionUseCase()

    override fun canHandle(intent: FollowerIntent): Boolean = intent is FollowerIntent.Follow

    override suspend fun handle(
        intent: FollowerIntent,
        setResult: (FollowerResult) -> Unit
    ) {
        val followIntent = intent as FollowerIntent.Follow
        try {
            followActionUseCase.invoke(followIntent.followerId, followIntent.followeeId, false)
            setResult(FollowerResult.FollowActionResult(true))
        } catch (e: Exception) {
            setResult(FollowerResult.Error(e.message ?: "Unknown error"))
        }
    }
}