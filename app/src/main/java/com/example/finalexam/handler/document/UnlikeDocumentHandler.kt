package com.example.finalexam.handler.document

import com.example.finalexam.data.api.DocumentApi
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.DocumentIntent
import com.example.finalexam.network.RetrofitClient
import com.example.finalexam.result.DocumentResult

class UnlikeDocumentHandler : IntentHandler<DocumentIntent, DocumentResult> {
    override fun canHandle(intent: DocumentIntent): Boolean = intent is DocumentIntent.UnlikeDocument

    override suspend fun handle(intent: DocumentIntent, setResult: (DocumentResult) -> Unit) {
        val unlikeIntent = intent as DocumentIntent.UnlikeDocument
        try {
            val api = RetrofitClient.createApi(DocumentApi::class.java)
            val response = api.unlikeDocument(unlikeIntent.documentId)
            if (response.isSuccessful) {
                setResult(DocumentResult.Unliked) // Kết quả cụ thể cho hành động "bỏ thích"
            } else {
                setResult(DocumentResult.Error("Failed to unlike document: ${response.message()}"))
            }
        } catch (e: Exception) {
            setResult(DocumentResult.Error(e.message ?: "Unknown error"))
        }
    }
}