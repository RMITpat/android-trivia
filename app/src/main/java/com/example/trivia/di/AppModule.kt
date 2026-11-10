package com.example.trivia.di

import com.example.trivia.data.QuestionRepository
import com.example.trivia.data.QuestionRepositoryImpl
import com.example.trivia.network.OpenTDBService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import javax.inject.Singleton


@Module
@InstallIn(
    SingletonComponent::class
)
object AppModule {


    @Provides
    @Singleton
    fun provideOpenTDBService(): OpenTDBService {
        return Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl("https://opentdb.com/")
            .build()
            .create(OpenTDBService::class.java)

    }

    @Provides
    @Singleton
    fun provideQuestionRepository(openTDBService: OpenTDBService): QuestionRepository {
        return QuestionRepositoryImpl(openTDBService)
    }
}