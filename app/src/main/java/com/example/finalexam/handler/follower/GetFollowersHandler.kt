package com.example.finalexam.handler.follower

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.FollowerIntent
import com.example.finalexam.result.FollowerResult
import com.example.finalexam.usecase.follower.GetFollowersUseCase

class GetFollowersHandler : IntentHandler<FollowerIntent, FollowerResult> {
    private val getFollowersUseCase = GetFollowersUseCase()
    override fun canHandle(intent: FollowerIntent): Boolean = intent is FollowerIntent.GetFollowers


    override suspend fun handle(
        intent: FollowerIntent,
        setResult: (FollowerResult) -> Unit
    ) {
        val getFollowersIntent = intent as FollowerIntent.GetFollowers
        try {
            setResult(FollowerResult.GetFollowers(getFollowersUseCase.invoke(getFollowersIntent.userId)))
        } catch (e: Exception) {
            setResult(FollowerResult.Error(e.message ?: "Unknown error"))

        }
    }
}