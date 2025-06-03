package com.example.finalexam.intent

// thiện làm: Định nghĩa các intent cho Auth (Login/Register)
sealed class AuthIntent {
    data class Login(val email: String, val password: String) : AuthIntent()
    data class Register(val username: String, val email: String, val password: String) : AuthIntent()
    object ClearError : AuthIntent()
} 