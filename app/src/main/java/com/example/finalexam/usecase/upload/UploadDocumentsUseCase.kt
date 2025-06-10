package com.example.finalexam.usecase.upload

import com.example.finalexam.entity.UploadDocument
import com.example.finalexam.result.UploadDocumentResult

class UploadDocumentsUseCase {
    suspend operator fun invoke(
        documents: List<UploadDocument>,
        universityId: String,
        courseIndex: Int
    ): UploadDocumentResult {
        return try {
            // TODO: Replace with actual API call
            // val response = apiService.uploadDocuments(
            //     documents = documents,
            //     universityId = universityId,
            //     courseIndex = courseIndex
            // )
            // UploadDocumentResult.UploadSuccess(response.uploadedDocuments)
            
            // Temporary mock success
            UploadDocumentResult.UploadSuccess(documents)
        } catch (e: Exception) {
            UploadDocumentResult.Error(e.message ?: "Failed to upload documents")
        }
    }
} 