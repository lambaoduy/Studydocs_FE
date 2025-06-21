package com.example.finalexam.state

import com.example.finalexam.entity.User

data class RegisterState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: Throwable? = null
)

