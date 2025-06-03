package com.example.finalexam.state

// thiện làm: State cho Auth (Login/Register)
data class AuthState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
) 