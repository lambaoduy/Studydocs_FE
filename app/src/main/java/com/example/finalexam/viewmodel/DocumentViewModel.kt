package com.example.finalexam.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.handler.document.*
import com.example.finalexam.intent.DocumentIntent
import com.example.finalexam.reduce.DocumentReducer
import com.example.finalexam.result.DocumentResult
import com.example.finalexam.state.DocumentState
import com.example.finalexam.ui.myLibraryScreen.UploadDocumentScreen
import com.example.finalexam.network.AuthFilter
import android.app.Application
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DocumentViewModel(private val app: Application) : ViewModel() {
    private val reducer = DocumentReducer()
    private val _state = MutableStateFlow(DocumentState())
    val state = _state.asStateFlow()

    private val handlers = listOf(
        LoadDocumentHandler(),
        DownloadDocumentHandler(),
        LikeDocumentHandler(),
        UnlikeDocumentHandler(),
        ErrorHandler() // Thêm ErrorHandler vào danh sách
    )

    /**
     * Xử lý intent từ UI
     * @param intent Hành động người dùng thực hiện
     */
    fun processIntent(intent: DocumentIntent) {
        // Kiểm tra đăng nhập trước khi xử lý intent
        AuthFilter.requireLogin(app)
        viewModelScope.launch {
            val handler = handlers.find { it.canHandle(intent) }
            handler?.handle(intent) { result ->
                _state.value = reducer.reduce(_state.value, result)
            } ?: println("[WARN] No handler for intent: $intent")
        }
    }

    inner class ErrorHandler : IntentHandler<DocumentIntent, DocumentResult> {
        override fun canHandle(intent: DocumentIntent): Boolean = intent is DocumentIntent.Error
        override suspend fun handle(intent: DocumentIntent, setResult: (DocumentResult) -> Unit) {
            val errorIntent = intent as DocumentIntent.Error
            setResult(DocumentResult.Error(errorIntent.message))
        }
    }
}
