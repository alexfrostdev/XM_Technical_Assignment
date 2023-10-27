package com.example.xmtechnicalassignment.presentation.ui.questions

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.xmtechnicalassignment.data.remote.entity.Question

sealed interface UiStatus {
    object Loading : UiStatus
    object Success : UiStatus
    object Error : UiStatus
}

data class QuestionState(
    val status: UiStatus? = null,
    val question: Question,
    val answer: String = "",
    val submittedAnswer: String = "",
)

data class QuestionsScreenState(
    val questions: SnapshotStateList<QuestionState> = SnapshotStateList(),
    val submittedQuestions: Int = 0
)