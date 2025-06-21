package com.example.finalexam.handler

import com.example.finalexam.intent.UpdateProfileIntent
import com.example.finalexam.result.UpdateProfileResult
import com.example.finalexam.usecase.auth.UpdateProfileUseCase
import com.example.finalexam.handler.IntentHandler

// Handler cho chức năng cập nhật profile
class UpdateProfileHandler(private val useCase: UpdateProfileUseCase) : IntentHandler<UpdateProfileIntent, UpdateProfileResult> {
    override fun canHandle(intent: UpdateProfileIntent): Boolean = true

    override suspend fun handle(intent: UpdateProfileIntent, setResult: (UpdateProfileResult) -> Unit) {
        when (intent) {
            is UpdateProfileIntent.Update -> {
                setResult(UpdateProfileResult.Loading)
                // Cập nhật thông tin user lên Firebase
                val updateResult = useCase.updateProfile(intent.user)
                if (updateResult.isSuccess) {
                    // Sau khi update thành công, lấy lại thông tin user mới nhất từ Firebase
                    val profileResult = useCase.getProfile(intent.user.userId)
                    setResult(profileResult.fold(
                        onSuccess = { UpdateProfileResult.Updated(it) },
                        onFailure = { UpdateProfileResult.Error(it) }
                    ))
                } else {
                    setResult(UpdateProfileResult.Error(updateResult.exceptionOrNull() ?: Exception("Lỗi cập nhật")))
                }
            }
            is UpdateProfileIntent.Load -> {
                setResult(UpdateProfileResult.Loading)
                // Lấy thông tin user từ Firebase
                val profileResult = useCase.getProfile(intent.userId)
                setResult(profileResult.fold(
                    onSuccess = { UpdateProfileResult.Loaded(it) },
                    onFailure = { UpdateProfileResult.Error(it) }
                ))
            }
            is UpdateProfileIntent.ClearError -> setResult(UpdateProfileResult.Error(Exception("")))
        }
    }
}
