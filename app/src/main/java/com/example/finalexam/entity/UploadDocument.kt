package com.example.finalexam.entity

data class UploadDocument(
    val id: String,
    val name: String,
    val title: String = "",
    val description: String = "",
    val universityId: String = "",
    val subject: String = "",
    val fileUrl: String = "",
    val fileId: String = "",
    var isSelected: Boolean = false,
    var isDelete: Boolean = false,
    var createAt: String = "",
    var updateAt: String = "",
    var deletedAt: String? = null
)