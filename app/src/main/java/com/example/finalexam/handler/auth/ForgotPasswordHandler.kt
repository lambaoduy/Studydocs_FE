package com.example.finalexam.handler.auth

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.AuthIntent
import com.example.finalexam.result.AuthResult
import com.example.finalexam.usecase.auth.ForgotPasswordUseCase

class ForgotPasswordHandler : IntentHandler<AuthIntent, AuthResult> {
    private val forgotPasswordUseCase = ForgotPasswordUseCase()
    override fun canHandle(intent: AuthIntent): Boolean = intent is AuthIntent.ForgotPassword

    override suspend fun handle(
        intent: AuthIntent,
        setResult: (AuthResult) -> Unit
    ) {
        setResult(AuthResult.Loading)
        val forgotPasswordIntent = intent as AuthIntent.ForgotPassword
        val result = forgotPasswordUseCase.invoke(forgotPasswordIntent.email)
        if (result.isSuccess) {
            setResult(AuthResult.Success)
        } else {
            setResult(AuthResult.Error(result.exceptionOrNull()!!))
        }
    }

}