package com.example.finalexam.usecase.mylibrary

import com.example.finalexam.data.dao.document.DocumentDao
import com.example.finalexam.result.MyLibraryResult

class LoadDocumentsUseCase(private val documentDao: DocumentDao) {
    suspend operator fun invoke(): MyLibraryResult {
        return try {
            val documents = documentDao.getMyDocuments()
            MyLibraryResult.LoadDocumentsSuccess(documents)
        } catch (e: Exception) {
            MyLibraryResult.Error(e.message ?: "Failed to load documents")
        }
    }
} 