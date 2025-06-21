package com.example.finalexam.result

//Result cho Auth (Login/Register)
sealed class AuthResult {
    object Loading : AuthResult()
    object Success : AuthResult()
    data class Error(val throwable: Throwable) : AuthResult()
    data class ProfileLoaded(val user: com.example.finalexam.entity.User) : AuthResult()
    data class ProfileUpdated(val user: com.example.finalexam.entity.User) : AuthResult()
} 