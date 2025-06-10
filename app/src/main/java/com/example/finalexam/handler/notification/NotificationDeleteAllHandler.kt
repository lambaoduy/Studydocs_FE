package com.example.finalexam.handler.notification

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.NotificationIntent
import com.example.finalexam.result.NotificationResult
import com.example.finalexam.usecase.notification.NotificationDeleteAllUseCase

class NotificationDeleteAllHandler : IntentHandler<NotificationIntent, NotificationResult> {
    private val notificationDeleteAllUseCase = NotificationDeleteAllUseCase()
    override fun canHandle(intent: NotificationIntent): Boolean =
        intent is NotificationIntent.DeleteAll

    override suspend fun handle(
        intent: NotificationIntent,
        setResult: (NotificationResult) -> Unit
    ) {
        setResult(NotificationResult.Loading)
        try {
            val notificationDeleteAllIntent = intent as NotificationIntent.DeleteAll
            notificationDeleteAllUseCase.invoke(notificationDeleteAllIntent.userId)
            setResult(NotificationResult.DeleteAll)
        } catch (e: Exception) {
            setResult(NotificationResult.Error(e.message ?: "Unknown error"))
        }
    }
}