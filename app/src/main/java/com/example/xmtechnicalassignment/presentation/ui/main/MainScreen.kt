package com.example.xmtechnicalassignment.presentation.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.xmtechnicalassignment.R
import com.example.xmtechnicalassignment.presentation.component.FailureContent
import com.example.xmtechnicalassignment.presentation.component.SuccessContent
import com.example.xmtechnicalassignment.presentation.ui.theme.XMTechnicalAssignmentTheme
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Preview
@Composable
fun PreviewMainScreenNone() {
    XMTechnicalAssignmentTheme {
        MainContent(MainState())
    }
}

@Preview
@Composable
fun PreviewMainScreenError() {
    XMTechnicalAssignmentTheme {
        MainContent(MainState(UiStatus.Error))
    }
}

@Preview
@Composable
fun PreviewMainScreenLoading() {
    XMTechnicalAssignmentTheme {
        MainContent(MainState(UiStatus.Loading))
    }
}

@Preview
@Composable
fun PreviewMainScreenEmpty() {
    XMTechnicalAssignmentTheme {
        MainContent(MainState(UiStatus.Empty))
    }
}

@Composable
fun MainScreen(onOpenQuestions: () -> Unit) {
    val viewModel: MainViewModel = viewModel()
    val state by viewModel.collectAsState()

    viewModel.collectSideEffect {
        when (it) {
            is MainSideEffect.OpenQuestions -> onOpenQuestions.invoke()
        }
    }

    MainContent(
        state = state,
        onStartSurveyClick = viewModel::onStartSurveyClick,
        onRefreshClick = viewModel::onStartSurveyClick
    )
}

@Composable
fun MainContent(
    state: MainState,
    onStartSurveyClick: () -> Unit = {},
    onRefreshClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    when (state.status) {
        UiStatus.Empty -> DefaultContent(
            showSuccessText = stringResource(R.string.main_empty_message),
            onStartSurveyClick = onStartSurveyClick
        )

        UiStatus.Error -> DefaultContent(
            showError = true,
            onStartSurveyClick = onStartSurveyClick,
            onRefreshClick = onRefreshClick
        )

        UiStatus.Loading -> LoadingContent()
        null -> DefaultContent(onStartSurveyClick = onStartSurveyClick)
    }
}

@Composable
fun DefaultContent(
    showError: Boolean = false,
    showSuccessText: String = "",
    onStartSurveyClick: () -> Unit = {},
    onRefreshClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (showError) {
            FailureContent(onRefreshClick)
        }
        if (showSuccessText.isNotEmpty()) {
            SuccessContent(text = showSuccessText)
        }
        Spacer(Modifier.size(height = 16.dp, width = 0.dp))
        Text(text = stringResource(R.string.main_welcome))
        Spacer(Modifier.size(height = 90.dp, width = 0.dp))
        Button(onClick = { onStartSurveyClick() }) {
            Text(stringResource(R.string.main_start_survey))
        }
    }
}

@Composable
fun LoadingContent(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp)
        )
    }
}
