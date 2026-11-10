package com.example.trivia.data

import android.util.Log
import com.example.trivia.network.OpenTDBService
import com.example.trivia.network.Question
import androidx.core.text.HtmlCompat

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

        return response.results.map { q ->
            q.copy(
                question = decodeHtml(q.question),
                category = decodeHtml(q.category),
                correctAnswer = decodeHtml(q.correctAnswer),
                incorrectAnswers = q.incorrectAnswers.map {
                    decodeHtml(it)
                }

            )
        }
    }


    private fun decodeHtml(text: String): String =
        HtmlCompat.fromHtml(
            text,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        ).toString()
}
