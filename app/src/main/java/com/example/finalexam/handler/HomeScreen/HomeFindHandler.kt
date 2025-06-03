package com.example.finalexam.handler.HomeScreen

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.HomeIntent
import com.example.finalexam.result.HomeResult


class HomeFindHandler : IntentHandler<HomeIntent, HomeResult> {
    override fun canHandle(intent: HomeIntent): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun handle(intent: HomeIntent, setResult: (HomeResult) -> Unit) {
        TODO("Not yet implemented")
    }

}