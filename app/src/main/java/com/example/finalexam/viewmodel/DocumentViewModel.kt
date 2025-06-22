package com.example.finalexam.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexam.data.api.DocumentApi
import com.example.finalexam.data.dao.document.DocumentDao
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.handler.document.DocumentFollowHandler
import com.example.finalexam.handler.document.DocumentSaveHandler
import com.example.finalexam.handler.document.DocumentUnFollowHandler
import com.example.finalexam.handler.document.DownloadDocumentHandler
import com.example.finalexam.handler.document.LikeDocumentHandler
import com.example.finalexam.handler.document.LoadDocumentHandler
import com.example.finalexam.handler.document.UnlikeDocumentHandler
import com.example.finalexam.intent.DocumentIntent
import com.example.finalexam.intent.FollowIntent
import com.example.finalexam.network.RetrofitClient
import com.example.finalexam.reduce.DocumentReducer
import com.example.finalexam.result.DocumentResult
import com.example.finalexam.state.DocumentState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DocumentViewModel : ViewModel() {
    private val reducer = DocumentReducer()
    private val _state = MutableStateFlow(DocumentState())
    val state = _state.asStateFlow()
    val documentApi = RetrofitClient.createApi(DocumentApi::class.java)
    private val documentDao = DocumentDao(documentApi)
    private val handlers = listOf<IntentHandler<DocumentIntent, DocumentResult>>(
        LoadDocumentHandler(),
        DownloadDocumentHandler(),
        LikeDocumentHandler(),
        UnlikeDocumentHandler(),
        ErrorHandler(),
        DocumentFollowHandler(),
        DocumentUnFollowHandler(),
        DocumentSaveHandler(documentDao)
    )

    fun processIntent(intent: Any) {
        viewModelScope.launch {
            Log.d("DocumentViewModel", "Xử lý intent: $intent")
            when (intent) {
                is DocumentIntent -> {
                    val handler = handlers.find { it.canHandle(intent) }
                    if (handler != null) {
                        try {
                            handler.handle(intent) { result ->
                                Log.d("DocumentViewModel", "Kết quả: $result")
                                var newState = reducer.reduce(_state.value, result)
                                if (result is DocumentResult.Loaded) {
                                    val userId = FirebaseAuth.getInstance().currentUser?.uid
                                    if (userId != null) {
                                        val isLiked = result.document.likes?.any { it.userId == userId } ?: false
                                        newState = newState.copy(isLiked = isLiked)
                                        Log.d("DocumentViewModel", "Cập nhật isLiked: $isLiked cho documentId: ${result.document.id}")
                                    }
                                }
                                _state.value = newState
                            }
                        } catch (e: Exception) {
                            Log.e("DocumentViewModel", "Lỗi xử lý DocumentIntent: ${e.message}")
                            _state.value = reducer.reduce(
                                _state.value,
                                DocumentResult.Error("Lỗi: ${e.message}")
                            )
                        }
                    } else {
                        Log.w("DocumentViewModel", "Không có handler cho intent: $intent")
                    }
                }
                is FollowIntent -> {
                    Log.d("DocumentViewModel", "FollowIntent được gửi từ UI: $intent")
                    // FollowIntent sẽ được xử lý bởi FollowViewModel trong DocumentDetailScreen
                }
                else -> {
                    Log.w("DocumentViewModel", "Loại intent không xác định: $intent")
                }
            }
        }
    }

    inner class ErrorHandler : IntentHandler<DocumentIntent, DocumentResult> {
        override fun canHandle(intent: DocumentIntent): Boolean = intent is DocumentIntent.Error
        override suspend fun handle(intent: DocumentIntent, setResult: (DocumentResult) -> Unit) {
            val errorIntent = intent as DocumentIntent.Error
            Log.e("DocumentViewModel", "Intent lỗi: ${errorIntent.message}")
            setResult(DocumentResult.Error(errorIntent.message))
        }
    }
}