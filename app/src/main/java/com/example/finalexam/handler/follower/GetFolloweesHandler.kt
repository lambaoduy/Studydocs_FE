package com.example.finalexam.handler.follower

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.FollowerIntent
import com.example.finalexam.result.FollowerResult
import com.example.finalexam.usecase.follower.GetFolloweesUseCase

class GetFolloweesHandler: IntentHandler<FollowerIntent, FollowerResult> {
    private val getFolloweesUseCase = GetFolloweesUseCase()
    override fun canHandle(intent: FollowerIntent): Boolean =intent is FollowerIntent.GetFollowees

    override suspend fun handle(
        intent: FollowerIntent,
        setResult: (FollowerResult) -> Unit
    ) {
        val getFolloweesIntent = intent as FollowerIntent.GetFollowees
        try {
            setResult(FollowerResult.GetFollowees(getFolloweesUseCase.invoke(getFolloweesIntent.userId)))
        }
        catch (e: Exception) {
            setResult(FollowerResult.Error(e.message ?: "Unknown error"))

        }
    }

}