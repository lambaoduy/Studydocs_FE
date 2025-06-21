//package com.example.finalexam.handler
//
//import com.example.finalexam.intent.AuthIntent
//import com.example.finalexam.result.AuthResult
//import com.example.finalexam.usecase.AuthUseCase
//
//// thiện làm: Handler cho Auth (Login/Register)
//class AuthHandler(private val useCase: AuthUseCase) : IntentHandler<AuthIntent, AuthResult> {
//    override fun canHandle(intent: AuthIntent): Boolean = true
//
//    override suspend fun handle(intent: AuthIntent, setResult: (AuthResult) -> Unit) {
//        when (intent) {
//            is AuthIntent.Login -> {
//                setResult(AuthResult.Loading)
//                val result = useCase.login(intent.email, intent.password)
//                setResult(result.fold(
//                    onSuccess = { AuthResult.Success },
//                    onFailure = { AuthResult.Error(it) }
//                ))
//            }
//            is AuthIntent.Register -> {
//                setResult(AuthResult.Loading)
//                val result = useCase.register(intent.username, intent.email, intent.password)
//                setResult(result.fold(
//                    onSuccess = { AuthResult.Success },
//                    onFailure = { AuthResult.Error(it) }
//                ))
//            }
//            is AuthIntent.ForgotPassword -> {
//                setResult(AuthResult.Loading)
//                val result = useCase.forgotPassword(intent.email)
//                setResult(result.fold(
//                    onSuccess = { AuthResult.Success },
//                    onFailure = { AuthResult.Error(it) }
//                ))
//            }
//            is AuthIntent.ClearError -> setResult(AuthResult.Error(Exception("")))
//            is AuthIntent.UpdateProfile -> {
//                setResult(AuthResult.Loading)
//                // Cập nhật thông tin user lên Firebase
//                val updateResult = useCase.updateProfile(intent.user)
//                if (updateResult.isSuccess) {
//                    // Sau khi update thành công, lấy lại thông tin user mới nhất từ Firebase
//                    val profileResult = useCase.getProfile(intent.user.userId)
//                    setResult(profileResult.fold(
//                        onSuccess = { AuthResult.ProfileUpdated(it) },
//                        onFailure = { AuthResult.Error(it) }
//                    ))
//                } else {
//                    setResult(AuthResult.Error(updateResult.exceptionOrNull() ?: Exception("Lỗi cập nhật")))
//                }
//            }
//            is AuthIntent.LoadProfile -> {
//                setResult(AuthResult.Loading)
//                // Lấy thông tin user từ Firebase
//                val profileResult = useCase.getProfile(intent.userId)
//                setResult(profileResult.fold(
//                    onSuccess = { AuthResult.ProfileLoaded(it) },
//                    onFailure = { AuthResult.Error(it) }
//                ))
//            }
//        }
//    }
//}