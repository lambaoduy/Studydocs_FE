package com.example.finalexam.handler.university

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.UniversityIntent
import com.example.finalexam.result.UniversityResult
import com.example.finalexam.entity.University

class SelectSubjectHandler : IntentHandler<UniversityIntent, UniversityResult> {

    override fun canHandle(intent: UniversityIntent): Boolean = intent is UniversityIntent.SelectSubject

    override suspend fun handle(
        intent: UniversityIntent,
        setResult: (UniversityResult) -> Unit
    ) {
        val selectIntent = intent as UniversityIntent.SelectSubject
        // Tạo một university tạm thời để pass qua result
        // Logic thực tế sẽ được xử lý trong reducer
        val tempUniversity = com.example.finalexam.entity.University(
            id = selectIntent.universityId,
            name = "",
            subjects = emptyList(),
            selectedSubjectIndex = selectIntent.subjectIndex
        )
        setResult(UniversityResult.SubjectSelected(tempUniversity))
    }
} 