package com.example.finalexam.handler

import com.example.finalexam.intent.HomeIntent
import com.example.finalexam.result.HomeResult
import com.example.finalexam.usecase.HomeFindUseCase

//file này duy viết
interface IntentHandler<I : Any> {
    fun canHandle(intent: I): Boolean
    suspend fun handle(intent: I, setResult: (HomeResult) -> Unit)
}
class HomeFindHandler(private val useCase: HomeFindUseCase) : IntentHandler<HomeIntent> {
    override fun canHandle(intent: HomeIntent): Boolean = intent is HomeIntent.LoadTodos
    override suspend fun handle(intent: HomeIntent, setResult: (HomeResult) -> Unit) {
        setResult(HomeResult.Loading)
        try {
            val todos = useCase()//todos ở đây chính là dữ liệu trả về khi usecase xử lý
            setResult(HomeResult.Find(todos))
        } catch (e: Exception) {
            setResult(HomeResult.Error(e))
        }
    }
}