package com.example.finalexam.reduce

import com.example.finalexam.result.UpdateProfileResult
import com.example.finalexam.state.UpdateProfileState

// Reducer cho chức năng cập nhật profile
object UpdateProfileReducer {
    fun reduce(state: UpdateProfileState, result: UpdateProfileResult): UpdateProfileState = when (result) {
        is UpdateProfileResult.Loading -> state.copy(isLoading = true, error = null, isSuccess = false)
        is UpdateProfileResult.Loaded -> state.copy(isLoading = false, user = result.user, error = null)
        is UpdateProfileResult.Updated -> state.copy(isLoading = false, user = result.user, isSuccess = true, error = null)
        is UpdateProfileResult.Error -> state.copy(isLoading = false, error = result.error, isSuccess = false)
        is UpdateProfileResult.Success -> state.copy(isLoading = false, isSuccess = true, error = null)
    }
}

