package com.example.finalexam.handler.document

import com.example.finalexam.data.api.DocumentApi
import com.example.finalexam.network.RetrofitClient
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.DocumentIntent
import com.example.finalexam.result.DocumentResult

class LoadDocumentHandler : IntentHandler<DocumentIntent, DocumentResult> {
    override fun canHandle(intent: DocumentIntent): Boolean = intent is DocumentIntent.LoadDocument

    override suspend fun handle(intent: DocumentIntent, setResult: (DocumentResult) -> Unit) {
        val loadIntent = intent as DocumentIntent.LoadDocument
        setResult(DocumentResult.Loading)
        try {
            val api = RetrofitClient.createApi(DocumentApi::class.java)
            val response = api.getDocumentDetail(loadIntent.documentId)
            if (response.isSuccessful) {
                response.body()?.data?.let { setResult(DocumentResult.Loaded(it)) }
                    ?: setResult(DocumentResult.Error("Document not found"))
            } else {
                setResult(DocumentResult.Error("Failed to load document: ${response.message()}"))
            }
        } catch (e: Exception) {
            setResult(DocumentResult.Error(e.message ?: "Unknown error"))
        }
    }
}