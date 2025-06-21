package com.example.finalexam.intent

sealed class ForgotPasswordIntent {
    data class Submit(val email: String) : ForgotPasswordIntent()
}

