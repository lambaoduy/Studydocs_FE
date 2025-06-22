package com.example.finalexam.handler.profile

import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.intent.ProfileIntent
import com.example.finalexam.result.ProfileResult
import com.example.finalexam.usecase.profile.UpdateProfileUseCase

// Handler cho chức năng cập nhật profile
class UpdateProfileHandler :
    IntentHandler<ProfileIntent, ProfileResult> {
    private val useCase = UpdateProfileUseCase()
    override fun canHandle(intent: ProfileIntent): Boolean = intent is ProfileIntent.Update

    override suspend fun handle(intent: ProfileIntent, setResult: (ProfileResult) -> Unit) {
        val intent = intent as ProfileIntent.Update
        setResult(ProfileResult.Loading)
        // Cập nhật thông tin user lên Firebase
        val updateResult = useCase.invoke(intent.user)
        if (updateResult.isSuccess) {
            setResult(ProfileResult.Success)
        } else {
            setResult(
                ProfileResult.Error(updateResult.exceptionOrNull()?.message ?: "Unknown error")
            )
        }
    }
}