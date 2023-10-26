package com.example.xmtechnicalassignment.data.di

import com.example.xmtechnicalassignment.data.remote.QuestionsService
import com.example.xmtechnicalassignment.data.repository.QuestionsRepository
import retrofit2.Retrofit

fun dataComponent(): DataComponent = DataComponent.instance

interface DataComponent {
    val retrofit: Retrofit
    val questionsServiceImp: QuestionsService
    val questionsRepository: QuestionsRepository

    companion object {
        lateinit var instance: DataComponent
    }
}