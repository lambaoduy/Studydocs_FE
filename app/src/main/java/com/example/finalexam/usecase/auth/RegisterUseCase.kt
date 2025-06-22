package com.example.finalexam.usecase.auth

import com.example.finalexam.config.FirebaseConfig
import com.example.finalexam.data.api.UserApi
import com.example.finalexam.data.datastore.FcmTokenManager
import com.example.finalexam.data.datastore.UserPreferences
import com.example.finalexam.data.request.RegisterRequest
import com.example.finalexam.data.response.BaseResponse
import com.example.finalexam.entity.User
import com.example.finalexam.network.RetrofitClient
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await
import retrofit2.HttpException

class RegisterUseCase {
    private val firebaseAuth = FirebaseConfig.firebaseAuth
    private val userApi = RetrofitClient.createApi(UserApi::class.java)
    suspend fun invoke(fullName: String, email: String, password: String): Result<Unit> {
        var createdFirebaseUser = false

        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user
                ?: return Result.failure(Exception("Không thể lấy thông tin người dùng"))
            createdFirebaseUser = true

            val token = user.getIdToken(true).await().token ?: ""
            val uid = user.uid
            // Lưu local
            UserPreferences.saveUser(uid, token)

            val response = userApi.register(RegisterRequest(fullName, email))
            if (response.status != 200) throw Exception(response.message)
            FcmTokenManager.syncTokenIfNeeded(userApi)
            UserPreferences.saveUserObject(User(fullName, email, null))
            Result.success(Unit)
        } catch (e: Exception) {
            // Rollback nếu đã tạo Firebase user
            if (createdFirebaseUser) {
                try {
                    firebaseAuth.currentUser?.delete()?.await()
                } catch (ex: Exception) {
                    println("Error deleting Firebase user: ${ex.message}")
                }
            }

            // Rollback local
            try {
                UserPreferences.clear()
            } catch (_: Exception) {
            }

            // Xử lý thông báo lỗi cuối cùng
            val message = when (e) {
                is FirebaseAuthUserCollisionException -> "Tài khoản đã tồn tại."
                is FirebaseAuthInvalidCredentialsException -> "Email không hợp lệ hoặc mật khẩu quá yếu."
                is FirebaseNetworkException -> "Lỗi kết nối mạng. Vui lòng thử lại sau."
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    Gson().fromJson(errorBody, BaseResponse::class.java)?.message
                        ?: "Lỗi hệ thống: ${e.code()} ${e.message()}"
                }

                else -> "Lỗi không xác định: ${e.message}"
            }

            Result.failure(Exception(message))
        }
    }

}