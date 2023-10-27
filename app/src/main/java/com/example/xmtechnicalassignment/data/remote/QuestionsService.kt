package com.example.xmtechnicalassignment.data.remote

import com.example.xmtechnicalassignment.data.remote.entity.Question
import com.example.xmtechnicalassignment.data.remote.entity.SubmitQuestion
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface QuestionsService {

    @GET("/questions")
    suspend fun getQuestions(): List<Question>

    @POST("/question/submit")
    suspend fun submitQuestion(@Body item: SubmitQuestion): Response<Unit>
}
