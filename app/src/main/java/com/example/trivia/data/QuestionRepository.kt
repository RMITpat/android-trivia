package com.example.trivia.data

import android.util.Log
import com.example.trivia.network.OpenTDBService
import com.example.trivia.network.Question

interface QuestionRepository {
    suspend fun getQuestions(): List<Question>
}

class QuestionRepositoryImpl(
    private val openTDBService: OpenTDBService
) : QuestionRepository {
    override suspend fun getQuestions(): List<Question> {
        val response = openTDBService.getQuestions()
        Log.d("opentdb" ,response.responseCode.toString())
        for (q: Question in response.results) {
            Log.d("opentdb", q.toString())
        }
        return response.results
    }
}
