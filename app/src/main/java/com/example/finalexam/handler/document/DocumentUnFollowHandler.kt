package com.example.finalexam.handler.document

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.DocumentIntent
import com.example.finalexam.result.DocumentResult
import com.example.finalexam.usecase.follow.UnfollowUseCase

class DocumentUnFollowHandler : IntentHandler<DocumentIntent, DocumentResult> {
    private val unfollowUseCase = UnfollowUseCase()
    override fun canHandle(intent: DocumentIntent): Boolean = intent is DocumentIntent.UnFollow

    override suspend fun handle(intent: DocumentIntent, setResult: (DocumentResult) -> Unit) {
        try {
            val unFollowIntent = intent as DocumentIntent.UnFollow
            unfollowUseCase.invokeByTarget(unFollowIntent.targetId, unFollowIntent.type)
            setResult(DocumentResult.Followed)
        } catch (e: Exception) {
            setResult(DocumentResult.Error("Theo dõi thất bại: ${e.message}"))
        }
    }
}