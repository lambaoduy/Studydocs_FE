package com.example.finalexam.handler.auth

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.ForgotPasswordIntent
import com.example.finalexam.result.ForgotPasswordResult
import com.example.finalexam.usecase.auth.ForgotPasswordUseCase

class ForgotPasswordHandler : IntentHandler<ForgotPasswordIntent, ForgotPasswordResult> {
    private val forgotPasswordUseCase = ForgotPasswordUseCase()
    override fun canHandle(intent: ForgotPasswordIntent): Boolean = intent is ForgotPasswordIntent.Submit

    override suspend fun handle(
        intent: ForgotPasswordIntent,
        setResult: (ForgotPasswordResult) -> Unit
    ) {
        setResult(ForgotPasswordResult.Loading)
        when (intent) {
            is ForgotPasswordIntent.Submit -> {
                val result = forgotPasswordUseCase.invoke(intent.email)
                if (result.isSuccess) {
                    setResult(ForgotPasswordResult.Success)
                } else {
                    setResult(ForgotPasswordResult.Error(result.exceptionOrNull()!!))
                }
            }
        }
    }

}