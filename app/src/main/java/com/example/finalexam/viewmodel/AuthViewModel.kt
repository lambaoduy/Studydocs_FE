package com.example.finalexam.viewmodel

import android.app.Application
import android.content.Context
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
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// thiện làm: ViewModel cho Auth (Login/Register)
class AuthViewModel(private val app: Application) : ViewModel() {
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
            handlers.find { it.canHandle(intent) }?.handle(intent) { result ->
                _state.value = reducer.reduce(_state.value, result)
            } ?: println("[WARN] No handler for intent: $intent")
        }
    }

    /**
     * Hàm đăng xuất: signOut Firebase và xóa token trong SharedPreferences
     */
    fun logout() {
        // Đăng xuất Firebase
        FirebaseAuth.getInstance().signOut()
        // Xóa token trong SharedPreferences
        val prefs = app.getSharedPreferences("APP_PREF", Context.MODE_PRIVATE)
        prefs.edit().remove("ID_TOKEN").apply()
        // Có thể cập nhật state hoặc gửi event để UI chuyển về màn hình đăng nhập
    }
}
