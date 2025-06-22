package com.example.finalexam.handler.university

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.UniversityIntent
import com.example.finalexam.result.UniversityResult
import com.example.finalexam.entity.University

class SelectUniversityHandler : IntentHandler<UniversityIntent, UniversityResult> {

    override fun canHandle(intent: UniversityIntent): Boolean = intent is UniversityIntent.SelectUniversity

    override suspend fun handle(
        intent: UniversityIntent,
        setResult: (UniversityResult) -> Unit
    ) {
        val selectIntent = intent as UniversityIntent.SelectUniversity
        // Tạo một university tạm thời để pass qua result
        // Logic thực tế sẽ được xử lý trong reducer
        val tempUniversity = University(
            id = selectIntent.universityId,
            name = "",
            subjects = emptyList()
        )
        setResult(UniversityResult.UniversitySelected(tempUniversity))
    }
} 