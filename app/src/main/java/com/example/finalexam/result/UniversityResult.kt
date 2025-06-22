package com.example.finalexam.result

import com.example.finalexam.entity.University

sealed class UniversityResult {
    data object Loading : UniversityResult()
    data class UniversitiesLoaded(val universities: List<University>) : UniversityResult()
    data class SubjectAdded(val success: Boolean) : UniversityResult()
    data class UniversitySelected(val university: University) : UniversityResult()
    data class SubjectSelected(val university: University) : UniversityResult()
    data class Error(val message: String) : UniversityResult()
} 