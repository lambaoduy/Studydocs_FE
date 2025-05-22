package com.example.finalexam.handler.document

import com.example.finalexam.api.RetrofitClient
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.DocumentIntent
import com.example.finalexam.result.DocumentResult

class UnbookmarkDocumentHandler : IntentHandler<DocumentIntent, DocumentResult> {
    override fun canHandle(intent: DocumentIntent): Boolean = intent is DocumentIntent.UnbookmarkDocument

    override suspend fun handle(intent: DocumentIntent, setResult: (DocumentResult) -> Unit) {
        val unbookmarkIntent = intent as DocumentIntent.UnbookmarkDocument
        try {
            val response = RetrofitClient.api.unbookmarkDocument(unbookmarkIntent.documentId, unbookmarkIntent.userId)
            if (response.isSuccessful) {
                setResult(DocumentResult.ActionSuccess)
            } else {
                setResult(DocumentResult.Error("Failed to unbookmark document: ${response.message()}"))
            }
        } catch (e: Exception) {
            setResult(DocumentResult.Error(e.message ?: "Unknown error"))
        }
    }
}