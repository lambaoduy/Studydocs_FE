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
import com.example.finalexam.intent.FollowIntent
import com.example.finalexam.reduce.DocumentReducer
import com.example.finalexam.result.DocumentResult
import com.example.finalexam.state.DocumentState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DocumentViewModel(
    private val followViewModel: FollowViewModel = FollowViewModel() // Tạo instance mặc định
) : ViewModel() {
    private val reducer = DocumentReducer()
    private val _state = MutableStateFlow(DocumentState())
    val state = _state.asStateFlow()

    private val handlers = listOf<IntentHandler<DocumentIntent, DocumentResult>>(
        LoadDocumentHandler(),
        DownloadDocumentHandler(),
        LikeDocumentHandler(),
        UnlikeDocumentHandler(),
        ErrorHandler()
    )

    init {
        // Lắng nghe FollowState từ FollowViewModel để cập nhật isFollowed
        viewModelScope.launch {
            followViewModel.state.collectLatest { followState ->
                val currentUserId = _state.value.document?.userId
                if (currentUserId != null) {
                    val isFollowed = followState.followings.any { it.followingId == currentUserId }
                    if (_state.value.isFollowed != isFollowed) {
                        _state.value = reducer.reduce(
                            _state.value,
                            if (isFollowed) DocumentResult.Followed else DocumentResult.Unfollowed
                        )
                        Log.d("DocumentViewModel", "Cập nhật isFollowed: $isFollowed cho userId: $currentUserId")
                    }
                }
            }
        }
    }

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
                                _state.value = reducer.reduce(_state.value, result)
                                // Kiểm tra trạng thái Follow sau khi tải tài liệu
                                if (intent is DocumentIntent.LoadDocument && result is DocumentResult.Loaded) {
                                    try {
                                        viewModelScope.launch {
                                            followViewModel.processIntent(FollowIntent.GetFollowings)
                                        }
                                    } catch (e: Exception) {
                                        Log.e("DocumentViewModel", "Lỗi xử lý GetFollowings: ${e.message}")
                                        _state.value = reducer.reduce(
                                            _state.value,
                                            DocumentResult.Error("Lỗi kiểm tra trạng thái theo dõi: ${e.message}")
                                        )
                                    }
                                }
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
                    Log.d("DocumentViewModel", "Chuyển tiếp FollowIntent: $intent")
                    try {
                        followViewModel.processIntent(intent)
                    } catch (e: Exception) {
                        Log.e("DocumentViewModel", "Lỗi xử lý FollowIntent: ${e.message}")
                        _state.value = reducer.reduce(
                            _state.value,
                            DocumentResult.Error("Lỗi xử lý theo dõi: ${e.message}")
                        )
                    }
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