package com.example.finalexam.reduce


import com.example.finalexam.result.HomeResult
import com.example.finalexam.state.HomeState

//file này duy viết
class HomeReducer {
    fun reduce(state: HomeState, result: HomeResult): HomeState = when (result) {

        is HomeResult.Loading -> {
            state.copy(
                isLoading = true,
                error = null // clear error khi bắt đầu loading mới
            )
        }

        is HomeResult.Find -> {
            state.copy(
                listDocument = result.data,
                isLoading = false,
                error = null,
                keyword = null, // reset các filter khác nếu cần
                school = null,
                subject = null
            )
        }

        is HomeResult.FindBySubject -> {
            state.copy(
                listDocument = result.data,
                isLoading = false,
                error = null,
                subject = result.data.firstOrNull()?.subject ?: state.subject,
                keyword = null,
                school = null
            )
        }

        is HomeResult.FindByUniversity -> {
            state.copy(
                listDocument = result.data,
                isLoading = false,
                error = null,
                school = result.data.firstOrNull()?.university ?: state.school,
                keyword = null,
                subject = null
            )
        }

        is HomeResult.LoadByUserID -> {
            state.copy(
                listDocument = result.data,
                isLoading = false,
                error = null,
                keyword = null,
                school = null,
                subject = null
            )
        }

        is HomeResult.Error -> {
            state.copy(
                isLoading = false,
                error = result.throwable.message
            )
        }
    }
}
