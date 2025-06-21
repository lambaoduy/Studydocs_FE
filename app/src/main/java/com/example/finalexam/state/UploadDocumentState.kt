package com.example.finalexam.state

import com.example.finalexam.entity.University
import  com.example.finalexam.entity.UploadDocument

data class UploadDocumentState(
    val title: String = "",
    val description: String = "",
    val selectedDocument: UploadDocument? = null,
    val university: University? = null,
    val universityList: List<University> = emptyList(),
    val subject: String = "",
    val subjectList: List<String> = emptyList(),
    val isCreatingSubject: Boolean = false,
    val isUploading: Boolean = false,
    val uploadSuccess: Boolean = false,
    val uploadedDocuments: List<UploadDocument> = emptyList(),
    val error: String? = null
)
