package com.example.finalexam.handler.document

import com.example.finalexam.api.RetrofitClient
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.DocumentIntent
import com.example.finalexam.result.DocumentResult

class LikeDocumentHandler : IntentHandler<DocumentIntent, DocumentResult> {
    override fun canHandle(intent: DocumentIntent): Boolean = intent is DocumentIntent.LikeDocument

    override suspend fun handle(intent: DocumentIntent, setResult: (DocumentResult) -> Unit) {
        val likeIntent = intent as DocumentIntent.LikeDocument
        try {
            val response = RetrofitClient.api.likeDocument(likeIntent.documentId, likeIntent.userId)
            if (response.isSuccessful) {
                setResult(DocumentResult.ActionSuccess)
            } else {
                setResult(DocumentResult.Error("Failed to like document: ${response.message()}"))
            }
        } catch (e: Exception) {
            setResult(DocumentResult.Error(e.message ?: "Unknown error"))
        }
    }
}