package com.example.xmtechnicalassignment.presentation.ui.main

import com.example.xmtechnicalassignment.data.remote.entity.Question
import com.example.xmtechnicalassignment.data.repository.QuestionsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.orbitmvi.orbit.test.test
import java.io.IOException

class MainViewModelTest {
//    @get:Rule
//    val mainDispatcherRule = MainDispatcherRule()

    private val questionsRepository: QuestionsRepository = mockk()

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(questionsRepository)
    }

    @Test
    fun `WHEN repo returns empty THEN get Empty result`() = runTest {
        //Setup
        coEvery { questionsRepository.loadQuestions() } returns Result.success(emptyList())

        mainViewModel.test(this, MainState()) {
            expectInitialState()
            containerHost.onStartSurveyClick()

            expectState(MainState(UiStatus.Loading))
            expectState(MainState(UiStatus.Empty))
            expectState(MainState())
        }
    }

    @Test
    fun `WHEN repo returns error THEN get Error result`() = runTest {

        //Setup
        coEvery { questionsRepository.loadQuestions() } returns Result.failure(IOException())


        mainViewModel.test(this, MainState()) {
            expectInitialState()
            containerHost.onStartSurveyClick()

            expectState(MainState(UiStatus.Loading))
            expectState(MainState(UiStatus.Error))
            expectState(MainState())
        }
    }

    @Test
    fun `WHEN repo returns questions THEN get Error result`() = runTest {

        //Setup
        val list = listOf(Question(1, "test"))
        coEvery { questionsRepository.loadQuestions() } returns Result.success(list)


        mainViewModel.test(this, MainState()) {
            expectInitialState()
            containerHost.onStartSurveyClick()

            expectState(MainState(UiStatus.Loading))
            expectSideEffect(MainSideEffect.OpenQuestions)
            expectState(MainState())
        }
    }
}