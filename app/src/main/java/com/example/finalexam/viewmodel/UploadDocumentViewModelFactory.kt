package com.example.finalexam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UploadDocumentViewModelFactory(
    private val universityViewModel: UniversityViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UploadDocumentViewModel(universityViewModel) as T
    }
}
