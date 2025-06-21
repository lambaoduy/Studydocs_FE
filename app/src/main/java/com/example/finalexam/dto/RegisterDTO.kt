package com.example.finalexam.dto

// DTO đăng ký tài khoản, gửi lên BE để lưu thông tin người dùng vào Firestore
// Bổ sung các trường cần thiết: userId (UID Firebase), username, email, avatarUrl (nếu có)
data class RegisterDTO(
    val userId: String, // UID do Firebase cấp
    val username: String,
    val email: String,
    val avatarUrl: String? = null // Có thể null nếu chưa có ảnh đại diện
)
