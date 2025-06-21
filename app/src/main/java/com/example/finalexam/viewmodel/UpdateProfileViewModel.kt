package com.example.finalexam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexam.handler.UpdateProfileHandler
import com.example.finalexam.intent.UpdateProfileIntent
import com.example.finalexam.state.UpdateProfileState
import com.example.finalexam.usecase.auth.UpdateProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ViewModel cho chức năng cập nhật profile
class UpdateProfileViewModel(private val useCase: UpdateProfileUseCase) : ViewModel() {
    private val handler = UpdateProfileHandler(useCase)
    private val _state = MutableStateFlow(UpdateProfileState())
    val state: StateFlow<UpdateProfileState> = _state

    fun processIntent(intent: UpdateProfileIntent) {
        viewModelScope.launch {
            handler.handle(intent) { result ->
                _state.value = com.example.finalexam.reduce.UpdateProfileReducer.reduce(_state.value, result)
            }
        }
    }
}
