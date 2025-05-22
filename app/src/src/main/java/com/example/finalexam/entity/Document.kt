package com.example.finalexam.entity

import com.google.gson.annotations.SerializedName
import java.time.Instant

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
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
    val deletedAt: Instant? = null,
    val likes: List<Like>? = null
) {
    data class Like(
        val userId: String = "",
        val type: String = "",
        val createAt: Instant? = null
    )
}