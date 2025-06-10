package com.example.finalexam.intent

sealed class DocumentIntent {
    data class LoadDocument(val documentId: String) : DocumentIntent()
    data class DownloadDocument(val documentId: String) : DocumentIntent()
    data class LikeDocument(val documentId: String, val userId: String) : DocumentIntent()
    data class UnlikeDocument(val documentId: String, val userId: String) : DocumentIntent()
    data class Error(val message: String) : DocumentIntent()
}