package com.example.finalexam.handler.notification

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.NotificationIntent
import com.example.finalexam.result.NotificationResult
import com.example.finalexam.usecase.notification.NotificationMarkAsReadAllUseCase

class NotificationMarkAsReadAllHandler : IntentHandler<NotificationIntent, NotificationResult> {
    private val notificationMarkAsReadAllUseCase = NotificationMarkAsReadAllUseCase()
    override fun canHandle(intent: NotificationIntent): Boolean =
        intent is NotificationIntent.MarkAsReadAll

    override suspend fun handle(
        intent: NotificationIntent,
        setResult: (NotificationResult) -> Unit
    ) {
        setResult(NotificationResult.Loading)
        try {
            notificationMarkAsReadAllUseCase.invoke()
            setResult(NotificationResult.MarkAsReadAll)
        } catch (e: Exception) {
            setResult(NotificationResult.Error(e.message ?: "Unknown error"))
        }
    }
}