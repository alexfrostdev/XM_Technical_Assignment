package com.example.xmtechnicalassignment.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.xmtechnicalassignment.R
import com.example.xmtechnicalassignment.presentation.ui.theme.QuestionBackgroundColor
import com.example.xmtechnicalassignment.presentation.ui.theme.XMTechnicalAssignmentTheme


@Preview
@Composable
fun PreviewQuestionsAppBar() {

    XMTechnicalAssignmentTheme {
        QuestionsAppBar(
            info = QuestionsPagerInfo(currentPage = 2, pageCount = 13),
            onUpPress = {}
        )
    }
}

/**
 * TopAppBar for screens that need a back button.
 *
 * @param onUpPress function to be called when the back/up button is clicked
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionsAppBar(
    info: QuestionsPagerInfo, onUpPress: () -> Unit,
    onPageChanged: (newPage: Int) -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(
                    R.string.questions_question_template,
                    "${info.currentPage + 1}/${info.pageCount}"
                )
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = QuestionBackgroundColor),
        navigationIcon = {
            IconButton(onClick = onUpPress) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.questions_back)
                )
            }
        },
        actions = {
            IconButton(
                onClick = { onPageChanged.invoke(info.currentPage - 1) },
                Modifier.padding(horizontal = 0.dp),
                enabled = info.currentPage != 0
            ) {
                Text(text = stringResource(R.string.questions_previous), fontSize = 12.sp, maxLines = 1)
            }
            IconButton(
                onClick = { onPageChanged.invoke(info.currentPage + 1) },
                Modifier.padding(horizontal = 0.dp),
                enabled = info.currentPage + 1 != info.pageCount
            ) {
                Text(text = stringResource(R.string.questions_next), fontSize = 12.sp, maxLines = 1)
            }
        })
}

data class QuestionsPagerInfo(
    val currentPage: Int,
    val pageCount: Int,
)
