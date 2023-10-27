package com.example.xmtechnicalassignment.presentation.ui.questions

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.xmtechnicalassignment.R
import com.example.xmtechnicalassignment.data.remote.entity.Question
import com.example.xmtechnicalassignment.data.remote.entity.SubmitQuestion
import com.example.xmtechnicalassignment.presentation.ui.theme.XMTechnicalAssignmentTheme

@Preview
@Composable
fun PreviewQuestionContent() {
    val questionState = QuestionState(question = Question(1, "test1"))

    XMTechnicalAssignmentTheme {
        QuestionContent(questionState, {})
    }
}

@Preview
@Composable
fun PreviewQuestionContentSubmitted() {
    val questionState = QuestionState(
        status = UiStatus.Success,
        question = Question(1, "test1"),
        answer = "answer1"
    )

    XMTechnicalAssignmentTheme {
        QuestionContent(questionState, {})
    }
}

@Preview
@Composable
fun PreviewQuestionContentSuccess() {
    val questionState = QuestionState(
        status = UiStatus.Success,
        question = Question(1, "test1"),
        answer = ""
    )

    XMTechnicalAssignmentTheme {
        QuestionContent(questionState, {})
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionContent(
    questionState: QuestionState,
    onSubmitQuestion: (SubmitQuestion) -> Unit
) {
    var text by rememberSaveable { mutableStateOf(questionState.answer) }

    Column(modifier = Modifier.fillMaxSize()) {
        val question = questionState.question
        Text(
            text = question.question,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(24.dp))

        if (questionState.submittedAnswer.isNotEmpty()) {
            Text(
                text = questionState.submittedAnswer,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Type here for an answer...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
            )
        }
        Spacer(Modifier.height(40.dp))

        val notSubmitted = questionState.submittedAnswer.isEmpty()
        val buttonEnabled = text.isNotEmpty() && notSubmitted

        Button(
            onClick = {
                onSubmitQuestion.invoke(SubmitQuestion(question.id, text))
            },
            enabled = buttonEnabled,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            val labelId = if (notSubmitted) {
                R.string.questions_submit
            } else {
                R.string.questions_already_submitted
            }
            Text(stringResource(labelId))
        }

        Spacer(Modifier.height(24.dp))
    }
}