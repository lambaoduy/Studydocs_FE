package com.example.finalexam.result

import com.example.finalexam.entity.User

// Result cho chức năng cập nhật profile
sealed class UpdateProfileResult {
    object Loading : UpdateProfileResult()
    data class Loaded(val user: User) : UpdateProfileResult()
    data class Updated(val user: User) : UpdateProfileResult()
    data class Error(val error: Throwable) : UpdateProfileResult()
    object Success : UpdateProfileResult()
}

