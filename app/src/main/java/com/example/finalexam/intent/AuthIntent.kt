package com.example.finalexam.intent

// thiện làm: Định nghĩa các intent cho Auth (Login/Register)
sealed class AuthIntent {
    data class Login(val email: String, val password: String) : AuthIntent()
    data class Register(val username: String, val email: String, val password: String) : AuthIntent()
    data class ForgotPassword(val email: String) : AuthIntent()
    object ClearError : AuthIntent()
    // Intent cập nhật thông tin user
    data class UpdateProfile(val user: com.example.finalexam.entity.User) : AuthIntent()
    // Intent lấy lại thông tin user từ Firebase
    data class LoadProfile(val userId: String) : AuthIntent()
}