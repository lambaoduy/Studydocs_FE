package com.example.finalexam.entity

data class University(
    val id: String,
    val name: String,
    val subjects: List<String>,
    val selectedSubjectIndex: Int = 0
) {
    val selectedSubject: String
        get() = subjects[selectedSubjectIndex].toString()
}



