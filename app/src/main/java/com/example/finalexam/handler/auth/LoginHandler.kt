package com.example.finalexam.handler.auth

import com.example.finalexam.intent.LoginIntent
import com.example.finalexam.result.LoginResult
import com.example.finalexam.usecase.auth.LoginUseCase
import com.example.finalexam.handler.IntentHandler

class LoginHandler(private val useCase: LoginUseCase) : IntentHandler<LoginIntent, LoginResult> {
    override fun canHandle(intent: LoginIntent): Boolean = true

    override suspend fun handle(intent: LoginIntent, setResult: (LoginResult) -> Unit) {
        when (intent) {
            is LoginIntent.Login -> {
                setResult(LoginResult.Loading)
                val result = useCase.login(intent.email, intent.password)
                setResult(result.fold(
                    onSuccess = { LoginResult.Success(it) },
                    onFailure = { LoginResult.Error(it) }
                ))
            }
            is LoginIntent.ClearError -> setResult(LoginResult.Error(Exception("")))
        }
    }
}

