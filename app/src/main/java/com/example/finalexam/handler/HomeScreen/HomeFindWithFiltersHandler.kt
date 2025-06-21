package com.example.finalexam.handler.HomeScreen

import com.example.finalexam.data.dao.document.DocumentDao
import com.example.finalexam.entity.Document
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.HomeIntent
import com.example.finalexam.result.HomeResult
import com.example.finalexam.usecase.homescreen.HomeLoadDataUseCase

class HomeFindWithFiltersHandler(
    private val documentDao: DocumentDao,
    private val listDocument: List<Document>
) : IntentHandler<HomeIntent, HomeResult> {

    override fun canHandle(intent: HomeIntent): Boolean {
        return intent is HomeIntent.FindWithFilters
    }

    override suspend fun handle(intent: HomeIntent, setResult: (HomeResult) -> Unit) {
        if (intent is HomeIntent.FindWithFilters) {
        val filters = intent
        val usecase = HomeLoadDataUseCase(documentDao)

        var result = usecase.findByFilters(
            keyword = filters.keyword,
            school = filters.school,
            subject = filters.subject,
            cacheList = listDocument
        )

        setResult(HomeResult.Find(result))}
    }
}
