package com.example.finalexam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.handler.profile.LoadProfileHandler
import com.example.finalexam.handler.profile.LogoutHandler
import com.example.finalexam.handler.profile.UpdateProfileHandler
import com.example.finalexam.intent.ProfileIntent
import com.example.finalexam.reduce.ProfileReducer
import com.example.finalexam.result.ProfileResult
import com.example.finalexam.state.UpdateProfileState
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

// ViewModel cho chức năng cập nhật profile
class ProfileViewModel : ViewModel() {
    private val _state = MutableStateFlow(UpdateProfileState())
    private val reducer = ProfileReducer()
    val state = _state.asStateFlow()

    private val handlers: List<IntentHandler<ProfileIntent, ProfileResult>> = listOf(
        UpdateProfileHandler(),
        LogoutHandler(),
        LoadProfileHandler()

    )

    fun processIntent(intent: ProfileIntent) {
        viewModelScope.launch {
            try {
                withTimeout(5000L) {
                    handlers.find { it.canHandle(intent) }?.handle(intent) { result ->
                        _state.value = reducer.reduce(_state.value, result)
                    } ?: println("[WARN] No handler for intent: $intent")
                }
            } catch (e: TimeoutCancellationException) {
                _state.value = state.value.copy(isLoading = false, error = "Timeout đăng nhập!")
            }
        }
    }
}
