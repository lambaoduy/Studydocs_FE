package com.example.finalexam.usecase

/ UseCase cho Auth: xử lý logic gọi API thực tế (nên thay thế TODO bằng gọi API thực)
// Nên truyền AuthApi vào constructor để dễ test/mock
class AuthUseCase(private val api: AuthApi) {
    suspend fun login(email: String, password: String): Result<String> {
        // Gửi dữ liệu đăng nhập tới backend, nhận về userId/token
        return try {
            val response = api.login(Login(email, password))
            if (response.status == 200) Result.success(response.data)
            else Result.failure(Exception(response.message))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(email: String, password: String): Result<String> {
        // Gửi dữ liệu đăng ký tới backend, nhận về userId/token
        return try {
            val response = api.register(Register(email, password))
            if (response.status == 200) Result.success(response.data)
            else Result.failure(Exception(response.message))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout(): Result<Unit> {
        // Gọi API logout, không cần dữ liệu trả về
        return try {
            val response = api.logout()
            if (response.status == 200) Result.success(Unit)
            else Result.failure(Exception(response.message))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserInfo(): Result<User> {
        // Gọi API lấy thông tin người dùng
        return try {
            val response = api.getUserInfo()
            if (response.status == 200) Result.success(response.data)
            else Result.failure(Exception(response.message))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUserInfo(user: User): Result<User> {
        // Gọi API cập nhật thông tin người dùng
        return try {
            val response = api.updateUserInfo(user)
            if (response.status == 200) Result.success(response.data)
            else Result.failure(Exception(response.message))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun changePassword(oldPassword: String, newPassword: String): Result<Unit> {
        // Gọi API đổi mật khẩu
        return try {
            val response = api.changePassword(ChangePassword(oldPassword, newPassword))
            if (response.status == 200) Result.success(Unit)
            else Result.failure(Exception(response.message))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun resetPassword(email: String): Result<Unit> {
        // Gọi API gửi email reset mật khẩu
        return try {
            val response = api.resetPassword(ResetPassword(email))
            if (response.status == 200) Result.success(Unit)
            else Result.failure(Exception(response.message))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun verifyEmail(token: String): Result<Unit> {
        // Gọi API xác thực email
        return try {
            val response = api.verifyEmail(VerifyEmail(token))
            if (response.status == 200) Result.success(Unit)
            else Result.failure(Exception(response.message))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendVerificationEmail(email: String): Result<Unit> {
        // Gọi API gửi email xác thực
        return try {
            val response = api.sendVerificationEmail(SendVerificationEmail(email))
            if (response.status == 200) Result.success(Unit)
            else Result.failure(Exception(response.message))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteAccount(): Result<Unit> {
        // Gọi API xóa tài khoản
        return try {
            val response = api.deleteAccount()
            if (response.status == 200) Result.success(Unit)
            else Result.failure(Exception(response.message))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

} 