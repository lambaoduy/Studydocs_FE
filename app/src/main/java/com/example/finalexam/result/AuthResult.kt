package com.example.finalexam.result

//Result cho Auth (Login/Register)
sealed class AuthResult {
    object Loading : AuthResult()
    object Success : AuthResult()
    data class Error(val message: String) : AuthResult()
}