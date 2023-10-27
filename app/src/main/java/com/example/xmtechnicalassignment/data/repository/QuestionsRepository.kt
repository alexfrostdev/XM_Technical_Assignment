package com.example.xmtechnicalassignment.data.repository

import android.util.Log
import com.example.xmtechnicalassignment.data.remote.QuestionsService
import com.example.xmtechnicalassignment.data.remote.entity.Question


interface QuestionsRepository {
    suspend fun getQuestions(): Result<List<Question>>
}

class QuestionsRepositoryImp(private val service: QuestionsService) : QuestionsRepository {

    override suspend fun getQuestions(): Result<List<Question>> {
        return kotlin.runCatching { service.getQuestions() }.onFailure {
            Log.e("QuestionsRepository", "error", it)
        }
    }
}