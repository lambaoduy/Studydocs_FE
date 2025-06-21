package com.example.finalexam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexam.intent.LogoutIntent
import com.example.finalexam.result.LogoutResult
import com.example.finalexam.state.LogoutState
import com.example.finalexam.handler.auth.LogoutHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.content.Context

class LogoutViewModel(context: Context) : ViewModel() {
    private val handler = LogoutHandler(context)
    private val _state = MutableStateFlow(LogoutState())
    val state: StateFlow<LogoutState> = _state

    fun processIntent(intent: LogoutIntent) {
        viewModelScope.launch {
            handler.handle(intent) { result ->
                _state.value = when (result) {
                    is LogoutResult.Loading -> _state.value.copy(isLoading = true, error = null)
                    is LogoutResult.LoggedOut -> _state.value.copy(isLoading = false, isLoggedOut = true, error = null)
                    is LogoutResult.Error -> _state.value.copy(isLoading = false, error = result.throwable)
                }
            }
        }
    }
}

