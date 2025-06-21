package com.example.finalexam.handler.follow

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.FollowIntent
import com.example.finalexam.result.FollowResult
import com.example.finalexam.usecase.follow.GetFollowersUseCase

class GetFollowersHandler : IntentHandler<FollowIntent, FollowResult> {
    private val getFollowersUseCase = GetFollowersUseCase()
    
    override fun canHandle(intent: FollowIntent): Boolean = intent is FollowIntent.GetFollowers

    override suspend fun handle(
        intent: FollowIntent,
        setResult: (FollowResult) -> Unit
    ) {
        setResult(FollowResult.Loading)
        val getFollowersIntent = intent as FollowIntent.GetFollowers
        getFollowersUseCase.invoke(
            getFollowersIntent.targetId,
            getFollowersIntent.targetType
        ).onSuccess { followers ->
            setResult(FollowResult.GetFollowers(followers))
        }.onFailure { error ->
            setResult(FollowResult.Error(error.message ?: "An unknown error occurred."))
        }
    }
} 