package com.example.finalexam.usecase.follow

import com.example.finalexam.data.api.FollowApi
import com.example.finalexam.data.request.ToggleNotifyRequest
import com.example.finalexam.network.RetrofitClient

class ToggleNotifyEnableUseCase {
    private val followApi: FollowApi = RetrofitClient.createApi(FollowApi::class.java)
    suspend fun invoke(followingId: String, notifyEnables: Boolean) {
        val response =
            followApi.toggleNotify(ToggleNotifyRequest(followingId, notifyEnables))
        if (response.status != 200) {
            throw Exception(response.message)
        }
    }
}