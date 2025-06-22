package com.example.finalexam.handler.profile

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.ProfileIntent
import com.example.finalexam.result.ProfileResult
import com.example.finalexam.usecase.profile.LogoutUseCase

class LogoutHandler : IntentHandler<ProfileIntent, ProfileResult> {
    private val logoutUseCase = LogoutUseCase()

    override fun canHandle(intent: ProfileIntent): Boolean = intent is ProfileResult.Logout

    override suspend fun handle(
        intent: ProfileIntent,
        setResult: (ProfileResult) -> Unit
    ) {
        setResult(ProfileResult.Loading)
        logoutUseCase.invoke()
            .onSuccess {
                setResult(ProfileResult.Success)
            }
            .onFailure { error ->
                setResult(ProfileResult.Error(error.message ?: "Unknown error"))
            }
    }
}