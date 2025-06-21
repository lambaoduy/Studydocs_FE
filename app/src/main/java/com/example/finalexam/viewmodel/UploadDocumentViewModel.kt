package com.example.finalexam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.handler.upload.SetDocumentHandler
import com.example.finalexam.handler.upload.SetTextHandler
import com.example.finalexam.handler.upload.UploadDocumentHandler
import com.example.finalexam.intent.UploadDocumentIntent
import com.example.finalexam.reduce.UploadDocumentReducer
import com.example.finalexam.result.UploadDocumentResult
import com.example.finalexam.state.UploadDocumentState
import com.example.finalexam.usecase.upload.UploadDocumentsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UploadDocumentViewModel(
    private val uploadDocumentsUseCase: UploadDocumentsUseCase
) : ViewModel() {

    private val reducer = UploadDocumentReducer()
    private val _state = MutableStateFlow(UploadDocumentState())
    val state = _state.asStateFlow()

    private val handlers: List<IntentHandler<UploadDocumentIntent, UploadDocumentResult>> = listOf(
        SetDocumentHandler(),
        SetTextHandler(),
        UploadDocumentHandler(uploadDocumentsUseCase)
    )

    fun processIntent(intent: UploadDocumentIntent) {
        viewModelScope.launch {
            handlers.find { it.canHandle(intent) }?.handle(intent) { result ->
                _state.value = reducer.reduce(_state.value, result)
            } ?: println("[WARN] No handler for intent: $intent")
        }
    }
}
