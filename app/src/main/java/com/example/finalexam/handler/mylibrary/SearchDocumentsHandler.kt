package com.example.finalexam.handler.mylibrary

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.MyLibraryIntent
import com.example.finalexam.result.MyLibraryResult
import com.example.finalexam.state.MyLibraryState
import com.example.finalexam.usecase.mylibrary.SearchDocumentsUseCase
import kotlinx.coroutines.flow.StateFlow

class SearchDocumentsHandler(
    private val state: StateFlow<MyLibraryState>
) : IntentHandler<MyLibraryIntent, MyLibraryResult> {

    override fun canHandle(intent: MyLibraryIntent): Boolean = 
        intent is MyLibraryIntent.FindWithFilters

    override suspend fun handle(
        intent: MyLibraryIntent,
        setResult: (MyLibraryResult) -> Unit
    ) {
        if (intent is MyLibraryIntent.FindWithFilters) {
            val filters = intent
            val usecase = SearchDocumentsUseCase()

            var mydoc = usecase.findByFilters(
                keyword = filters.keyword,
                school = filters.university,
                subject = filters.subject,
                cacheList =state.value.documents

            )
            var savedoc=usecase.findByFilters(
                keyword = filters.keyword,
                school = filters.university,
                subject = filters.subject,
                cacheList =state.value.documentsSave

            )

            setResult(MyLibraryResult.SearchSuccess(mydoc,savedoc))}
    }
} 