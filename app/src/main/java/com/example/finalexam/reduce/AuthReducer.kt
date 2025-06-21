package com.example.finalexam.reduce

import com.example.finalexam.result.AuthResult
import com.example.finalexam.state.AuthState

// thiện làm: Reducer cho Auth (Login/Register)
class AuthReducer {
    fun reduce(state: AuthState, result: AuthResult): AuthState = when (result) {
        is AuthResult.Loading -> state.copy(isLoading = true, error = null)
        is AuthResult.Success -> state.copy(isLoading = false, isSuccess = true)
        is AuthResult.Error -> state.copy(isLoading = false, error = result.message)
        is AuthResult.ProfileLoaded -> state.copy(isLoading = false, user = result.user)
        is AuthResult.ProfileUpdated -> state.copy(isLoading = false, user = result.user, isSuccess = true)
    }
} 