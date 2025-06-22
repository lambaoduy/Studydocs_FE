package com.example.finalexam.handler.document

import android.util.Log
import com.example.finalexam.data.api.DocumentApi
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.DocumentIntent
import com.example.finalexam.network.RetrofitClient
import com.example.finalexam.result.DocumentResult
import kotlinx.coroutines.tasks.await

class LikeDocumentHandler : IntentHandler<DocumentIntent, DocumentResult> {
    private val api = RetrofitClient.createApi(DocumentApi::class.java)

    override fun canHandle(intent: DocumentIntent): Boolean = intent is DocumentIntent.LikeDocument

    override suspend fun handle(intent: DocumentIntent, setResult: (DocumentResult) -> Unit) {
        val likeIntent = intent as DocumentIntent.LikeDocument
        try {
            setResult(DocumentResult.Loading) // Đặt isLoadingLike = true qua reducer
            Log.d("LikeDocumentHandler", "Liking document: ${likeIntent.documentId}")
            val response = api.likeDocument(likeIntent.documentId)
            if (response.isSuccessful) {
                Log.d("LikeDocumentHandler", "Document liked successfully")
                setResult(DocumentResult.Liked())
            } else {
                setResult(DocumentResult.Error("Failed to like document: ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("LikeDocumentHandler", "Error liking document: ${e.message}", e)
            setResult(DocumentResult.Error("Lỗi khi like tài liệu: ${e.message}"))
        }
    }
}