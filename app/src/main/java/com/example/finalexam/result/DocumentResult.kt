package com.example.finalexam.result

import com.example.finalexam.entity.Document

sealed class DocumentResult {
    object Loading : DocumentResult()
    data class Loaded(val document: Document) : DocumentResult()
    data class DownloadUrl(val url: String) : DocumentResult()
    data class Liked(val updatedLikes: List<Document.Like>? = null) : DocumentResult()
    data class Unliked(val updatedLikes: List<Document.Like>? = null) : DocumentResult()
    data object Followed : DocumentResult()
    data object Unfollowed : DocumentResult()
    data class Error(val message: String) : DocumentResult()
}