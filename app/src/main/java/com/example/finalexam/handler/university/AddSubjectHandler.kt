package com.example.finalexam.handler.university

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.UniversityIntent
import com.example.finalexam.result.UniversityResult
import com.example.finalexam.usecase.university.AddSubjectUseCase

class AddSubjectHandler(
    private val addSubjectUseCase: AddSubjectUseCase
) : IntentHandler<UniversityIntent, UniversityResult> {

    override fun canHandle(intent: UniversityIntent): Boolean = intent is UniversityIntent.AddSubject

    override suspend fun handle(
        intent: UniversityIntent,
        setResult: (UniversityResult) -> Unit
    ) {
        val addSubjectIntent = intent as UniversityIntent.AddSubject
        try {
            setResult(UniversityResult.Loading)
            val success = addSubjectUseCase(addSubjectIntent.universityId, addSubjectIntent.subject)
            setResult(UniversityResult.SubjectAdded(success))
        } catch (e: Exception) {
            setResult(UniversityResult.Error(e.message ?: "Unknown error"))
        }
    }
} 