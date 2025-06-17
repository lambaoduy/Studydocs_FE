package com.example.finalexam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexam.entity.Document
import com.example.finalexam.intent.MyLibraryIntent
import com.example.finalexam.navigation.NavigationEvent
import com.example.finalexam.state.MyLibraryState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

// ViewModel cho màn hình MyLibrary
// Nhận Intent từ UI, xử lý logic và cập nhật State
class MyLibraryViewModel : ViewModel() {
    // StateFlow để quản lý trạng thái UI
    private val _state = MutableStateFlow(MyLibraryState())
    val state: StateFlow<MyLibraryState> = _state

    // SharedFlow để quản lý các sự kiện navigation
    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    // Hàm nhận Intent từ UI
    fun processIntent(intent: MyLibraryIntent) {
        when (intent) {
            is MyLibraryIntent.Search -> {
                // Cập nhật từ khóa tìm kiếm và lọc danh sách tài liệu
                _state.value = _state.value.copy(searchQuery = intent.query)
                filterDocuments(intent.query)
            }
            is MyLibraryIntent.Refresh -> {
                // Tải lại danh sách tài liệu
                loadDocuments()
            }
            is MyLibraryIntent.OnUploadClicked -> {
                // Gửi sự kiện navigation để chuyển đến màn hình upload
                viewModelScope.launch {
                    _navigationEvent.emit(NavigationEvent.NavigateToUpload)
                }
            }
            is MyLibraryIntent.SelectDocument -> {
                // Gửi sự kiện navigation để chuyển đến màn hình chi tiết tài liệu
                viewModelScope.launch {
                    _navigationEvent.emit(NavigationEvent.NavigateToDocumentDetail(intent.document.id))
                }
            }
            is MyLibraryIntent.NavigateToHome -> {
                viewModelScope.launch {
                    _navigationEvent.emit(NavigationEvent.NavigateToHome)
                }
            }
        }
    }

    // Hàm lọc tài liệu theo từ khóa tìm kiếm
    private fun filterDocuments(query: String) {
        val filtered = _state.value.documents.filter {
            it.title.contains(query, ignoreCase = true)
        }
        _state.value = _state.value.copy(documents = filtered)
    }

    // Hàm tải danh sách tài liệu
    fun loadDocuments() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            // TODO: Gọi backend hoặc repository để lấy dữ liệu thật
            // Dưới đây là dữ liệu mẫu
            val documents = listOf(
                Document("1", "Tài liệu Kotlin", "Nguyễn Văn A", "01/01/2024"),
                Document("2", "Tài liệu Jetpack Compose", "Trần Thị B", "02/01/2024"),
                Document("3", "Tài liệu Android", "Lê Văn C", "03/01/2024")
            )
            _state.value = _state.value.copy(isLoading = false, documents = documents)
        }
    }
} 