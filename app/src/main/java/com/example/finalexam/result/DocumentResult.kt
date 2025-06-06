package com.example.finalexam.result

import com.example.finalexam.entity.Document

sealed class DocumentResult {
    object Loading : DocumentResult()
    data class Loaded(val document: Document) : DocumentResult()
    data class DownloadUrl(val url: String) : DocumentResult()
    object ActionSuccess : DocumentResult()
    data class Error(val message: String) : DocumentResult()
}