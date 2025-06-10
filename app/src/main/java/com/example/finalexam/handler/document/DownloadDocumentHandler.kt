package com.example.finalexam.handler.document

import com.example.finalexam.data.api.DocumentApi
import com.example.finalexam.network.RetrofitClient
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.DocumentIntent
import com.example.finalexam.result.DocumentResult

class DownloadDocumentHandler : IntentHandler<DocumentIntent, DocumentResult> {
    override fun canHandle(intent: DocumentIntent): Boolean = intent is DocumentIntent.DownloadDocument

    override suspend fun handle(intent: DocumentIntent, setResult: (DocumentResult) -> Unit) {
        val downloadIntent = intent as DocumentIntent.DownloadDocument
        try {
            val api = RetrofitClient.createApi(DocumentApi::class.java)
            val response = api.getDownloadUrl(downloadIntent.documentId)
            if (response.isSuccessful) {
                response.body()?.data?.let { setResult(DocumentResult.DownloadUrl(it)) }
                    ?: setResult(DocumentResult.Error("Download URL not found"))
            } else {
                setResult(DocumentResult.Error("Failed to get download URL: ${response.message()}"))
            }
        } catch (e: Exception) {
            setResult(DocumentResult.Error(e.message ?: "Unknown error"))
        }
    }
}