package com.example.finalexam.handler.university

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.UniversityIntent
import com.example.finalexam.result.UniversityResult
import com.example.finalexam.usecase.university.GetAllUniversitiesUseCase

class LoadUniversitiesHandler(
    private val getAllUniversitiesUseCase: GetAllUniversitiesUseCase
) : IntentHandler<UniversityIntent, UniversityResult> {

    override fun canHandle(intent: UniversityIntent): Boolean = intent is UniversityIntent.LoadUniversities

    override suspend fun handle(
        intent: UniversityIntent,
        setResult: (UniversityResult) -> Unit
    ) {
        try {
            setResult(UniversityResult.Loading)
            val universities = getAllUniversitiesUseCase()
            setResult(UniversityResult.UniversitiesLoaded(universities))
        } catch (e: Exception) {
            setResult(UniversityResult.Error(e.message ?: "Unknown error"))
        }
    }
} 