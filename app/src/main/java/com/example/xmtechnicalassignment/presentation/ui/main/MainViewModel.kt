package com.example.xmtechnicalassignment.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xmtechnicalassignment.data.di.dataComponent
import com.example.xmtechnicalassignment.data.repository.QuestionsRepository
import com.example.xmtechnicalassignment.presentation.component.MESSAGE_DELAY
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val questionsRepository: QuestionsRepository = dataComponent().questionsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<QuestionsState>(QuestionsState.None)
    val state = _state.asStateFlow()

    fun onStartSurveyClick() {
        viewModelScope.launch {
            _state.emit(QuestionsState.Loading)
            val result = questionsRepository.getQuestions()

            if (result.isSuccess) {
                val questions = result.getOrThrow()

                when {
                    questions.isEmpty() -> {
                        _state.emit(QuestionsState.Empty)
                        delay(MESSAGE_DELAY)
                        _state.emit(QuestionsState.None)
                    }

                    else -> _state.emit(QuestionsState.Success)
                }
            } else {
                _state.emit(QuestionsState.Error)
                delay(MESSAGE_DELAY)
                _state.emit(QuestionsState.None)
            }
        }
    }

    fun setNoneState() {
        viewModelScope.launch {
            _state.emit(QuestionsState.None)
        }
    }

}

sealed interface QuestionsState {
    object None : QuestionsState
    object Loading : QuestionsState
    object Error : QuestionsState
    object Empty : QuestionsState
    object Success : QuestionsState
}
