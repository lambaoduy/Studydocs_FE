package com.example.finalexam.handler.notification

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.NotificationIntent
import com.example.finalexam.result.NotificationResult
import com.example.finalexam.usecase.notification.NotificationMarkAsReadUseCase

class NotificationMarkAsReadHandler : IntentHandler<NotificationIntent, NotificationResult> {
    private val notificationMarkAsReadUseCase = NotificationMarkAsReadUseCase()
    override fun canHandle(intent: NotificationIntent): Boolean =
        intent is NotificationIntent.MarkAsRead

    override suspend fun handle(
        intent: NotificationIntent,
        setResult: (NotificationResult) -> Unit
    ) {
        setResult(NotificationResult.Loading)
        try {
            val notificationMarkAsReadIntent = intent as NotificationIntent.MarkAsRead
            notificationMarkAsReadUseCase.invoke(
                notificationMarkAsReadIntent.notificationId
            )
            setResult(NotificationResult.MarkAsRead(notificationMarkAsReadIntent.notificationId))
        } catch (e: Exception) {
            setResult(NotificationResult.Error(e.message ?: "Unknown error"))
        }
    }
}