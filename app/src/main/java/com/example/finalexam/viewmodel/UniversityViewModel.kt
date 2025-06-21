package com.example.finalexam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexam.handler.IntentHandler
import com.example.finalexam.handler.university.AddSubjectHandler
import com.example.finalexam.handler.university.LoadUniversitiesHandler
import com.example.finalexam.handler.university.SelectSubjectHandler
import com.example.finalexam.handler.university.SelectUniversityHandler
import com.example.finalexam.intent.UniversityIntent
import com.example.finalexam.reduce.UniversityReducer
import com.example.finalexam.result.UniversityResult
import com.example.finalexam.state.UniversityState
import com.example.finalexam.usecase.university.AddSubjectUseCase
import com.example.finalexam.usecase.university.GetAllUniversitiesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UniversityViewModel(
    private val getAllUniversitiesUseCase: GetAllUniversitiesUseCase,
    private val addSubjectUseCase: AddSubjectUseCase
) : ViewModel() {

    private val reducer = UniversityReducer()
    private val _state = MutableStateFlow(UniversityState())
    val state = _state.asStateFlow()

    private val handlers: List<IntentHandler<UniversityIntent, UniversityResult>> = listOf(
        LoadUniversitiesHandler(getAllUniversitiesUseCase),
        AddSubjectHandler(addSubjectUseCase),
        SelectUniversityHandler(),
        SelectSubjectHandler()
    )

    fun processIntent(intent: UniversityIntent) {
        viewModelScope.launch {
            handlers.find { it.canHandle(intent) }?.handle(intent) { result ->
                _state.value = reducer.reduce(_state.value, result)
            } ?: println("[WARN] No handler for intent: $intent")
        }
    }
} 