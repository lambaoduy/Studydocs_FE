package com.example.finalexam.reduce

import com.example.finalexam.result.ProfileResult
import com.example.finalexam.state.ProfileState

// Reducer cho chức năng cập nhật profile
class ProfileReducer {
    fun reduce(state: ProfileState, result: ProfileResult): ProfileState =
        when (result) {
            is ProfileResult.Loading -> state.copy(
                isLoading = true,
                error = null,
                isSuccess = false
            )

            is ProfileResult.Loaded -> state.copy(
                isLoading = false,
                user = result.user,
                error = null
            )

            is ProfileResult.Error -> state.copy(
                isLoading = false,
                error = result.error,
                isSuccess = false
            )

            is ProfileResult.Success -> state.copy(
                isLoading = false,
                isSuccess = true,
                error = null
            )

            is ProfileResult.Logout -> state.copy(
                isLoading = false,
                isSuccess = true,
                user = null,
                error = null
            )
        }
}

