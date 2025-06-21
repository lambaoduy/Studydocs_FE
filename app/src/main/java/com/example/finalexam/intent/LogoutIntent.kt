package com.example.finalexam.intent

// Intent cho chức năng đăng xuất
sealed class LogoutIntent {
    object Logout : LogoutIntent()
}

