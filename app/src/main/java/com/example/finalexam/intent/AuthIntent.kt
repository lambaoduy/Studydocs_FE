package com.example.finalexam.intent

//Định nghĩa các intent cho Auth (Login/Register)
sealed class AuthIntent {
    data class Login(val email: String, val password: String) : AuthIntent()
    data class Register(val username: String, val email: String, val password: String) : AuthIntent()
    object ClearError : AuthIntent()
    data class ForgotPassword(val email: String) : AuthIntent()
    data class GetProfile(val userId: String) : AuthIntent()
    data class UpdateProfile(val userId: String, val username: String, val email: String, val avatarUrl: String?, val phone: String?, val bio: String?) : AuthIntent()
} 