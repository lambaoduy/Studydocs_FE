package com.example.finalexam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexam.intent.UploadDocumentIntent
import com.example.finalexam.state.UploadDocumentState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// ===== Hảo làm phần này (Đồng bộ dữ liệu trường và môn học từ UniversityViewModel) =====
class UploadDocumentViewModel(
    private val universityViewModel: UniversityViewModel // Inject hoặc truyền vào từ Activity/Fragment
) : ViewModel() {

    private val _state = MutableStateFlow(UploadDocumentState())
    val state: StateFlow<UploadDocumentState> = _state

    init {
        // Lắng nghe state của UniversityViewModel để đồng bộ danh sách trường
        viewModelScope.launch {
            universityViewModel.state.collect { uniState ->
                _state.update { it.copy(universityList = uniState.universityList) }
            }
        }
    }

    fun processIntent(intent: UploadDocumentIntent) {
        when (intent) {
            is UploadDocumentIntent.PickDocument -> {
                // UI sẽ handle mở file picker
            }

            is UploadDocumentIntent.SetSelectedDocument -> {
                _state.value = _state.value.copy(selectedDocument = intent.document)
            }

            is UploadDocumentIntent.RemoveSelectedDocument -> {
                _state.value = _state.value.copy(selectedDocument = null)
            }

            is UploadDocumentIntent.SelectUniversity -> {
                // Lấy trường từ danh sách đã đồng bộ từ UniversityViewModel
                val university = _state.value.universityList.find { it.id == intent.universityId }
                if (university != null) {
                    _state.value = _state.value.copy(university = university)
                }
            }

            is UploadDocumentIntent.SelectSubjectIndex -> {
                _state.value.university?.let { uni ->
                    val updatedUni = uni.copy(selectedSubjectIndex = intent.index)
                    _state.value = _state.value.copy(university = updatedUni)
                }
            }

            is UploadDocumentIntent.AddSubject -> {
                // Gọi UniversityViewModel để thêm môn học qua API
                _state.value.university?.let { uni ->
                    universityViewModel.addSubject(uni.id, intent.subjectName)
                    // Khi UniversityViewModel cập nhật xong, danh sách trường sẽ tự đồng bộ lại qua collect ở trên
                }
            }

            is UploadDocumentIntent.SetTitle -> {
                _state.value = _state.value.copy(title = intent.title)
            }

            is UploadDocumentIntent.SetDescription -> {
                _state.value = _state.value.copy(description = intent.description)
            }

            is UploadDocumentIntent.SelectSubjectByName -> {
                _state.value = _state.value.copy(subject = intent.subject)
            }

            is UploadDocumentIntent.Upload -> {
                if (_state.value.selectedDocument != null) {
                    _state.value = _state.value.copy(isUploading = true)
                    viewModelScope.launch {
                        kotlinx.coroutines.delay(1000)
                        _state.value = _state.value.copy(
                            isUploading = false,
                            uploadSuccess = true
                        )
                    }
                }
            }

            is UploadDocumentIntent.Back -> {
                // Nếu cần xử lý riêng khi back
            }
        }
    }
}
// ===== end Hảo làm phần này =====
