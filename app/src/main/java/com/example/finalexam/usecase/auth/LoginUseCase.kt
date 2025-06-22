package com.example.finalexam.usecase.auth

import com.example.finalexam.config.FirebaseConfig
import com.example.finalexam.data.api.UserApi
import com.example.finalexam.data.datastore.FcmTokenManager
import com.example.finalexam.data.datastore.UserPreferences
import com.example.finalexam.network.RetrofitClient
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.tasks.await

class LoginUseCase {
    private val firebaseAuth = FirebaseConfig.firebaseAuth
    private val userApi = RetrofitClient.createApi(UserApi::class.java)
    suspend fun invoke(email: String, password: String): Result<Unit> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = result.user!!
            val token = user.getIdToken(true).await().token ?: ""
            val uid = user.uid
            UserPreferences.saveUser(uid, token)
            FcmTokenManager.syncTokenIfNeeded(userApi)
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