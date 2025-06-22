package com.example.finalexam.handler.upload

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.UploadDocumentIntent
import com.example.finalexam.result.UploadDocumentResult
import com.example.finalexam.usecase.upload.UploadDocumentsUseCase

class UploadDocumentHandler(
    private val uploadDocumentsUseCase: UploadDocumentsUseCase
) : IntentHandler<UploadDocumentIntent, UploadDocumentResult> {

    override fun canHandle(intent: UploadDocumentIntent): Boolean = intent is UploadDocumentIntent.Upload

    override suspend fun handle(
        intent: UploadDocumentIntent,
        setResult: (UploadDocumentResult) -> Unit
    ) {
        try {
            setResult(UploadDocumentResult.Loading)
            // TODO: Implement actual upload logic
            val uploadedDocuments = emptyList<com.example.finalexam.entity.UploadDocument>()
            setResult(UploadDocumentResult.UploadSuccess(uploadedDocuments))
        } catch (e: Exception) {
            setResult(UploadDocumentResult.Error(e.message ?: "Upload failed"))
        }
    }
} 