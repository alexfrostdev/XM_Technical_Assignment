package com.example.xmtechnicalassignment.data.di

import android.content.Context
import com.example.xmtechnicalassignment.BuildConfig
import com.example.xmtechnicalassignment.data.remote.QuestionsService
import com.example.xmtechnicalassignment.data.repository.QuestionsRepository
import com.example.xmtechnicalassignment.data.repository.QuestionsRepositoryImp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataModule(val context: Context) : DataComponent {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://xm-assignment.web.app")
        .addConverterFactory(GsonConverterFactory.create())
        .client(getHttpClient())
        .build()

    private val questionsServiceImp: QuestionsService = retrofit.create(QuestionsService::class.java)

    override val questionsRepository: QuestionsRepository = QuestionsRepositoryImp(questionsServiceImp)

    companion object {

        @JvmStatic
        fun getHttpClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()

            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                builder.addInterceptor(logging)
            }
            return builder.build()
        }
    }
}