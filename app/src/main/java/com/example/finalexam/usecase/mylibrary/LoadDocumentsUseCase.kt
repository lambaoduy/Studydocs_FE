package com.example.finalexam.usecase.mylibrary

import com.example.finalexam.entity.Document
import com.example.finalexam.result.MyLibraryResult

class LoadDocumentsUseCase {
    suspend operator fun invoke(): MyLibraryResult {
        return try {
            // TODO: Replace with actual API call
            // val response = apiService.getDocuments()
            // MyLibraryResult.LoadDocumentsSuccess(response.documents)
            
            // Temporary mock data
            val mockDocuments = listOf(
                Document("1", "Tài liệu Kotlin", "Nguyễn Văn A", "01/01/2024"),
                Document("2", "Tài liệu Jetpack Compose", "Trần Thị B", "02/01/2024")
            )
            MyLibraryResult.LoadDocumentsSuccess(mockDocuments)
        } catch (e: Exception) {
            MyLibraryResult.Error(e.message ?: "Failed to load documents")
        }
    }
} 