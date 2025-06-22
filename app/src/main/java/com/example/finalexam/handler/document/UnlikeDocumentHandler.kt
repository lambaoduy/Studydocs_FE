package com.example.finalexam.handler.document

import android.util.Log
import com.example.finalexam.data.api.DocumentApi
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.DocumentIntent
import com.example.finalexam.network.RetrofitClient
import com.example.finalexam.result.DocumentResult

class UnlikeDocumentHandler : IntentHandler<DocumentIntent, DocumentResult> {
    private val api = RetrofitClient.createApi(DocumentApi::class.java)

    override fun canHandle(intent: DocumentIntent): Boolean = intent is DocumentIntent.UnlikeDocument

    override suspend fun handle(intent: DocumentIntent, setResult: (DocumentResult) -> Unit) {
        val unlikeIntent = intent as DocumentIntent.UnlikeDocument // Sửa ép kiểu
        try {
            setResult(DocumentResult.Loading)
            Log.d("UnlikeDocumentHandler", "Unliking document: ${unlikeIntent.documentId}")
            val response = api.unlikeDocument(unlikeIntent.documentId) // Gọi API unlike
            if (response.isSuccessful) {
                Log.d("UnlikeDocumentHandler", "Document unliked successfully")
                setResult(DocumentResult.Unliked())
            } else {
                setResult(DocumentResult.Error("Failed to unlike document: ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("UnlikeDocumentHandler", "Error unliking document: ${e.message}", e)
            setResult(DocumentResult.Error("Lỗi khi unlike tài liệu: ${e.message}"))
        }
    }
}