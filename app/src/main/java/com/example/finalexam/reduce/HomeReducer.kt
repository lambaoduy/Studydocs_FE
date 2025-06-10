package com.example.finalexam.reduce


import com.example.finalexam.result.HomeResult
import com.example.finalexam.state.HomeState

//file này duy viết
class HomeReducer {
    //    hàm reduce để đóng gói dữ liệu thành state
    fun reduce(state: HomeState, result: HomeResult): HomeState = when (result) {
        is HomeResult.Find -> {
            println("Find result data: ${result.data}")  // In ra list document tìm được
            state.copy(
                listDocument = result.data,
                isLoading = false,
                error = null
            )

        }
        //đóng gói danh sách document theo userID thành state
        is HomeResult.LoadByUserID -> {
            state.copy(
                listDocument = result.data,
                isLoading = false,
                error = null
            )
        }
        //quăng lỗi theo state
        is HomeResult.Error -> {
            state.copy(
                isLoading = false,
                error = result.throwable.message
            )
        }
        HomeResult.Loading -> {
            state.copy(
                isLoading = true,
                error = null // clear error khi bắt đầu loading mới
            )
        }

    }

}