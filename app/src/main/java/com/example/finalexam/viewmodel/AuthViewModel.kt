package com.example.finalexam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexam.handler.AuthHandler
import com.example.finalexam.intent.AuthIntent
import com.example.finalexam.reduce.AuthReducer
import com.example.finalexam.result.AuthResult
import com.example.finalexam.state.AuthState
import com.example.finalexam.usecase.AuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// thiện làm: ViewModel cho Auth (Login/Register)
class AuthViewModel : ViewModel() {
    private val reducer = AuthReducer()
    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state

    private val handler = AuthHandler(AuthUseCase())

    fun processIntent(intent: AuthIntent) {
        viewModelScope.launch {
            handler.handle(intent) { result ->
                _state.value = reducer.reduce(_state.value, result)
            }
        }
    }
} 