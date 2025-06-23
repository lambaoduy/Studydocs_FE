package com.example.finalexam.entity

import android.net.Uri

data class UploadDocument(
    val id: String,
    val name: String,
    val title: String = "",
    val description: String = "",
    val universityName: String = "",
    val subject: String = "",
    val fileUrl: String = "",
    val fileId: String = "",
    //===Phần này của Hảo 22/6===
    val uri: Uri? = null, // Uri thực của file để upload
    //===Phần này của Hảo 22/6===
    var isSelected: Boolean = false,
    var isDelete: Boolean = false,
    var createAt: String = "",
    var updateAt: String = "",
    var deletedAt: String? = null
)