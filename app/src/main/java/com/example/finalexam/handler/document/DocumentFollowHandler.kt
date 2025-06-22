package com.example.finalexam.handler.document

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.DocumentIntent
import com.example.finalexam.result.DocumentResult
import com.example.finalexam.usecase.follow.FollowUseCase

class DocumentFollowHandler : IntentHandler<DocumentIntent, DocumentResult> {
    private val followUseCase = FollowUseCase()
    override fun canHandle(intent: DocumentIntent): Boolean = intent is DocumentIntent.Follow

    override suspend fun handle(intent: DocumentIntent, setResult: (DocumentResult) -> Unit) {
        try {
            val followIntent = intent as DocumentIntent.Follow
            followUseCase.invoke(followIntent.targetId, followIntent.type)
            setResult(DocumentResult.Followed)
        } catch (e: Exception) {
            setResult(DocumentResult.Error("Theo dõi thất bại: ${e.message}"))
        }
    }
}