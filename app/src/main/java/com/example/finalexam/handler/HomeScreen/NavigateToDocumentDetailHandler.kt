package com.example.finalexam.handler.HomeScreen

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.HomeIntent
import com.example.finalexam.result.HomeResult
import com.example.finalexam.usecase.homescreen.HomeLoadDataUseCase
import com.example.finalexam.usecase.homescreen.HomeNavigate

class NavigateToDocumentDetailHandler : IntentHandler<HomeIntent, HomeResult> {


    override fun canHandle(intent: HomeIntent): Boolean = intent is HomeIntent.NavigateToDocDetail

    override suspend fun handle(intent: HomeIntent, setResult: (HomeResult) -> Unit) {
        if (intent is HomeIntent.NavigateToDocDetail) {
            HomeNavigate.toDocDetail(intent.document)
        }
    }

}