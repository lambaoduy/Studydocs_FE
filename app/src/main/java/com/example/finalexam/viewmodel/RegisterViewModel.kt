package com.example.finalexam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexam.intent.RegisterIntent
import com.example.finalexam.result.RegisterResult
import com.example.finalexam.state.RegisterState
import com.example.finalexam.handler.auth.RegisterHandler
import com.example.finalexam.usecase.auth.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val useCase: RegisterUseCase) : ViewModel() {
    private val handler = RegisterHandler(useCase)
    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state

    fun processIntent(intent: RegisterIntent) {
        viewModelScope.launch {
            handler.handle(intent) { result ->
                _state.value = when (result) {
                    is RegisterResult.Loading -> _state.value.copy(isLoading = true, error = null)
                    is RegisterResult.Success -> _state.value.copy(isLoading = false, user = result.user, error = null)
                    is RegisterResult.Error -> _state.value.copy(isLoading = false, error = result.throwable)
                }
            }
        }
    }
}

