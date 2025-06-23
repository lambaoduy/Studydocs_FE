package com.example.finalexam.intent

sealed class UniversityIntent {
    object LoadUniversities : UniversityIntent()
    data class AddSubject(val universityName: String, val subject: String) : UniversityIntent()
    data class SelectUniversity(val universityName: String) : UniversityIntent()
    data class SelectSubject(val universityName: String, val subjectIndex: Int) : UniversityIntent()
} 