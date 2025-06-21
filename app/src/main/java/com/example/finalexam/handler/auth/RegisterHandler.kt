package com.example.finalexam.handler.auth

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.AuthIntent
import com.example.finalexam.result.AuthResult
import com.example.finalexam.usecase.auth.RegisterUseCase

class RegisterHandler : IntentHandler<AuthIntent, AuthResult> {
    private val registerUseCase = RegisterUseCase()
    override fun canHandle(intent: AuthIntent): Boolean = intent is AuthIntent.Register

    override suspend fun handle(
        intent: AuthIntent,
        setResult: (AuthResult) -> Unit
    ) {
        setResult(AuthResult.Loading)
        val registerIntent = intent as AuthIntent.Register
        registerUseCase.invoke(
            registerIntent.fullName,
            registerIntent.email,
            registerIntent.password
        ).onSuccess {
            setResult(AuthResult.Success)
        }.onFailure { error ->
            setResult(AuthResult.Error(error.message ?: "Unknown error"))
        }
    }
}