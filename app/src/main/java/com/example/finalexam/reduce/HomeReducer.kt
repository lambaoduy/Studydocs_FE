package com.example.finalexam.reduce


import com.example.finalexam.result.HomeResult
import com.example.finalexam.state.HomeState

//file này duy viết
class HomeReducer {
    //    hàm reduce để đóng gói dữ liệu thành state
    fun reduce(state: HomeState, result: HomeResult): HomeState = when (result) {

        is HomeResult.Find -> TODO()//hàm tìm kiếm chưa làm nên tạm để đây
        is HomeResult.LoadByUserID -> state.copy(result.data)
        is HomeResult.Error -> state.copy(result.Error)
        HomeResult.Loading -> TODO()
    }

}