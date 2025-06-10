package com.example.finalexam.state

import com.example.finalexam.entity.Document

data class DocumentState(
    val isLoading: Boolean = false,
    val document: Document? = null,
    val downloadUrl: String? = null,
    val errorMessage: String? = null,
    val isLiked: Boolean = false

)