package com.example.finalexam.result

import com.example.finalexam.entity.User

sealed class LoginResult {
    object Loading : LoginResult()
    data class Success(val user: User) : LoginResult()
    data class Error(val throwable: Throwable) : LoginResult()
}

