package com.example.finalexam.handler.document

import com.example.finalexam.api.RetrofitClient
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.DocumentIntent
import com.example.finalexam.result.DocumentResult

class BookmarkDocumentHandler : IntentHandler<DocumentIntent, DocumentResult> {
    override fun canHandle(intent: DocumentIntent): Boolean = intent is DocumentIntent.BookmarkDocument

    override suspend fun handle(intent: DocumentIntent, setResult: (DocumentResult) -> Unit) {
        val bookmarkIntent = intent as DocumentIntent.BookmarkDocument
        try {
            val response = RetrofitClient.api.bookmarkDocument(bookmarkIntent.documentId, bookmarkIntent.userId)
            if (response.isSuccessful) {
                setResult(DocumentResult.ActionSuccess)
            } else {
                setResult(DocumentResult.Error("Failed to bookmark document: ${response.message()}"))
            }
        } catch (e: Exception) {
            setResult(DocumentResult.Error(e.message ?: "Unknown error"))
        }
    }
}