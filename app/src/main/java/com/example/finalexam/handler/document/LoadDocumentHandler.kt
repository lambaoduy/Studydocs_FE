package com.example.finalexam.handler.document

import com.example.finalexam.data.api.DocumentApi
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.DocumentIntent
import com.example.finalexam.network.RetrofitClient
import com.example.finalexam.result.DocumentResult

class LoadDocumentHandler : IntentHandler<DocumentIntent, DocumentResult> {
    override fun canHandle(intent: DocumentIntent): Boolean = intent is DocumentIntent.LoadDocument

    override suspend fun handle(intent: DocumentIntent, setResult: (DocumentResult) -> Unit) {
        val loadIntent = intent as DocumentIntent.LoadDocument
        setResult(DocumentResult.Loading)
        try {
            val api = RetrofitClient.createApi(DocumentApi::class.java)

            // Gọi API để lấy chi tiết tài liệu
            val detailResponse = api.getDocumentDetail(loadIntent.documentId)
            if (detailResponse.isSuccessful) {
                val document = detailResponse.body()?.data
                if (document != null) {
                    setResult(DocumentResult.Loaded(document)) // Không cần lấy signed URL từ API
                } else {
                    setResult(DocumentResult.Error("Document not found"))
                }
            } else {
                setResult(DocumentResult.Error("Failed to load document: ${detailResponse.message()}"))
            }
        } catch (e: Exception) {
            setResult(DocumentResult.Error("Error loading document: ${e.message ?: "Unknown error"}"))
        }
    }
}