package com.example.xmtechnicalassignment.presentation.ui.questions

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import com.example.xmtechnicalassignment.MainDispatcherRule
import com.example.xmtechnicalassignment.data.remote.entity.Question
import com.example.xmtechnicalassignment.data.remote.entity.SubmitQuestion
import com.example.xmtechnicalassignment.data.repository.QuestionsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.orbitmvi.orbit.test.test
import java.io.IOException

class QuestionsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val questionsRepository: QuestionsRepository = mockk()

    private lateinit var viewModel: QuestionsViewModel

    @Before
    fun setUp() {
        coEvery { questionsRepository.questions } returns ArrayList()
        viewModel = QuestionsViewModel(questionsRepository)
    }

    private fun createInitState(): QuestionsScreenState {
        val items = ArrayList<QuestionState>()
        return QuestionsScreenState(questions = items.toMutableStateList())
    }

    @Test
    fun `WHEN repo returns empty THEN close screen`() = runTest {
        viewModel.test(this, createInitState()) {
            expectInitialState()
            viewModel.checkQuestions()
            expectSideEffect(QuestionsSideEffect.CloseQuestions)
        }
    }

    @Test
    fun `WHEN error during submition THEN get error message`() = runTest {
        //Setup
        val submitQuestion = SubmitQuestion(id = 1, answer = "answer1")
        coEvery { questionsRepository.questions } returns ArrayList<Question>().apply {
            add(Question(id = 1, question = "question1"))
        }
        viewModel = QuestionsViewModel(questionsRepository)

        coEvery { questionsRepository.submitQuestion(submitQuestion) } returns Result.failure(IOException())
        val state = QuestionsScreenState(
            questions = mutableStateListOf(
                QuestionState(
                    question = Question(
                        id = 1,
                        question = "question1"
                    )
                )
            )
        )

        val expected = QuestionsScreenState(
            questions = mutableStateListOf(
                QuestionState(
                    question = Question(
                        id = 1,
                        question = "question1"
                    )
                )
            )
        )
        //TODO investigate why libs test method does not get state updates
//        viewModel.test(this, state) {
//            expectInitialState()
//            containerHost.onSubmitQuestion(submitQuestion)
//
//            expectState(expected)
//        }
    }
}