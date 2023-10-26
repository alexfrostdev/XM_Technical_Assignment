package com.example.xmtechnicalassignment.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.xmtechnicalassignment.R
import com.example.xmtechnicalassignment.presentation.ui.theme.ErrorColor
import com.example.xmtechnicalassignment.presentation.ui.theme.SuccessColor
import com.example.xmtechnicalassignment.presentation.ui.theme.XMTechnicalAssignmentTheme

@Preview
@Composable
fun PreviewSuccessContent() {
    XMTechnicalAssignmentTheme {
        SuccessContent()
    }
}

@Composable
fun SuccessContent(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = SuccessColor
    ) {
        Text(
            text = stringResource(R.string.message_success),
            Modifier.padding(horizontal = 16.dp, vertical = 32.dp)
        )
    }
}

@Preview
@Composable
fun PreviewFailureContent() {
    XMTechnicalAssignmentTheme {
        FailureContent({})
    }
}

@Composable
fun FailureContent(onRefreshClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = ErrorColor
    ) {
        Row(
            Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.message_failure))
            Spacer(Modifier.size(height = 0.dp, width = 0.dp))
            Button(onClick = { onRefreshClick() }, Modifier.weight(1f, false)) {
                Text(stringResource(R.string.message_retry))
            }
        }
    }

}