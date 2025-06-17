package com.example.finalexam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexam.entity.University
import com.example.finalexam.intent.UploadDocumentIntent
import com.example.finalexam.state.UploadDocumentState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UploadDocumentViewModel : ViewModel() {

    private val _state = MutableStateFlow(UploadDocumentState())
    val state: StateFlow<UploadDocumentState> = _state

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
                // Giả lập chọn 1 trường từ danh sách
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
                _state.value.university?.let { uni ->
                    val updatedSubjects = uni.subjects + intent.subjectName
                    val updatedUni = uni.copy(
                        subjects = updatedSubjects,
                        selectedSubjectIndex = updatedSubjects.lastIndex
                    )
                    _state.value = _state.value.copy(university = updatedUni)
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

    fun loadMockData() {
        val university1 = University(
            id = "1",
            name = "Đại học Nông Lâm TP.HCM",
            subjects = listOf("Công nghệ thông tin", "Khoa học dữ liệu", "Kỹ thuật phần mềm"),
            selectedSubjectIndex = 0
        )

        val university2 = University(
            id = "2",
            name = "Đại học Khoa Học Tự Nhiên",
            subjects = listOf("Hệ thống nhúng", "AI", "Học máy"),
            selectedSubjectIndex = 1
        )

        _state.value = _state.value.copy(universityList = listOf(university1, university2))
    }
}
