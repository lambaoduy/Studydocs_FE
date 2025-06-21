package com.example.finalexam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalexam.usecase.upload.UploadDocumentsUseCase

class UploadDocumentViewModelFactory(
    private val uploadDocumentsUseCase: UploadDocumentsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UploadDocumentViewModel(uploadDocumentsUseCase) as T
    }
}
