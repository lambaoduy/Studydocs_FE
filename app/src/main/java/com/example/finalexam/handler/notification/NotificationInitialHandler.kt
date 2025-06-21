package com.example.finalexam.handler.notification

import com.example.finalexam.data.response.BaseResponse
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.NotificationIntent
import com.example.finalexam.result.NotificationResult
import com.example.finalexam.usecase.notification.NotificationInitialUseCase
import com.google.gson.Gson
import retrofit2.HttpException

class NotificationInitialHandler : IntentHandler<NotificationIntent, NotificationResult> {
    private val notificationInitialUseCase = NotificationInitialUseCase()
    override fun canHandle(intent: NotificationIntent): Boolean =
        intent is NotificationIntent.Initial || intent is NotificationIntent.Refresh

    override suspend fun handle(
        intent: NotificationIntent,
        setResult: (NotificationResult) -> Unit
    ) {
        setResult(NotificationResult.Loading)
        notificationInitialUseCase.invoke()
            .onSuccess { notifications ->
                setResult(NotificationResult.GetNotifications(notifications))
            }
            .onFailure { error ->
                setResult(NotificationResult.Error(error.message ?: "An unknown error occurred."))
            }
    }
}