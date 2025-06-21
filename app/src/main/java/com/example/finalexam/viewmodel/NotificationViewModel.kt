package com.example.finalexam.viewmodel

import androidx.lifecycle.ViewModel
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.handler.notification.NotificationDeleteAllHandler
import com.example.finalexam.handler.notification.NotificationDeleteHandler
import com.example.finalexam.handler.notification.NotificationInitialHandler
import com.example.finalexam.handler.notification.NotificationMarkAsReadAllHandler
import com.example.finalexam.handler.notification.NotificationMarkAsReadHandler
import com.example.finalexam.intent.NotificationIntent
import com.example.finalexam.reduce.NotificationReducer
import com.example.finalexam.result.NotificationResult
import com.example.finalexam.state.NotificationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NotificationViewModel: ViewModel() {
    private val reducer = NotificationReducer()
    private val _state = MutableStateFlow(NotificationState())
    val state = _state.asStateFlow()
    private val handlers: List<IntentHandler<NotificationIntent, NotificationResult>> = listOf(
        NotificationInitialHandler(),
        NotificationMarkAsReadHandler(),
        NotificationMarkAsReadAllHandler(),
        NotificationDeleteHandler(),
        NotificationDeleteAllHandler()
    )

    suspend fun processIntent(intent: NotificationIntent) {
        handlers.find { it.canHandle(intent) }?.handle(intent) { result ->
            _state.value = reducer.reduce(_state.value, result)
        } ?: println("[WARN] No handler for intent: $intent")
    }


}