package com.example.finalexam.entity

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class Document(
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val fileUrl: String = "",
    val subject: String = "",
    val university: String = "",
    @SerializedName("isDelete")
    val isDelete: Boolean = false,
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null,
    val deletedAt: Timestamp? = null,
    val likes: List<Like>? = null
) {
    data class Like(
        val userId: String = "",
        val type: String = "",
        val createAt: Timestamp? = null
    )
}