package com.example.finalexam.handler.follower

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.FollowerIntent
import com.example.finalexam.result.FollowerResult
import com.example.finalexam.usecase.follower.FollowActionUseCase

class UnfollowHandler : IntentHandler<FollowerIntent, FollowerResult> {
    private val followActionUseCase = FollowActionUseCase()


    override fun canHandle(intent: FollowerIntent): Boolean = intent is FollowerIntent.Unfollow

    override suspend fun handle(
        intent: FollowerIntent,
        setResult: (FollowerResult) -> Unit
    ) {
        val unfollowIntent = intent as FollowerIntent.Unfollow
        try {
            followActionUseCase.invoke(unfollowIntent.followerId, unfollowIntent.followeeId, true)
            setResult(FollowerResult.FollowActionResult(false))
        }
        catch (e: Exception) {
            setResult(FollowerResult.Error(e.message ?: "Unknown error"))
        }
    }
}