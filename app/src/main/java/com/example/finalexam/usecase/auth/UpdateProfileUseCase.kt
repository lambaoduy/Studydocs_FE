package com.example.finalexam.usecase.auth

import com.example.finalexam.entity.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

// UseCase cho chức năng cập nhật và lấy profile từ Firebase
class UpdateProfileUseCase {
    // Cập nhật thông tin user lên Firestore
    suspend fun updateProfile(user: User): Result<Unit> {
        return try {
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(user.userId).set(user).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Lấy thông tin user từ Firestore
    suspend fun getProfile(userId: String): Result<User> {
        return try {
            val db = FirebaseFirestore.getInstance()
            val snapshot = db.collection("users").document(userId).get().await()
            val user = snapshot.toObject(User::class.java)
                ?: return Result.failure(Exception("Không tìm thấy user"))
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

