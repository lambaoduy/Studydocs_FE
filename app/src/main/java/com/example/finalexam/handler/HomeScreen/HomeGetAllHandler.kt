package com.example.finalexam.handler.HomeScreen

import com.example.finalexam.data.dao.document.DocumentDao
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.HomeIntent
import com.example.finalexam.result.HomeResult
import com.example.finalexam.usecase.homescreen.HomeLoadDataUseCase

class HomeGetAllHandler  (documentDao: DocumentDao) : IntentHandler<HomeIntent, HomeResult> {
        private val usecase= HomeLoadDataUseCase(documentDao)
        override fun canHandle(intent: HomeIntent): Boolean = intent is HomeIntent.GetAllTodo

        override suspend fun handle(intent: HomeIntent, setResult: (HomeResult) -> Unit) {
            // Ép kiểu intent để keyword
            if (intent is HomeIntent.GetAllTodo) {

                println("➡️ [HomeGetAllHandler] Nhận intent: GetAllTodo")

                val documents = usecase.getAll()

                println("✅ [HomeGetAllHandler] Lấy ${documents.size} documents từ usecase")

                // Trả kết quả về
                setResult(HomeResult.Find(documents))
            }
        }
    }