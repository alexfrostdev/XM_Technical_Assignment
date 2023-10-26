package com.example.xmtechnicalassignment.presentation.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.xmtechnicalassignment.R
import com.example.xmtechnicalassignment.presentation.ui.theme.XMTechnicalAssignmentTheme
import java.util.*

@Preview("Main")
@Composable
fun PreviewMainScreen() {

    XMTechnicalAssignmentTheme {
        MainContent({})
    }
}

@Composable
fun MainScreen(onStartSurveyClick: () -> Unit) {
    val viewModel: MainViewModel = viewModel()

    MainContent(onStartSurveyClick)
}

@Composable
fun MainContent(onStartSurveyClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = stringResource(R.string.main_welcome))
        Spacer(Modifier.size(height = 90.dp, width = 0.dp))
        Button(onClick = { onStartSurveyClick() }) {
            Text(stringResource(R.string.main_start_survey))
        }
    }
}