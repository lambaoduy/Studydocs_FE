package com.example.finalexam.handler.notification

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.NotificationIntent
import com.example.finalexam.result.NotificationResult

class CloseNotificationHandler : IntentHandler<NotificationIntent, NotificationResult> {
    override fun canHandle(intent: NotificationIntent): Boolean = intent is NotificationIntent.Close

    override suspend fun handle(
        intent: NotificationIntent,
        setResult: (NotificationResult) -> Unit
    ) {
        setResult(NotificationResult.Close)
    }
}