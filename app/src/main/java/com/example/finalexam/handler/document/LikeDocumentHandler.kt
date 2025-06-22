package com.example.finalexam.handler.document

import com.example.finalexam.data.api.DocumentApi
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.DocumentIntent
import com.example.finalexam.network.RetrofitClient
import com.example.finalexam.result.DocumentResult

class LikeDocumentHandler : IntentHandler<DocumentIntent, DocumentResult> {
    override fun canHandle(intent: DocumentIntent): Boolean = intent is DocumentIntent.LikeDocument

    override suspend fun handle(intent: DocumentIntent, setResult: (DocumentResult) -> Unit) {
        val likeIntent = intent as DocumentIntent.LikeDocument
        try {
            val api = RetrofitClient.createApi(DocumentApi::class.java)
            val response = api.likeDocument(likeIntent.documentId)
            if (response.isSuccessful) {
                setResult(DocumentResult.Liked) // Kết quả cụ thể cho hành động "thích"
            } else {
                setResult(DocumentResult.Error("Failed to like document: ${response.message()}"))
            }
        } catch (e: Exception) {
            setResult(DocumentResult.Error(e.message ?: "Unknown error"))
        }
    }
}