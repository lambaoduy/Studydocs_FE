package com.example.finalexam.handler.auth

import com.example.finalexam.intent.RegisterIntent
import com.example.finalexam.result.RegisterResult
import com.example.finalexam.usecase.auth.RegisterUseCase
import com.example.finalexam.handler.IntentHandler

class RegisterHandler(private val useCase: RegisterUseCase) : IntentHandler<RegisterIntent, RegisterResult> {
    override fun canHandle(intent: RegisterIntent): Boolean = true

    override suspend fun handle(intent: RegisterIntent, setResult: (RegisterResult) -> Unit) {
        when (intent) {
            is RegisterIntent.Register -> {
                setResult(RegisterResult.Loading)
                val result = useCase.register(intent.username, intent.email, intent.password)
                setResult(result.fold(
                    onSuccess = { RegisterResult.Success(it) },
                    onFailure = { RegisterResult.Error(it) }
                ))
            }
            is RegisterIntent.ClearError -> setResult(RegisterResult.Error(Exception("")))
        }
    }
}

