package com.example.finalexam.data.request

data class EditProfileRequest(
    val username: String,
    val email: String,
    val avatarUrl: String?,
    val phone: String?,
    val bio: String?
) 