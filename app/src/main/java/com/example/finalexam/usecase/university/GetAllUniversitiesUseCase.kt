package com.example.finalexam.usecase.university

import com.example.finalexam.data.repository.UniversityRepository
import com.example.finalexam.entity.University

class GetAllUniversitiesUseCase(private val repository: UniversityRepository) {
    suspend operator fun invoke(): List<University> {
        return repository.getAllUniversities()
    }
}