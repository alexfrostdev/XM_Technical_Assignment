package com.example.xmtechnicalassignment.data.repository

import com.example.xmtechnicalassignment.data.remote.QuestionsService
import com.example.xmtechnicalassignment.data.remote.entity.Question
import com.example.xmtechnicalassignment.data.remote.entity.SubmitQuestion
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class QuestionsRepositoryImpTest {

    private val service: QuestionsService = mockk()

    private lateinit var questionsRepository: QuestionsRepository

    @Before
    fun setUp() {
        questionsRepository = QuestionsRepositoryImp(service)
    }

    @Test
    fun `WHEN getQuestions() without errors THEN get success result`() = runTest {

        //Setup
        val list = listOf(Question(1, "test"))
        coEvery { service.getQuestions() } returns list

        //Run test
        val result: Result<List<Question>> = questionsRepository.loadQuestions()

        //Validate
        assertTrue(result.isSuccess)
        assertEquals(list, result.getOrThrow())
    }

    @Test
    fun `WHEN getQuestions() with errors THEN get failure result`() = runTest {

        //Setup
        coEvery { service.getQuestions() } throws IOException()

        //Run test
        val result: Result<List<Question>> = questionsRepository.loadQuestions()

        //Validate
        assertTrue(result.isFailure)
    }

    @Test
    fun `WHEN getQuestions() without errors THEN get success result1`() = runTest {

        //Setup
        val submitQuestion = SubmitQuestion(id = 1, answer = "answer1")
        coEvery { service.submitQuestion(submitQuestion) } returns Response.success(null)

        //Run test
        val result: Result<Boolean> = questionsRepository.submitQuestion(submitQuestion)

        //Validate
        assertTrue(result.isSuccess)
    }

    @Test
    fun `WHEN submitQuestion() with errors THEN get failure result`() = runTest {

        //Setup
        val submitQuestion = SubmitQuestion(id = 1, answer = "answer1")
        coEvery { service.submitQuestion(submitQuestion) } throws IOException()

        //Run test
        val result: Result<Boolean> = questionsRepository.submitQuestion(submitQuestion)

        //Validate
        assertTrue(result.isFailure)
    }
}