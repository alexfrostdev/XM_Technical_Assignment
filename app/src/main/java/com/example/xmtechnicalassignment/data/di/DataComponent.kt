package com.example.xmtechnicalassignment.data.di

import com.example.xmtechnicalassignment.data.repository.QuestionsRepository

fun dataComponent(): DataComponent = DataComponent.instance

interface DataComponent {
    val questionsRepository: QuestionsRepository

    companion object {
        lateinit var instance: DataComponent
    }
}