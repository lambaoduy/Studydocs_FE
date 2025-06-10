package com.example.finalexam.handler

import com.example.finalexam.intent.AuthIntent
import com.example.finalexam.result.AuthResult
import com.example.finalexam.usecase.AuthUseCase

// Xử lý các intent liên quan đến Auth (Login, Register, ForgotPassword, Get/UpdateProfile)
// Gửi dữ liệu tới backend qua UseCase, nhận kết quả và chuyển thành AuthResult cho ViewModel
class AuthHandler(private val useCase: AuthUseCase) : IntentHandler<AuthIntent, AuthResult> {
    override fun canHandle(intent: AuthIntent): Boolean = true

    override suspend fun handle(intent: AuthIntent, setResult: (AuthResult) -> Unit) {
        when (intent) {
            is AuthIntent.Login -> {
                setResult(AuthResult.Loading)
                // Gửi dữ liệu đăng nhập tới backend
                val result = useCase.login(intent.email, intent.password)
                setResult(result.fold(
                    onSuccess = { AuthResult.Success },
                    onFailure = { AuthResult.Error(it) }
                ))
            }
            is AuthIntent.Register -> {
                setResult(AuthResult.Loading)
                // Gửi dữ liệu đăng ký tới backend
                val result = useCase.register(intent.username, intent.email, intent.password)
                setResult(result.fold(
                    onSuccess = { AuthResult.Success },
                    onFailure = { AuthResult.Error(it) }
                ))
            }
            is AuthIntent.ForgotPassword -> {
                setResult(AuthResult.Loading)
                // Gửi email quên mật khẩu tới backend
                val result = useCase.forgotPassword(intent.email)
                setResult(result.fold(
                    onSuccess = { AuthResult.Success },
                    onFailure = { AuthResult.Error(it) }
                ))
            }
            is AuthIntent.GetProfile -> {
                setResult(AuthResult.Loading)
                // Lấy thông tin cá nhân từ backend
                try {
                    val api = com.example.finalexam.network.RetrofitClient.createApi(com.example.finalexam.data.api.UserApi::class.java)
                    val response = api.getProfile(intent.userId)
                    setResult(AuthResult.ProfileLoaded(response.data))
                } catch (e: Exception) {
                    setResult(AuthResult.Error(e))
                }
            }
            is AuthIntent.UpdateProfile -> {
                setResult(AuthResult.Loading)
                // Gửi dữ liệu chỉnh sửa thông tin cá nhân tới backend
                try {
                    val api = com.example.finalexam.network.RetrofitClient.createApi(com.example.finalexam.data.api.UserApi::class.java)
                    val req = com.example.finalexam.data.request.EditProfileRequest(
                        username = intent.username,
                        email = intent.email,
                        avatarUrl = intent.avatarUrl,
                        phone = intent.phone,
                        bio = intent.bio
                    )
                    val response = api.updateProfile(intent.userId, req)
                    setResult(AuthResult.ProfileUpdated(response.data))
                } catch (e: Exception) {
                    setResult(AuthResult.Error(e))
                }
            }
            is AuthIntent.ClearError -> setResult(AuthResult.Error(Exception("")))
        }
    }
} 