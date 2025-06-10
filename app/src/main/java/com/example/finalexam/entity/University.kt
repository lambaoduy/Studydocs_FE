package com.example.finalexam.entity

data class University(
    val id: String,
    val name: String,
    val courses: List<String>,
    val selectedCourseIndex: Int = 0
) {
    val selectedCourse: String
        get() = courses[selectedCourseIndex].toString()
}



