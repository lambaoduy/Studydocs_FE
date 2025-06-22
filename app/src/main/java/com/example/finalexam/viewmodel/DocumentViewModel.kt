package com.example.finalexam.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.handler.document.DownloadDocumentHandler
import com.example.finalexam.handler.document.LikeDocumentHandler
import com.example.finalexam.handler.document.LoadDocumentHandler
import com.example.finalexam.handler.document.UnlikeDocumentHandler
import com.example.finalexam.intent.DocumentIntent
import com.example.finalexam.reduce.DocumentReducer
import com.example.finalexam.result.DocumentResult
import com.example.finalexam.state.DocumentState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DocumentViewModel : ViewModel() {
    private val reducer = DocumentReducer()
    private val _state = MutableStateFlow(DocumentState())
    val state = _state.asStateFlow()

    private val handlers = listOf(
        LoadDocumentHandler(),
        DownloadDocumentHandler(),
        LikeDocumentHandler(),
        UnlikeDocumentHandler(),
        ErrorHandler()
    )

    fun processIntent(intent: DocumentIntent) {
        viewModelScope.launch {
            Log.d("DocumentViewModel", "Processing intent: $intent")
            val handler = handlers.find { it.canHandle(intent) }
            if (handler != null) {
                handler.handle(intent) { result ->
                    Log.d("DocumentViewModel", "Result: $result")
                    _state.value = reducer.reduce(_state.value, result)
                    Log.d("DocumentViewModel", "New state: ${_state.value}")
                }
            } else {
                Log.w("DocumentViewModel", "No handler for intent: $intent")
            }
        }
    }

    inner class ErrorHandler : IntentHandler<DocumentIntent, DocumentResult> {
        override fun canHandle(intent: DocumentIntent): Boolean = intent is DocumentIntent.Error
        override suspend fun handle(intent: DocumentIntent, setResult: (DocumentResult) -> Unit) {
            val errorIntent = intent as DocumentIntent.Error
            Log.e("DocumentViewModel", "Error intent: ${errorIntent.message}")
            setResult(DocumentResult.Error(errorIntent.message))
        }
    }
}