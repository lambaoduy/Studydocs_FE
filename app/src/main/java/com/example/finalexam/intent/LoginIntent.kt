package com.example.finalexam.intent

sealed class LoginIntent {
    data class Login(val email: String, val password: String) : LoginIntent()
    object ClearError : LoginIntent()
}

