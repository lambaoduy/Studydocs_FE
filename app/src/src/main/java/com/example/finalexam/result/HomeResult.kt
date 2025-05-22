package com.example.finalexam.result
//file này duy viết
sealed class HomeResult {
    object Loading : HomeResult()
//    data class Loaded(val todos: List<Todo>) : HomeResult()
//    data class Added(val todos: List<Todo>) : HomeResult()
//    data class Removed(val todos: List<Todo>) : HomeResult()
    data class Find(val todos: List<String>): HomeResult()
    data class Error(val throwable: Throwable) : HomeResult()

}