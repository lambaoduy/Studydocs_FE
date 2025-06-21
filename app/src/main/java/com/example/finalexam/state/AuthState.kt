package com.example.finalexam.state

// State cho Auth (Login/Register)
data class AuthState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val user: com.example.finalexam.entity.User? = null
) 