package com.example.finalexam.usecase.follow

import android.util.Log
import com.example.finalexam.data.api.FollowApi
import com.example.finalexam.data.request.UnfollowRequest
import com.example.finalexam.network.RetrofitClient
import com.google.gson.Gson
import retrofit2.HttpException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.processNextEventInCurrentThread
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat

class UnfollowUseCase {
    private val followApi: FollowApi = RetrofitClient.createApi(FollowApi::class.java)
    private val logTag = "UnfollowUseCase"
    private val gson = Gson()
    suspend fun invoke(followingId: String): Result<String> {
        return try {
            val response = followApi.unFollow(followingId)
            if (response.status == 200) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }
    suspend fun invokeByFollowingId(followingId: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            Log.d(logTag, "Calling /user/unfollow with followingId: $followingId")
            val request = UnfollowRequest(followingId = followingId)
            Log.d(logTag, "Request: ${gson.toJson(request)}")
            val response = followApi.unFollowByTarget(request)
            Log.d(logTag, "Response: status=${response.status}, data=${response.data}, message=${response.message}")
            // Ghi log vào file
            writeToLogFile(
                "Unfollow /user/unfollow: followingId=$followingId, request=${gson.toJson(request)}, " +
                        "response=status=${response.status}, data=${response.data}, message=${response.message}"
            )
            if (response.status == 200 && response.data != null) {
                Result.success(response.data)
            } else {
                val errorMsg = response.message ?: "Failed to unfollow"
                Log.e(logTag, "Unfollow failed: $errorMsg")
                writeToLogFile("Unfollow failed: $errorMsg")
                Result.failure(Exception(errorMsg))
            }
        } catch (e: HttpException) {
            Log.e(logTag, "HTTP error: ${e.code()} - ${e.message()}", e)
            writeToLogFile("Unfollow HTTP error: ${e.code()} - ${e.message()}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e(logTag, "Unexpected error: ${e.message}", e)
            writeToLogFile("Unfollow unexpected error: ${e.message}")
            Result.failure(e)
        }
    }
    private fun writeToLogFile(message: String) {

    }

}