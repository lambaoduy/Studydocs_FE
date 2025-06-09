package com.example.finalexam.result

// thiện làm: Result cho Auth (Login/Register)
sealed class AuthResult {
    object Loading : AuthResult()
    object Success : AuthResult()
    data class Error(val throwable: Throwable) : AuthResult()
} 