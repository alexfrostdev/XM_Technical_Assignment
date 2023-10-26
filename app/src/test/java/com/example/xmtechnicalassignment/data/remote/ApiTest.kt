package com.example.xmtechnicalassignment.data.remote

import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun getTestQuestionsService(mockWebServer: MockWebServer): QuestionsService {
    return Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(QuestionsService::class.java)
}

open class ApiTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var service: QuestionsService

    @Before
    open fun setUp() {
        mockWebServer = MockWebServer()
        service = getTestQuestionsService(mockWebServer)
    }

    @After
    open fun tearDown() {
        mockWebServer.shutdown()
    }

    protected fun enqueueResponse(
        fileName: String,
        headers: Map<String, String> = emptyMap(),
        responseCode: Int = -1
    ) {
        mockWebServer.enqueue(
            createMockResponse(javaClass.classLoader!!, fileName, headers, responseCode)
        )
    }

    protected fun enqueueStringResponse(
        response: String,
        headers: Map<String, String> = emptyMap(),
        responseCode: Int = -1
    ) {
        mockWebServer.enqueue(createMockResponse(response, headers, responseCode))
    }

    protected fun getRequestString(fileName: String): String {
        return requestAsStringWithoutFormatting(javaClass.classLoader!!, fileName)
    }
}