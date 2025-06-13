package com.example.finalexam.data.api

import com.example.finalexam.data.response.BaseResponse
import com.example.finalexam.entity.Notification
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface NotificationApi {
    @GET("/user/notifications")
    suspend fun getNotifications(): BaseResponse<List<Notification>>

    @PATCH("/user/notifications/read/{notificationId}")
    suspend fun readNotification(@Path("notificationId") notificationId: String): BaseResponse<Boolean>

    @PATCH("/user/notifications")
    suspend fun readAllNotification(): BaseResponse<Boolean>

    @DELETE("/user/notifications/{notificationId}")
    suspend fun deleteNotification(@Path("notificationId") notificationId: String): BaseResponse<Boolean>

    @DELETE("/user/notifications")
    suspend fun deleteAllNotification(): BaseResponse<Boolean>

}