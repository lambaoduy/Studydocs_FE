package com.example.finalexam.handler.auth

import android.content.Context
import com.example.finalexam.intent.LogoutIntent
import com.example.finalexam.result.LogoutResult
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.usecase.auth.LogoutUseCase

class LogoutHandler(private val context: Context) : IntentHandler<LogoutIntent, LogoutResult> {
    private val useCase = LogoutUseCase(context)
    override fun canHandle(intent: LogoutIntent): Boolean = true

    override suspend fun handle(intent: LogoutIntent, setResult: (LogoutResult) -> Unit) {
        when (intent) {
            is LogoutIntent.Logout -> {
                setResult(LogoutResult.Loading)
                val result = useCase.logout()
                setResult(result.fold(
                    onSuccess = { LogoutResult.LoggedOut },
                    onFailure = { LogoutResult.Error(it) }
                ))
            }
        }
    }
}

