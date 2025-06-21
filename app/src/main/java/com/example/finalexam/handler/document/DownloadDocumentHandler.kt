package com.example.finalexam.handler.document

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.DocumentIntent
import com.example.finalexam.result.DocumentResult

class DownloadDocumentHandler : IntentHandler<DocumentIntent, DocumentResult> {
    override fun canHandle(intent: DocumentIntent): Boolean = intent is DocumentIntent.DownloadDocument

    override suspend fun handle(intent: DocumentIntent, setResult: (DocumentResult) -> Unit) {
        val downloadIntent = intent as DocumentIntent.DownloadDocument
        try {
            // Không gọi API, sử dụng URL trực tiếp từ intent
            setResult(DocumentResult.DownloadUrl(downloadIntent.url))
        } catch (e: Exception) {
            setResult(DocumentResult.Error("Lỗi xử lý URL tải xuống: ${e.message}"))
        }
    }
}