package com.example.xmtechnicalassignment.presentation.ui.questions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.xmtechnicalassignment.R
import com.example.xmtechnicalassignment.data.remote.entity.Question
import com.example.xmtechnicalassignment.data.remote.entity.SubmitQuestion
import com.example.xmtechnicalassignment.presentation.component.FailureContent
import com.example.xmtechnicalassignment.presentation.component.LoadingContent
import com.example.xmtechnicalassignment.presentation.component.QuestionsAppBar
import com.example.xmtechnicalassignment.presentation.component.SuccessContent
import com.example.xmtechnicalassignment.presentation.navigation.Actions
import com.example.xmtechnicalassignment.presentation.navigation.Screen
import com.example.xmtechnicalassignment.presentation.ui.theme.QuestionBackgroundColor
import com.example.xmtechnicalassignment.presentation.ui.theme.XMTechnicalAssignmentTheme
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

//@Preview
//@Composable
//fun PreviewEmptyQuestionsScreen() {
//    val state = QuestionsState.Loading
//
//    XMTechnicalAssignmentTheme {
//        QuestionsContent(state, {})
//    }
//}
//
//@Preview
//@Composable
//fun PreviewQuestionsScreen() {
//    val state = QuestionsState.Loading
//
//    XMTechnicalAssignmentTheme {
//        QuestionsContent(state, {})
//    }
//}

fun NavGraphBuilder.addQuestions(actions: Actions) {
    composable(Screen.Questions.route) {
        val viewModel = viewModel { QuestionsViewModel() }
        val state by viewModel.collectAsState()

        viewModel.collectSideEffect {
            when (it) {
                QuestionsSideEffect.CloseQuestions -> actions.popBackStack
            }
        }

        QuestionsContent(
            state,
            onUpPress = actions.popBackStack,
            onSubmitQuestion = viewModel::onSubmitQuestion,
            onRetryClick = viewModel::onSubmitQuestion,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionsContent(
    state: QuestionsScreenState,
    onUpPress: () -> Unit,
    onSubmitQuestion: (SubmitQuestion) -> Unit,
    onRetryClick: (SubmitQuestion) -> Unit = {},
) {
    Scaffold(
        topBar = { QuestionsAppBar(onUpPress = onUpPress) },
        content = { padding ->
            QuestionsPager(
                state = state,
                modifier = Modifier.padding(padding),
                onSubmitQuestion,
                onRetryClick,
            )
        }
    )
}

@Composable
fun QuestionsPager(
    state: QuestionsScreenState,
    modifier: Modifier = Modifier,
    onSubmitQuestion: (SubmitQuestion) -> Unit,
    onRetryClick: (SubmitQuestion) -> Unit = {},
) {
    HorizontalPager(state.questions, modifier = modifier, onSubmitQuestion, onRetryClick)
}

@Preview
@Composable
fun PreviewHorizontalPager() {
    val list = listOf(
        QuestionState(question = Question(1, "test1")),
        QuestionState(question = Question(2, "test2")),
    )

    XMTechnicalAssignmentTheme {
        HorizontalPager(list)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPager(
    question: List<QuestionState>,
    modifier: Modifier = Modifier,
    onSubmitQuestion: (SubmitQuestion) -> Unit = {},
    onRetryClick: (SubmitQuestion) -> Unit = {},
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier) {
        HorizontalPager(
            pageCount = question.size,
            state = pagerState,
        ) { page ->
            DisplayQuestion(question[page], onSubmitQuestion, onRetryClick)
        }

//        LaunchedEffect(pagerState) {
//            snapshotFlow { pagerState.currentPage }
//                .collect { currentPage ->
//                    pagerState.animateScrollToPage(currentPage)
//                }
//        }
    }
}

@Preview
@Composable
fun PreviewDisplayQuestion() {
    val questionState = QuestionState(question = Question(1, "test1"))

    XMTechnicalAssignmentTheme {
        DisplayQuestion(questionState)
    }
}

@Preview
@Composable
fun PreviewDisplayQuestionSubmitted() {
    val questionState = QuestionState(
        status = UiStatus.Success,
        question = Question(1, "test1"),
        answer = "answer1"
    )

    XMTechnicalAssignmentTheme {
        DisplayQuestion(questionState)
    }
}

@Preview
@Composable
fun PreviewDisplayQuestionLoading() {
    val questionState = QuestionState(
        status = UiStatus.Loading,
        question = Question(1, "test1"),
    )

    XMTechnicalAssignmentTheme {
        DisplayQuestion(questionState)
    }
}

@Preview
@Composable
fun PreviewDisplayQuestionError() {
    val questionState = QuestionState(
        status = UiStatus.Error,
        question = Question(1, "test1"),
    )

    XMTechnicalAssignmentTheme {
        DisplayQuestion(questionState)
    }
}

@Preview
@Composable
fun PreviewDisplayQuestionSuccess() {
    val questionState = QuestionState(
        status = UiStatus.Success,
        question = Question(1, "test1"),
    )

    XMTechnicalAssignmentTheme {
        DisplayQuestion(questionState)
    }
}

@Composable
fun DisplayQuestion(
    questionState: QuestionState,
    onSubmitQuestion: (SubmitQuestion) -> Unit = {},
    onRetryClick: (SubmitQuestion) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(QuestionBackgroundColor)
    ) {
        val question = questionState.question
        val status = questionState.status
        if (status == UiStatus.Loading) {
            LoadingContent()
        } else {
            QuestionContent(questionState = questionState, onSubmitQuestion = onSubmitQuestion)
            if (status == UiStatus.Error) {
                FailureContent(onRetryClick = {
                    onRetryClick.invoke(
                        SubmitQuestion(
                            id = question.id,
                            answer = questionState.answer
                        )
                    )
                })
            }
            if (status == UiStatus.Success) {
                SuccessContent(text = stringResource(R.string.message_success))
            }
        }
    }
}

