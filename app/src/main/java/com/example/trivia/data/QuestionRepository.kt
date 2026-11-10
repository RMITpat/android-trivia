package com.example.trivia.data

import com.example.trivia.network.OpenTDBService
import com.example.trivia.network.Question

interface QuestionRepository {
    suspend fun getQuestions(): List<Question>
}

class QuestionRepositoryImpl(
    private val openTDBService: OpenTDBService
) : QuestionRepository {
    override suspend fun getQuestions(): List<Question> {
        return openTDBService.getQuestions()
    }
}
