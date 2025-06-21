    package com.example.finalexam.usecase.auth

    import com.example.finalexam.config.FirebaseConfig
    import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
    import com.google.firebase.auth.FirebaseAuthInvalidUserException
    import kotlinx.coroutines.tasks.await

    class ForgotPasswordUseCase {
        private val firebaseAuth = FirebaseConfig.firebaseAuth
        suspend fun invoke(email: String): Result<Unit> {
            return try {
                firebaseAuth.sendPasswordResetEmail(email).await()
                Result.success(Unit)
            } catch (e: FirebaseAuthInvalidUserException) {
                Result.failure(Exception("Tài khoản không tồn tại. Vui lòng kiểm tra lại email."))
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                Result.failure(Exception("Định dạng email không hợp lệ."))
            } catch (e: Exception) {
                Result.failure(Exception("Đã xảy ra lỗi khi gửi email khôi phục mật khẩu: ${e.message}"))
            }
        }
    }