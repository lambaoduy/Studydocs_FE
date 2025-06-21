package com.example.finalexam.handler.mylibrary

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.MyLibraryIntent
import com.example.finalexam.result.MyLibraryResult
import com.example.finalexam.usecase.mylibrary.SearchDocumentsUseCase

class SearchDocumentsHandler(
    private val searchDocumentsUseCase: SearchDocumentsUseCase
) : IntentHandler<MyLibraryIntent, MyLibraryResult> {
    
    override fun canHandle(intent: MyLibraryIntent): Boolean = 
        intent is MyLibraryIntent.Search

    override suspend fun handle(
        intent: MyLibraryIntent,
        setResult: (MyLibraryResult) -> Unit
    ) {
        val searchIntent = intent as MyLibraryIntent.Search
        setResult(MyLibraryResult.Loading)
        setResult(searchDocumentsUseCase(searchIntent.query))
    }
} 