package com.example.finalexam.viewmodel

import androidx.lifecycle.ViewModel
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.handler.follower.FollowHandler
import com.example.finalexam.handler.follower.GetFolloweesHandler
import com.example.finalexam.handler.follower.GetFollowersHandler
import com.example.finalexam.intent.FollowerIntent
import com.example.finalexam.reduce.FollowerReducer
import com.example.finalexam.result.FollowerResult
import com.example.finalexam.state.FollowerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FollowerViewModel: ViewModel() {
    private val reducer = FollowerReducer()
    private val _state = MutableStateFlow(FollowerState())
    private val state = _state.asStateFlow()
    private val handlers: List<IntentHandler<FollowerIntent, FollowerResult>> = listOf(
        GetFollowersHandler(),
        GetFolloweesHandler(),
        FollowHandler()
    )
    suspend fun processIntent(intent: FollowerIntent) {
        handlers.find { it.canHandle(intent) }?.handle(intent) { result ->
            _state.value = reducer.reduce(_state.value, result)
        } ?: println("[WARN] No handler for intent: $intent")
    }

}