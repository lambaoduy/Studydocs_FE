package com.example.finalexam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexam.intent.LoginIntent
import com.example.finalexam.result.LoginResult
import com.example.finalexam.state.LoginState
import com.example.finalexam.handler.auth.LoginHandler
import com.example.finalexam.usecase.auth.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val useCase: LoginUseCase) : ViewModel() {
    private val handler = LoginHandler(useCase)
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    fun processIntent(intent: LoginIntent) {
        viewModelScope.launch {
            handler.handle(intent) { result ->
                _state.value = when (result) {
                    is LoginResult.Loading -> _state.value.copy(isLoading = true, error = null)
                    is LoginResult.Success -> _state.value.copy(isLoading = false, user = result.user, error = null)
                    is LoginResult.Error -> _state.value.copy(isLoading = false, error = result.throwable)
                }
            }
        }
    }
}
