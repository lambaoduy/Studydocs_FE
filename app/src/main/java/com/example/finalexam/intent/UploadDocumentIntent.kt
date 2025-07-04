// Định nghĩa các Intent (hành động) cho màn hình UploadDocument
package com.example.finalexam.intent

import com.example.finalexam.entity.UploadDocument

sealed class UploadDocumentIntent {
    object PickDocument : UploadDocumentIntent()
    data class SetSelectedDocument(val document: UploadDocument) : UploadDocumentIntent()
    object RemoveSelectedDocument : UploadDocumentIntent()
    
    data class SetTitle(val title: String) : UploadDocumentIntent()
    data class SetDescription(val description: String) : UploadDocumentIntent()
    
    object Upload : UploadDocumentIntent()
    object Back : UploadDocumentIntent()
}


