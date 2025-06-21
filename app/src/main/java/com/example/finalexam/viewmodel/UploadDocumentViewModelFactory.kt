package com.example.finalexam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

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
    private val universityViewModel: UniversityViewModel
) : ViewModelProvider.Factory {
    
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Kiểm tra xem class có phải là UploadDocumentViewModel không
        if (modelClass.isAssignableFrom(UploadDocumentViewModel::class.java)) {
            // Tạo UploadDocumentViewModel với universityViewModel dependency
            // TODO: Cần cập nhật UploadDocumentViewModel constructor để nhận universityViewModel
            return UploadDocumentViewModel(universityViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
