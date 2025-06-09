package com.example.finalexam.handler.HomeScreen

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.HomeIntent
import com.example.finalexam.result.HomeResult
import com.example.finalexam.usecase.homescreen.HomeLoadDataUseCase

class HomeLoadByUerIDHandler : IntentHandler<HomeIntent, HomeResult> {
    private val usecase=HomeLoadDataUseCase()
    override fun canHandle(intent: HomeIntent): Boolean = intent is HomeIntent.LoadByUserID

    override suspend fun handle(intent: HomeIntent, setResult: (HomeResult) -> Unit) {
        // Ép kiểu intent để lấy userID
        if (intent is HomeIntent.LoadByUserID) {
            val userID = intent.userid // Lấy userID từ intent

            // Gọi usecase, truyền userID vào
            val documents = usecase.loadByUserID(userID)

            // Trả kết quả về
            setResult(HomeResult.LoadByUserID(documents))
        }
    }
}