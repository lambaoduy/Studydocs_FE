//package com.example.finalexam.usecase
//
//import com.example.finalexam.api.AuthApi
//import com.example.finalexam.dto.ForgotPasswordDTO
//import com.example.finalexam.dto.LoginDTO
//import com.example.finalexam.dto.RegisterDTO
//import com.google.firebase.auth.FirebaseAuth
//import kotlinx.coroutines.tasks.await
//
//class AuthUseCase(private val authApi: AuthApi) {
//    suspend fun login(email: String, password: String): Result<Unit> {
//        return try {
//            val authResult = FirebaseAuth.getInstance()
//                .signInWithEmailAndPassword(email, password)
//                .await()
//            val idToken = authResult.user?.getIdToken(true)?.await()?.token
//                ?: return Result.failure(Exception("Không lấy được token"))
//            // Gửi token lên backend
//            val response = authApi.login(LoginDTO(idToken))
//            if (response.success) Result.success(Unit)
//            else Result.failure(Exception(response.message ?: "Lỗi backend"))
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
//
//    suspend fun register(username: String, email: String, password: String, avatarUrl: String? = null): Result<Unit> {
//        return try {
//            // Đăng ký tài khoản với Firebase Auth
//            val authResult = FirebaseAuth.getInstance()
//                .createUserWithEmailAndPassword(email, password)
//                .await()
//            // Lấy UID do Firebase cấp
//            val userId = authResult.user?.uid ?: return Result.failure(Exception("Không lấy được UID từ Firebase"))
//            // Đóng gói thông tin người dùng gửi lên backend để lưu vào Firestore
//            val registerDTO = RegisterDTO(
//                userId = userId,
//                username = username,
//                email = email,
//                avatarUrl = avatarUrl // Có thể null nếu chưa có ảnh
//            )
//            // Gửi thông tin lên backend
//            val response = authApi.register(registerDTO)
//            if (response.success) Result.success(Unit)
//            else Result.failure(Exception(response.message ?: "Lỗi backend"))
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
//
//    suspend fun forgotPassword(email: String): Result<Unit> {
//        return try {
//            // Gửi email reset password qua Firebase
//            FirebaseAuth.getInstance().sendPasswordResetEmail(email).await()
//            // Gửi email lên backend nếu cần (nếu BE cần log hoặc xử lý gì thêm)
//            val response = authApi.forgotPassword(ForgotPasswordDTO(email))
//            if (response.success) Result.success(Unit)
//            else Result.failure(Exception(response.message ?: "Lỗi backend"))
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
//
//    // Cập nhật thông tin user lên Firestore
//    suspend fun updateProfile(user: com.example.finalexam.entity.User): Result<Unit> {
//        return try {
//            val db = com.google.firebase.firestore.FirebaseFirestore.getInstance()
//            // Lưu user vào collection "users" với document id là userId
//            db.collection("users").document(user.userId).set(user).await()
//            Result.success(Unit)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
//
//    // Lấy thông tin user từ Firestore
//    suspend fun getProfile(userId: String): Result<com.example.finalexam.entity.User> {
//        return try {
//            val db = com.google.firebase.firestore.FirebaseFirestore.getInstance()
//            val snapshot = db.collection("users").document(userId).get().await()
//            val user = snapshot.toObject(com.example.finalexam.entity.User::class.java)
//                ?: return Result.failure(Exception("Không tìm thấy user"))
//            Result.success(user)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//}