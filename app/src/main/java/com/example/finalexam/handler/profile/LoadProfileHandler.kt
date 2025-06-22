package com.example.finalexam.handler.profile

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.ProfileIntent
import com.example.finalexam.result.ProfileResult
import com.example.finalexam.usecase.profile.LoadProfileUseCase

class LoadProfileHandler : IntentHandler<ProfileIntent, ProfileResult> {
    private val loadProfileUseCase = LoadProfileUseCase()

    override fun canHandle(intent: ProfileIntent): Boolean = intent is ProfileResult.Loaded

    override suspend fun handle(
        intent: ProfileIntent,
        setResult: (ProfileResult) -> Unit
    ) {
        setResult(ProfileResult.Loading)
        loadProfileUseCase.invoke()
            .onSuccess {
                setResult(ProfileResult.Loaded(it))
            }
            .onFailure { error ->
                setResult(ProfileResult.Error(error.message ?: "Unknown error"))
            }
    }
}