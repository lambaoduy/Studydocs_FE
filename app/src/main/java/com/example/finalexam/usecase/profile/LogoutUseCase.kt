package com.example.finalexam.usecase.profile

import com.example.finalexam.data.api.UserApi
import com.example.finalexam.data.datastore.UserPreferences
import com.example.finalexam.network.RetrofitClient
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LogoutUseCase {
    private val userApi = RetrofitClient.createApi(UserApi::class.java)
    suspend fun invoke(): Result<Unit> {
        return try {
            val fcmToken = UserPreferences.getFcmToken()
            if (fcmToken != null) {
                userApi.deleteFcmToken(fcmToken)
                UserPreferences.clear()
            }
            Result.success(Unit)
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            return Result.failure(Exception("Email hoặc mật khẩu không đúng"))
        } catch (e: FirebaseAuthInvalidUserException) {
            return Result.failure(Exception("Tài khoản không tồn tại"))
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}