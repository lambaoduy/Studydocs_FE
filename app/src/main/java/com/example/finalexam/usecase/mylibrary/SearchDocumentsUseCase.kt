package com.example.finalexam.usecase.mylibrary

import com.example.finalexam.entity.Document
import com.example.finalexam.result.MyLibraryResult

class SearchDocumentsUseCase {
    suspend operator fun invoke(query: String): MyLibraryResult {
        return try {
            // TODO: Replace with actual API call
            // val response = apiService.searchDocuments(query)
            // MyLibraryResult.SearchSuccess(response.documents)
            
            // Temporary mock data
            val mockDocuments = listOf(
                Document("1", "Tài liệu Kotlin", "Nguyễn Văn A", "01/01/2024"),
                Document("2", "Tài liệu Jetpack Compose", "Trần Thị B", "02/01/2024")
            ).filter { it.title.contains(query, ignoreCase = true) }
            
            MyLibraryResult.SearchSuccess(mockDocuments)
        } catch (e: Exception) {
            MyLibraryResult.Error(e.message ?: "Failed to search documents")
        }
    }
} 