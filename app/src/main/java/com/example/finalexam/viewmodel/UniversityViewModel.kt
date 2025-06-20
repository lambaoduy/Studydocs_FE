package com.example.finalexam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexam.entity.University
import com.example.finalexam.usecase.university.AddSubjectUseCase
import com.example.finalexam.usecase.university.GetAllUniversitiesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ===== Hảo làm phần này (UniversityViewModel quản lý danh sách trường và thêm môn học) =====
data class UniversityState(
    val universityList: List<University> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class UniversityViewModel(
    private val getAllUniversitiesUseCase: GetAllUniversitiesUseCase,
    private val addSubjectUseCase: AddSubjectUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UniversityState())
    val state: StateFlow<UniversityState> = _state

    /**
     * Lấy danh sách tất cả các trường đại học từ API
     * Hảo làm phần này
     */
    fun loadUniversities() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val universities = getAllUniversitiesUseCase()
                _state.value = _state.value.copy(
                    universityList = universities,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    /**
     * Thêm môn học mới vào trường đại học qua API
     * Hảo làm phần này
     */
    fun addSubject(universityId: String, subject: String) {
        viewModelScope.launch {
            try {
                val success = addSubjectUseCase(universityId, subject)
                if (success) {
                    // Sau khi thêm thành công, reload lại danh sách trường
                    loadUniversities()
                } else {
                    _state.value = _state.value.copy(error = "Môn học đã tồn tại")
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }
}
// ===== end Hảo làm phần này ===== 