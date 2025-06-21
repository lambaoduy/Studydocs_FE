package com.example.finalexam.result

sealed class LogoutResult {
    object Loading : LogoutResult()
    object LoggedOut : LogoutResult()
    data class Error(val throwable: Throwable) : LogoutResult()
}

