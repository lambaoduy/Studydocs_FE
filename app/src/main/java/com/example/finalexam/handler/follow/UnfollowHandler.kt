package com.example.finalexam.handler.follow

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.FollowIntent
import com.example.finalexam.result.FollowResult
import com.example.finalexam.usecase.follow.UnfollowUseCase

class UnfollowHandler : IntentHandler<FollowIntent, FollowResult> {
    private val unfollowUseCase = UnfollowUseCase()

    override fun canHandle(intent: FollowIntent): Boolean = intent is FollowIntent.Unfollow

    override suspend fun handle(
        intent: FollowIntent,
        setResult: (FollowResult) -> Unit
    ) {
        setResult(FollowResult.Loading)
        val unfollowIntent = intent as FollowIntent.Unfollow
        unfollowUseCase.invoke(unfollowIntent.followingId)
            .onSuccess { result ->
                setResult(FollowResult.UnFollowActionResult(result))
            }
            .onFailure { error ->
                setResult(FollowResult.Error(error.message ?: "An unknown error occurred."))
            }
    }
} 