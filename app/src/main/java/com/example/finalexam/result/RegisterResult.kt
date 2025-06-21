package com.example.finalexam.result

import com.example.finalexam.entity.User

sealed class RegisterResult {
    object Loading : RegisterResult()
    data class Success(val user: User) : RegisterResult()
    data class Error(val throwable: Throwable) : RegisterResult()
}

