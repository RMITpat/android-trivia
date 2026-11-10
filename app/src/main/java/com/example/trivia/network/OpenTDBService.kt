package com.example.trivia.network

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenTDBService {
    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: Int = 5,
        @Query("type") type: String = "multiple"
    ): List<Question>
}