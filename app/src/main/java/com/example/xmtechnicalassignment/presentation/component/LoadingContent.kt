package com.example.xmtechnicalassignment.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.xmtechnicalassignment.presentation.ui.theme.XMTechnicalAssignmentTheme

@Preview
@Composable
fun PreviewLoadingContent() {

    XMTechnicalAssignmentTheme {
        LoadingContent()
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