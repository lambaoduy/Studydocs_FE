package com.example.finalexam.state

import com.example.finalexam.entity.University

data class UniversityState(
    val universityList: List<University> = emptyList(),
    val selectedUniversity: University? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
