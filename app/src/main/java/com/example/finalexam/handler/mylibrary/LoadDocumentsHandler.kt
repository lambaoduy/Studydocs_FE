package com.example.finalexam.handler.mylibrary

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.MyLibraryIntent
import com.example.finalexam.result.MyLibraryResult
import com.example.finalexam.usecase.mylibrary.LoadDocumentsUseCase

class LoadDocumentsHandler(
    private val loadDocumentsUseCase: LoadDocumentsUseCase
) : IntentHandler<MyLibraryIntent, MyLibraryResult> {
    
    override fun canHandle(intent: MyLibraryIntent): Boolean = 
        intent is MyLibraryIntent.Refresh

    override suspend fun handle(
        intent: MyLibraryIntent,
        setResult: (MyLibraryResult) -> Unit
    ) {
        setResult(MyLibraryResult.Loading)
        setResult(loadDocumentsUseCase.invoke())
    }
} 