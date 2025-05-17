package com.example.finalexam.handler

//file này duy viết
interface IntentHandler<I : Any, R: Any> {
    fun canHandle(intent: I): Boolean
    suspend fun handle(intent: I, setResult: (R) -> Unit)
}
