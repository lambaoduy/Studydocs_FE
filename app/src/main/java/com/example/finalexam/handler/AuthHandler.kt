package com.example.finalexam.handler

import com.example.finalexam.intent.AuthIntent
import com.example.finalexam.result.AuthResult
import com.example.finalexam.usecase.AuthUseCase

// thiện làm: Handler cho Auth (Login/Register)
class AuthHandler(private val useCase: AuthUseCase) : IntentHandler<AuthIntent, AuthResult> {
    override fun canHandle(intent: AuthIntent): Boolean = true

    override suspend fun handle(intent: AuthIntent, setResult: (AuthResult) -> Unit) {
        when (intent) {
            is AuthIntent.Login -> {
                setResult(AuthResult.Loading)
                val result = useCase.login(intent.email, intent.password)
                setResult(result.fold(
                    onSuccess = { AuthResult.Success },
                    onFailure = { AuthResult.Error(it) }
                ))
            }
            is AuthIntent.Register -> {
                setResult(AuthResult.Loading)
                val result = useCase.register(intent.username, intent.email, intent.password)
                setResult(result.fold(
                    onSuccess = { AuthResult.Success },
                    onFailure = { AuthResult.Error(it) }
                ))
            }
            is AuthIntent.ClearError -> setResult(AuthResult.Error(Exception("")))
        }
    }
} 