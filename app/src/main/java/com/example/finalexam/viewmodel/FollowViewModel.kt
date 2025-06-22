package com.example.finalexam.viewmodel

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
    )

    fun processIntent(intent: FollowIntent) {
        viewModelScope.launch {
            handlers.find { it.canHandle(intent) }?.handle(intent) { result ->
                _state.value = reducer.reduce(_state.value, result)
            } ?: println("[WARN] No handler for intent: $intent")
        }
    }

}