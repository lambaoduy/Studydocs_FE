package com.example.finalexam.state

sealed class ForgotPasswordState {
    object Idle : ForgotPasswordState()
    object Loading : ForgotPasswordState()
    object Success : ForgotPasswordState()
    data class Error(val throwable: Throwable) : ForgotPasswordState()
}

