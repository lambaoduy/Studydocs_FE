package com.example.finalexam.intent

sealed class UniversityIntent {
    object LoadUniversities : UniversityIntent()
    data class AddSubject(val universityId: String, val subject: String) : UniversityIntent()
    data class SelectUniversity(val universityId: String) : UniversityIntent()
    data class SelectSubject(val universityId: String, val subjectIndex: Int) : UniversityIntent()
} 