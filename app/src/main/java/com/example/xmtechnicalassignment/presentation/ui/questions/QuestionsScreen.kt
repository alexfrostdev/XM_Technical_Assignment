package com.example.xmtechnicalassignment.presentation.ui.questions

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.xmtechnicalassignment.presentation.ui.theme.XMTechnicalAssignmentTheme
import com.example.xmtechnicalassignment.presentation.component.QuestionsAppBar

@Preview("Empty")
@Composable
fun PreviewEmptyQuestionsScreen() {
    val state = QuestionsState.Loading

    XMTechnicalAssignmentTheme {
        QuestionsContent(state, {})
    }
}

@Preview("Loading")
@Composable
fun PreviewQuestionsScreen() {
    val state = QuestionsState.Loading

    XMTechnicalAssignmentTheme {
        QuestionsContent(state, {})
    }
}

@Composable
fun QuestionsScreen(onUpPress: () -> Unit) {
    val viewModel = viewModel { QuestionsViewModel() }

    val state = viewModel.questionsState

    QuestionsContent(
        state,
        onUpPress = onUpPress,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionsContent(state: QuestionsState, onUpPress: () -> Unit) {
    Scaffold(
        topBar = { QuestionsAppBar(onUpPress = onUpPress) },
        content = { QuestionsPager(state = state) }
    )
}

@Composable
fun QuestionsPager(state: QuestionsState) {
    when (state) {
        is QuestionsState.Success -> Unit
        is QuestionsState.Error -> Unit
        is QuestionsState.Loading -> Unit
    }
}
