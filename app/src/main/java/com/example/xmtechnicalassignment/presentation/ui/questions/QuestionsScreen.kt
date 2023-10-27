package com.example.xmtechnicalassignment.presentation.ui.questions

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.xmtechnicalassignment.R
import com.example.xmtechnicalassignment.data.remote.entity.Question
import com.example.xmtechnicalassignment.data.remote.entity.SubmitQuestion
import com.example.xmtechnicalassignment.presentation.component.*
import com.example.xmtechnicalassignment.presentation.navigation.Actions
import com.example.xmtechnicalassignment.presentation.navigation.Screen
import com.example.xmtechnicalassignment.presentation.ui.theme.QuestionBackgroundColor
import com.example.xmtechnicalassignment.presentation.ui.theme.XMTechnicalAssignmentTheme
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewQuestionsScreen() {
    val list = mutableStateListOf(
        QuestionState(question = Question(1, "test1")),
        QuestionState(question = Question(2, "test2")),
    )
    val state = QuestionsScreenState(questions = list, submittedQuestions = 2)

    XMTechnicalAssignmentTheme {
        QuestionsContent(state, {}, {})
    }
}

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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun QuestionsContent(
    state: QuestionsScreenState,
    onUpPress: () -> Unit,
    onSubmitQuestion: (SubmitQuestion) -> Unit,
    onRetryClick: (SubmitQuestion) -> Unit = {},
) {
    val pagerState = rememberPagerState()
    val info by remember {
        derivedStateOf {
            QuestionsPagerInfo(
                currentPage = pagerState.currentPage,
                pageCount = state.questions.size,
            )
        }
    }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            QuestionsAppBar(
                info = info,
                onUpPress = onUpPress,
                onPageChanged = { newPage ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(newPage)
                    }
                })
        },
        content = { padding ->
            QuestionsPager(
                state = state,
                pagerState = pagerState,
                modifier = Modifier.padding(padding),
                onSubmitQuestion,
                onRetryClick,
            )
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuestionsPager(
    state: QuestionsScreenState,
    pagerState: PagerState = rememberPagerState(),
    modifier: Modifier = Modifier,
    onSubmitQuestion: (SubmitQuestion) -> Unit,
    onRetryClick: (SubmitQuestion) -> Unit = {},
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.questions_submitted_template, state.submittedQuestions),
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )
        QuestionsHorizontalPager(
            question = state.questions,
            pagerState = pagerState,
            onSubmitQuestion = onSubmitQuestion,
            onRetryClick = onRetryClick
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuestionsHorizontalPager(
    question: SnapshotStateList<QuestionState>,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    onSubmitQuestion: (SubmitQuestion) -> Unit = {},
    onRetryClick: (SubmitQuestion) -> Unit = {},
) {
    Column(modifier = modifier) {
        HorizontalPager(
            pageCount = question.size,
            state = pagerState,
        ) { page ->
            DisplayQuestion(question[page], onSubmitQuestion, onRetryClick)
        }
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

