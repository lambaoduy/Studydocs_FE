package com.example.finalexam.result

import com.example.finalexam.entity.University
import com.example.finalexam.entity.UploadDocument


sealed class UploadDocumentResult {
    data object Loading : UploadDocumentResult()
    data class SetSelectedDocument(val document: UploadDocument?) : UploadDocumentResult() // null nếu bỏ file
    data class SelectUniversitySuccess(val university: University) : UploadDocumentResult()
    data class SelectCourseSuccess(val courseIndex: Int) : UploadDocumentResult()
    data class UploadSuccess(val uploadedDocuments: List<UploadDocument>) : UploadDocumentResult()
    data class Error(val message: String) : UploadDocumentResult()
    data class SetTitleSuccess(val title: String) : UploadDocumentResult()
    data class SetDescriptionSuccess(val description: String) : UploadDocumentResult()
    data class SubjectListLoaded(val subjects: List<String>) : UploadDocumentResult()
    data class SubjectCreated(val subject: String) : UploadDocumentResult()
}