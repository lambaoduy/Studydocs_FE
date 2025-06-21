package com.example.finalexam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalexam.usecase.upload.UploadDocumentsUseCase
import com.example.finalexam.viewmodel.UniversityViewModel
import com.example.finalexam.viewmodel.UploadDocumentViewModel

/**
 * Factory class để tạo UploadDocumentViewModel với dependency injection
 * 
 * Luồng hoạt động:
 * 1. UploadDocumentScreen cần cả UploadDocumentViewModel và UniversityViewModel
 * 2. UniversityViewModel được tạo trước và truyền vào factory
 * 3. Factory tạo UploadDocumentViewModel với universityViewModel dependency
 * 4. UploadDocumentViewModel có thể truy cập university data để upload
 */
class UploadDocumentViewModelFactory(
    private val universityViewModel: UniversityViewModel,
    private val uploadDocumentsUseCase: UploadDocumentsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UploadDocumentViewModel::class.java)) {
            return UploadDocumentViewModel(universityViewModel, uploadDocumentsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

