package com.example.finalexam.handler

import com.example.finalexam.intent.UpdateIntent
import com.example.finalexam.result.UpdateResult
import com.example.finalexam.usecase.AuthUseCase
import com.example.finalexam.handler.IntentHandler

// Handler cho chức năng cập nhật profile
class UpdateHandler(private val useCase: AuthUseCase) : IntentHandler<UpdateIntent, UpdateResult> {
    override fun canHandle(intent: UpdateIntent): Boolean = true

    override suspend fun handle(intent: UpdateIntent, setResult: (UpdateResult) -> Unit) {
        when (intent) {
            is UpdateIntent.UpdateProfile -> {
                setResult(UpdateResult.Loading)
                // Cập nhật thông tin user lên Firebase
                val updateResult = useCase.updateProfile(intent.user)
                if (updateResult.isSuccess) {
                    // Sau khi update thành công, lấy lại thông tin user mới nhất từ Firebase
                    val profileResult = useCase.getProfile(intent.user.userId)
                    setResult(profileResult.fold(
                        onSuccess = { UpdateResult.ProfileUpdated(it) },
                        onFailure = { UpdateResult.Error(it) }
                    ))
                } else {
                    setResult(UpdateResult.Error(updateResult.exceptionOrNull() ?: Exception("Lỗi cập nhật")))
                }
            }
            is UpdateIntent.LoadProfile -> {
                setResult(UpdateResult.Loading)
                // Lấy thông tin user từ Firebase
                val profileResult = useCase.getProfile(intent.userId)
                setResult(profileResult.fold(
                    onSuccess = { UpdateResult.ProfileLoaded(it) },
                    onFailure = { UpdateResult.Error(it) }
                ))
            }
            is UpdateIntent.ClearError -> setResult(UpdateResult.Error(Exception("")))
        }
    }
}

