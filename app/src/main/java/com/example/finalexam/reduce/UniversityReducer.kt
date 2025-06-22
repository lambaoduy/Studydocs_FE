package com.example.finalexam.reduce

import com.example.finalexam.result.UniversityResult
import com.example.finalexam.state.UniversityState

class UniversityReducer {
    fun reduce(state: UniversityState, result: UniversityResult): UniversityState =
        when (result) {
            is UniversityResult.Loading -> state.copy(isLoading = true, error = null)
            
            is UniversityResult.UniversitiesLoaded -> state.copy(
                universityList = result.universities,
                selectedUniversity = if (state.selectedUniversity == null) result.universities.firstOrNull() else state.selectedUniversity,
                isLoading = false,
                error = null
            )
            
            is UniversityResult.SubjectAdded -> state.copy(
                isLoading = false,
                error = if (result.success) null else "Môn học đã tồn tại"
            )
            
            is UniversityResult.UniversitySelected -> {
                val selectedUni = state.universityList.find { it.id == result.university.id }
                state.copy(
                    selectedUniversity = selectedUni,
                    isLoading = false,
                    error = null
                )
            }
            
            is UniversityResult.SubjectSelected -> {
                val currentUni = state.selectedUniversity
                val updatedUni = currentUni?.copy(selectedSubjectIndex = result.university.selectedSubjectIndex)
                state.copy(
                    selectedUniversity = updatedUni,
                    isLoading = false,
                    error = null
                )
            }
            
            is UniversityResult.Error -> state.copy(
                isLoading = false,
                error = result.message
            )
        }
} 