package com.example.xmtechnicalassignment.presentation.ui.main

import app.cash.turbine.test
import com.example.xmtechnicalassignment.MainDispatcherRule
import com.example.xmtechnicalassignment.data.remote.entity.Question
import com.example.xmtechnicalassignment.data.repository.QuestionsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import kotlin.time.Duration.Companion.seconds

class MainViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val questionsRepository: QuestionsRepository = mockk()

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(questionsRepository)
    }

    @Test
    fun `WHEN setNoneState() THEN get None`() = runTest {

        //Run test
        mainViewModel.setNoneState()
        advanceUntilIdle()

        //Validate
        mainViewModel.state.test {
            assertEquals(QuestionsState.None, awaitItem())
        }
    }

    @Test
    fun `WHEN repo returns empty THEN get Empty result`() = runTest {

        //Setup
        coEvery { questionsRepository.getQuestions() } returns Result.success(emptyList())

        //Validate
        mainViewModel.state.test(timeout = 50.seconds) {
            assertEquals(QuestionsState.None, awaitItem())
            //Run test
            mainViewModel.onStartSurveyClick()
            advanceUntilIdle()

            assertEquals(QuestionsState.Loading, awaitItem())
            assertEquals(QuestionsState.Empty, awaitItem())
            assertEquals(QuestionsState.None, awaitItem())
        }
    }

    @Test
    fun `WHEN repo returns error THEN get Error result`() = runTest {

        //Setup
        coEvery { questionsRepository.getQuestions() } returns Result.failure(IOException())

        //Validate
        mainViewModel.state.test(timeout = 50.seconds) {
            assertEquals(QuestionsState.None, awaitItem())
            //Run test
            mainViewModel.onStartSurveyClick()
            advanceUntilIdle()

            assertEquals(QuestionsState.Loading, awaitItem())
            assertEquals(QuestionsState.Error, awaitItem())
            assertEquals(QuestionsState.None, awaitItem())
        }
    }

    @Test
    fun `WHEN repo returns Success THEN get Error result`() = runTest {

        //Setup
        val list = listOf(Question(1, "test"))
        coEvery { questionsRepository.getQuestions() } returns Result.success(list)

        //Validate
        mainViewModel.state.test(timeout = 50.seconds) {
            assertEquals(QuestionsState.None, awaitItem())
            //Run test
            mainViewModel.onStartSurveyClick()
            advanceUntilIdle()

            assertEquals(QuestionsState.Loading, awaitItem())
            assertEquals(QuestionsState.Success, awaitItem())
        }
    }
}