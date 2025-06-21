package com.example.finalexam.intent

import com.example.finalexam.entity.User

// Intent cho chức năng cập nhật profile
sealed class UpdateProfileIntent {
    data class Update(val user: User) : UpdateProfileIntent()
    data class Load(val userId: String) : UpdateProfileIntent()
    object ClearError : UpdateProfileIntent()
}

