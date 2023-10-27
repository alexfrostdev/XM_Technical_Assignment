package com.example.xmtechnicalassignment.data.repository

import android.util.Log
import com.example.xmtechnicalassignment.data.remote.QuestionsService
import com.example.xmtechnicalassignment.data.remote.entity.Question
import com.example.xmtechnicalassignment.data.remote.entity.SubmitQuestion


interface QuestionsRepository {
    val questions: ArrayList<Question>
    suspend fun getQuestions(): Result<List<Question>>
    suspend fun submitQuestion(submitQuestion: SubmitQuestion): Result<Boolean>
}

class QuestionsRepositoryImp(private val service: QuestionsService) : QuestionsRepository {

    override val questions: ArrayList<Question> = ArrayList()

    override suspend fun getQuestions(): Result<List<Question>> {
        questions.clear()
        return kotlin.runCatching {
            service.getQuestions().also {
                questions.addAll(it)
            }
        }.onFailure {
            Log.e("QuestionsRepository", "getQuestions error", it)
        }
    }

    override suspend fun submitQuestion(submitQuestion: SubmitQuestion): Result<Boolean> {
        return try {
            val response = service.submitQuestion(submitQuestion)
            if (response.isSuccessful) {
                Result.success(true)
            } else {
                Result.failure(Exception("Server error code=${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e("QuestionsRepository", "submitQuestion error", e)
            Result.failure(e)
        }
    }
}