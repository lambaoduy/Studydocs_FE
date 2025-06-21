package com.example.finalexam.handler.follow

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.FollowIntent
import com.example.finalexam.result.FollowResult
import com.example.finalexam.usecase.follow.GetFollowingsUseCase

class GetFollowingsHandler : IntentHandler<FollowIntent, FollowResult> {
    private val getFollowingsUseCase = GetFollowingsUseCase()
    
    override fun canHandle(intent: FollowIntent): Boolean = intent is FollowIntent.GetFollowings

    override suspend fun handle(
        intent: FollowIntent,
        setResult: (FollowResult) -> Unit
    ) {
        setResult(FollowResult.Loading)
        getFollowingsUseCase.invoke()
            .onSuccess { followings ->
                setResult(FollowResult.GetFollowings(followings))
            }
            .onFailure { error ->
                setResult(FollowResult.Error(error.message ?: "An unknown error occurred."))
            }
    }
} 