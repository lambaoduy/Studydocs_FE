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
        try {
            setResult(
                FollowResult.GetFollowers(
                    getFollowersUseCase.invoke(
                        getFollowersIntent.targetId,
                        getFollowersIntent.targetType
                    )
                )
            )
        } catch (e: Exception) {
            setResult(FollowResult.Error(e.message ?: "Unknown error"))

        }
    }
}