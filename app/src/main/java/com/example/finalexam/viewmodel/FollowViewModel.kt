package com.example.finalexam.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.handler.follow.FollowHandler
import com.example.finalexam.handler.follow.GetFollowersHandler
import com.example.finalexam.handler.follow.GetFollowingsHandler
import com.example.finalexam.handler.follow.ToggleNotifyEnableHandler
import com.example.finalexam.handler.follow.UnfollowHandler
import com.example.finalexam.intent.FollowIntent
import com.example.finalexam.reduce.FollowReducer
import com.example.finalexam.result.FollowResult
import com.example.finalexam.state.FollowState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FollowViewModel : ViewModel() {
    private val reducer = FollowReducer()
    private val _state = MutableStateFlow(FollowState())
    val state = _state.asStateFlow()
    private val handlers: List<IntentHandler<FollowIntent, FollowResult>> = listOf(
        GetFollowersHandler(),
        GetFollowingsHandler(),
        FollowHandler(),
        UnfollowHandler(),
        ToggleNotifyEnableHandler(),
        ErrorHandler() // Thêm handler lỗi
    )

    fun processIntent(intent: FollowIntent) {
        viewModelScope.launch {
            try {
                val handler = handlers.find { it.canHandle(intent) }
                if (handler != null) {
                    handler.handle(intent) { result ->
                        _state.value = reducer.reduce(_state.value, result)
                        if (result is FollowResult.Error) {
                            Log.e("FollowViewModel", "Lỗi xử lý intent: ${result.message}")
                        }
                    }
                } else {
                    Log.w("FollowViewModel", "Không có handler cho intent: $intent")
                    _state.value = reducer.reduce(
                        _state.value,
                        FollowResult.Error("Không tìm thấy handler cho intent")
                    )
                }
            } catch (e: Exception) {
                Log.e("FollowViewModel", "Lỗi xử lý intent: ${e.message}")
                _state.value = reducer.reduce(
                    _state.value,
                    FollowResult.Error("Lỗi: ${e.message}")
                )
            }
        }
    }

    inner class ErrorHandler : IntentHandler<FollowIntent, FollowResult> {
        override fun canHandle(intent: FollowIntent): Boolean = false // Xử lý lỗi toàn cục
        override suspend fun handle(intent: FollowIntent, setResult: (FollowResult) -> Unit) {
            setResult(FollowResult.Error("Lỗi không xác định"))
        }
    }
}