package com.example.finalexam.intent
//file này duy viết
sealed class HomeIntent {
    object LoadTodos : HomeIntent()
    data class FindTodo(val search: String):HomeIntent()
//    data class AddTodo(val title: String) : HomeIntent()
//    data class RemoveTodo(val id: Int) : HomeIntent()
}