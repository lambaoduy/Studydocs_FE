package com.example.finalexam.usecase.university

import com.example.finalexam.data.repository.UniversityRepository

class AddSubjectUseCase(private val repository: UniversityRepository) {
    suspend operator fun invoke(universityId: String, subject: String): Boolean {
        return repository.addSubject(universityId, subject)
    }
}