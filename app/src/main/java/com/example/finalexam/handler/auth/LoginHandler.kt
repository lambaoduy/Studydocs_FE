package com.example.finalexam.handler.auth

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.AuthIntent
import com.example.finalexam.result.AuthResult
import com.example.finalexam.usecase.auth.LoginUseCase

class LoginHandler : IntentHandler<AuthIntent, AuthResult> {
    private val loginUseCase = LoginUseCase()
    override fun canHandle(intent: AuthIntent): Boolean = intent is AuthIntent.Login

    override suspend fun handle(
        intent: AuthIntent,
        setResult: (AuthResult) -> Unit
    ) {
        setResult(AuthResult.Loading)
        val loginIntent = intent as AuthIntent.Login
        loginUseCase.invoke(loginIntent.email, loginIntent.password)
            .onSuccess {
                setResult(AuthResult.Success)
            }
            .onFailure { error ->
                setResult(AuthResult.Error(error.message ?: "Unknown error"))
            }
    }
}