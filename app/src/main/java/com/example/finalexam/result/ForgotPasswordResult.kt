package com.example.finalexam.result

sealed class ForgotPasswordResult {
    object Loading : ForgotPasswordResult()
    object Success : ForgotPasswordResult()
    data class Error(val throwable: Throwable) : ForgotPasswordResult()
}

