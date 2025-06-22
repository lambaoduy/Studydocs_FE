package com.example.finalexam.state

import com.example.finalexam.entity.User

// State cho chức năng cập nhật profile
data class ProfileState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val isSuccess: Boolean = false,
    val error: String? = null
)

