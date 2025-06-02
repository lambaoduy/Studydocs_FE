package com.example.finalexam.reduce

import androidx.room.util.copy
import com.example.finalexam.result.HomeResult
import com.example.finalexam.state.HomeState

//file này duy viết
class HomeReducer {
//    hàm reduce để đóng gói dữ liệu thành state
    fun reduce(state: HomeState, result: HomeResult): HomeState = when (result) {
//        is TodoResult.Loading -> state.copy(isLoading = true, error = null)
//        is TodoResult.Loaded -> state.copy(isLoading = false, todos = result.todos)
//        is TodoResult.Added -> state.copy(isLoading = false, todos = result.todos)
//        is TodoResult.Removed -> state.copy(isLoading = false, todos = result.todos)
//        is TodoResult.Error -> state.copy(isLoading = false, error = result.throwable.message)
        is HomeResult.Error -> state.copy(isLoading = false, error = result.throwable.message)//quăng lỗi ra ở đây
        is HomeResult.Find -> TODO()//hàm tìm kiếm chưa làm nên tạm để đây
        HomeResult.Loading -> TODO()
    }

}