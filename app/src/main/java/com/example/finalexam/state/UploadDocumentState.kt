package com.example.finalexam.state

import com.example.finalexam.entity.Document
import com.example.finalexam.entity.UploadDocument

data class UploadDocumentState(
    val title: String = "",
    val description: String = "",
    val selectedDocument: UploadDocument? = null,
    val isUploading: Boolean = false,
    val uploadSuccess: Boolean = false,
    val uploadedDocuments: List<Document> = emptyList(),
    val error: String? = null
)
