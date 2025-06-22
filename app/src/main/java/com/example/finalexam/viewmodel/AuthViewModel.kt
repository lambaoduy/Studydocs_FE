package com.example.finalexam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.handler.auth.ForgotPasswordHandler
import com.example.finalexam.handler.auth.LoginHandler
import com.example.finalexam.handler.auth.RegisterHandler
import com.example.finalexam.intent.AuthIntent
import com.example.finalexam.reduce.AuthReducer
import com.example.finalexam.result.AuthResult
import com.example.finalexam.state.AuthState
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

// thiện làm: ViewModel cho Auth (Login/Register)
class AuthViewModel : ViewModel() {
    private val reducer = AuthReducer()
    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state
    private val handlers: List<IntentHandler<AuthIntent, AuthResult>> = listOf(
        LoginHandler(),
        RegisterHandler(),
        ForgotPasswordHandler(),
    )


    fun processIntent(intent: AuthIntent) {
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
