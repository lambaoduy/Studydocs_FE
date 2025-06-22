package com.example.finalexam.viewmodel // Đảm bảo package này đúng với cấu trúc dự án của bạn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalexam.usecase.university.AddSubjectUseCase
import com.example.finalexam.usecase.university.GetAllUniversitiesUseCase

// Lớp Factory này sẽ nhận các UseCase cần thiết làm tham số cho constructor của nó
class UniversityViewModelFactory(
    private val getAllUniversitiesUseCase: GetAllUniversitiesUseCase,
    private val addSubjectUseCase: AddSubjectUseCase
) : ViewModelProvider.Factory { // Implement ViewModelProvider.Factory

    // Ghi đè phương thức create
    @Suppress("UNCHECKED_CAST") // Cần thiết vì create trả về kiểu T chung chung
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Kiểm tra xem lớp ViewModel được yêu cầu có phải là UniversityViewModel không
        if (modelClass.isAssignableFrom(UniversityViewModel::class.java)) {
            // Nếu đúng, tạo và trả về một instance của UniversityViewModel,
            // truyền các UseCase đã được cung cấp cho Factory này vào constructor của ViewModel
            return UniversityViewModel(getAllUniversitiesUseCase, addSubjectUseCase) as T
        }
        // Nếu không phải lớp ViewModel mà Factory này biết cách tạo, ném ra một ngoại lệ
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}