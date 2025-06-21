package com.example.finalexam.state

data class LogoutState(
    val isLoading: Boolean = false,
    val isLoggedOut: Boolean = false,
    val error: Throwable? = null
)

