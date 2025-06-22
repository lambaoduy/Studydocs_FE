package com.example.finalexam.handler.upload

import com.example.finalexam.entity.UploadDocument
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.UploadDocumentIntent
import com.example.finalexam.result.UploadDocumentResult
import com.example.finalexam.usecase.upload.UploadDocumentsUseCase

class UploadDocumentsHandler(
    private val uploadDocumentsUseCase: UploadDocumentsUseCase
) : IntentHandler<UploadDocumentIntent, UploadDocumentResult> {
    
    override fun canHandle(intent: UploadDocumentIntent): Boolean = 
        intent is UploadDocumentIntent.Upload

    override suspend fun handle(
        intent: UploadDocumentIntent,
        setResult: (UploadDocumentResult) -> Unit
    ) {
        setResult(UploadDocumentResult.Loading)
//         TODO: Get selected documents and university info from state
        val selectedDocuments = emptyList<UploadDocument>() // Replace with actual selected documents
        val universityId = "" // Replace with actual university ID
        val courseIndex = 0 // Replace with actual course index
        
        setResult(uploadDocumentsUseCase(selectedDocuments, universityId, courseIndex))
    }
} 