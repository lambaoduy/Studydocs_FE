package com.example.finalexam.intent

import com.example.finalexam.data.enums.FollowType

sealed class DocumentIntent {
    data class LoadDocument(val documentId: String) : DocumentIntent()
    data class DownloadDocument(val url: String) : DocumentIntent() // Phải là url, không phải documentId
    data class LikeDocument(val documentId: String) : DocumentIntent()
    data class UnlikeDocument(val documentId: String) : DocumentIntent()
    data class UnFollow(val targetId: String,val type: FollowType) : DocumentIntent()
    data class Follow(val targetId: String,val type: FollowType)  : DocumentIntent()
    data class Error(val message: String) : DocumentIntent()
}