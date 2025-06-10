package com.example.finalexam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexam.entity.University
//import com.example.finalexam.entity.UploadDocument
import com.example.finalexam.intent.UploadDocumentIntent
import com.example.finalexam.state.UploadDocumentState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.collections.map


// ViewModel cho màn hình UploadDocument
// Nhận Intent từ UI, xử lý logic và cập nhật State
class UploadDocumentViewModel : ViewModel() {
    // StateFlow để quản lý trạng thái UI
    private val _state = MutableStateFlow(UploadDocumentState())
    val state: StateFlow<UploadDocumentState> = _state

    // Hàm nhận Intent từ UI
    fun processIntent(intent: UploadDocumentIntent) {
        when (intent) {
            is UploadDocumentIntent.PickDocument -> {
                // Trigger UI mở file picker, phần này sẽ do UI xử lý tiếp
            }
            is UploadDocumentIntent.SetSelectedDocument -> {
                _state.value = _state.value.copy(selectedDocument = intent.document)
            }
            is UploadDocumentIntent.RemoveSelectedDocument -> {
                _state.value = _state.value.copy(selectedDocument = null)
            }
            is UploadDocumentIntent.SelectUniversity -> {
                // Chọn trường đại học (giả lập)
                // TODO: Lấy thông tin trường từ backend nếu cần
                val university = University(
                    id = intent.universityId,
                    name = "Đại học Nông Lâm TP.HCM",
                    courses = listOf(
                        "Công nghệ thông tin",
                        "Khoa học dữ liệu",
                        "Kỹ thuật phần mềm"
                    ),
                    selectedCourseIndex = 0
                )
                _state.value = _state.value.copy(university = university)
            }

            is UploadDocumentIntent.SelectCourse -> {
                // Chọn khóa học trong trường
                _state.value.university?.let { uni ->
                    val updatedUni = uni.copy(selectedCourseIndex = intent.courseIndex)
                    _state.value = _state.value.copy(university = updatedUni)
                }
            }

            is UploadDocumentIntent.Upload -> {
                // Xử lý upload tài liệu, chỉ upload nếu đã chọn file
                if (_state.value.selectedDocument != null) {
                    _state.value = _state.value.copy(isUploading = true)
                    // Giả lập upload
                    viewModelScope.launch {
                        kotlinx.coroutines.delay(1000)
                        _state.value = _state.value.copy(isUploading = false, uploadSuccess = true)
                    }
                }
            }

            is UploadDocumentIntent.Back -> {
                // Có thể xử lý logic back nếu cần
            }

            is UploadDocumentIntent.SetTitle -> {
                _state.value = _state.value.copy(title = intent.title)
            }

            is UploadDocumentIntent.SetDescription -> {
                _state.value = _state.value.copy(description = intent.description)
            }

            is UploadDocumentIntent.SelectSubject -> {
                _state.value = _state.value.copy(subject = intent.subject)
            }

            is UploadDocumentIntent.CreateSubject -> {
                // Giả lập tạo môn học mới
                _state.value = _state.value.copy(isCreatingSubject = true)
                viewModelScope.launch {
                    kotlinx.coroutines.delay(500)
                    _state.value = _state.value.copy(
                        subject = intent.subjectName,
                        isCreatingSubject = false
                    )
                }
            }
        }
    }

    // Hàm khởi tạo dữ liệu mẫu (có thể gọi khi vào màn hình)
    fun loadMockData() {
        val university1 = University(
            id = "1",
            name = "Đại học Nông Lâm TP.HCM",
            courses = listOf("Công nghệ thông tin", "Khoa học dữ liệu", "Kỹ thuật phần mềm"),
            selectedCourseIndex = 0
        )

        val university2 = University(
            id = "2",
            name = "Đại học Khoa Học Tự Nhiên",
            courses = listOf("Công nghệ thông tin", "Khoa học dữ liệu", "Kỹ thuật phần mềm"),
            selectedCourseIndex = 1
        )

        _state.value = _state.value.copy(universityList = listOf(university1, university2))
    }

}