package com.example.finalexam.result

import com.example.finalexam.entity.User

// Result cho chức năng cập nhật profile
sealed class ProfileResult {
    object Loading : ProfileResult()
    data class Loaded(val user: User) : ProfileResult()
    data class Error(val error: String) : ProfileResult()
    object Success : ProfileResult()
    object Logout: ProfileResult()
}

