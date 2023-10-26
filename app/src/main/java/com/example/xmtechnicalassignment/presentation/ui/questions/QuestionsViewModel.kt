package com.example.xmtechnicalassignment.presentation.ui.questions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

sealed interface QuestionsState {
    object Loading : QuestionsState
    data class Error(val errorMessage: String?, val error: Throwable) : QuestionsState
    data class Success(val item: String) : QuestionsState
}

class QuestionsViewModel() : ViewModel() {

    var questionsState: QuestionsState by mutableStateOf(QuestionsState.Loading)
        private set
}