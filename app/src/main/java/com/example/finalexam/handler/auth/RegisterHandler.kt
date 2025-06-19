package com.example.finalexam.handler.auth

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.AuthIntent
import com.example.finalexam.result.AuthResult

class RegisterHandler : IntentHandler<AuthIntent, AuthResult> {
    override fun canHandle(intent: AuthIntent): Boolean = intent is AuthIntent.Register

    override suspend fun handle(
        intent: AuthIntent,
        setResult: (AuthResult) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}