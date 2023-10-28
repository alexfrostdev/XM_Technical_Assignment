package com.example.xmtechnicalassignment.data.remote

import com.example.xmtechnicalassignment.data.remote.entity.Question
import com.example.xmtechnicalassignment.data.remote.entity.SubmitQuestion
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okio.buffer
import okio.source
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

class QuestionsServiceTest : ApiTest() {

    @Test
    fun getQuestions() {
        //Setup
        enqueueResponse("questions.json")

        // Run the test
        val body: List<Question> = runBlocking {
            service.getQuestions()
        }

        //Verify the results
        val request = mockWebServer.takeRequest(1, TimeUnit.SECONDS)!!
        assertEquals("GET /questions HTTP/1.1", request.requestLine)

        assertTrue(body.size == 10)

        val question: Question = body[0]
        assertEquals(1, question.id)
        assertEquals("What is your favourite colour?", question.question)
    }

    @Test
    fun postAwardMessage() {
        //Setup
        val expected: String = getRequestString("post-submit-question-body.json")
        val submitQuestion = SubmitQuestion(id = 1,"test")
        //empty response
        mockWebServer.enqueue(MockResponse())

        // Run the test
        val body = runBlocking {
            service.submitQuestion(submitQuestion)
        }

        //Verify the results
        val post = mockWebServer.takeRequest(1, TimeUnit.SECONDS)!!
        assertEquals("POST /question/submit HTTP/1.1", post.requestLine)
        assertEquals("application/json; charset=UTF-8", post.getHeader("Content-Type"))
        assertEquals(expected, post.body.readUtf8())
    }
}