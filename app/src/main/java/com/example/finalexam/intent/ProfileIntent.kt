package com.example.finalexam.intent

import com.example.finalexam.entity.User

// Intent cho chức năng cập nhật profile
sealed class ProfileIntent {
    object Logout : ProfileIntent()
    data class Update(val user: User) : ProfileIntent()
    object Load : ProfileIntent()
    object ClearError : ProfileIntent()
}

