package com.example.finalexam.entity

data class User(
    val userId: String = "",
    val username: String = "",
    val email: String = "",
    val avatarUrl: String? = null,
    val phone: String? = null,
    val bio: String? = null
)
