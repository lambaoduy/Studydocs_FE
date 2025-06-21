package com.example.finalexam.intent

sealed class RegisterIntent {
    data class Register(val username: String, val email: String, val password: String) : RegisterIntent()
    object ClearError : RegisterIntent()
}

