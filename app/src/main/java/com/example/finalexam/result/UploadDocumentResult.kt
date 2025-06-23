package com.example.finalexam.result

import com.example.finalexam.entity.Document
import com.example.finalexam.entity.UploadDocument


sealed class UploadDocumentResult {
    data object Loading : UploadDocumentResult()
    data class DocumentSelected(val document: UploadDocument?) : UploadDocumentResult()
    data class UploadSuccess(val uploadedDocuments: List<Document>) : UploadDocumentResult()
    data class Error(val message: String) : UploadDocumentResult()
    data class SetTitle(val title: String) : UploadDocumentResult()
    data class SetDescription(val description: String) : UploadDocumentResult()
}