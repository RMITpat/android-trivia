package com.example.trivia.network

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys


@Serializable
data class TriviaResponse(
    @SerialName("response_code") val responseCode: Int,
    val results: List<Question>
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class Question(
    val category: String,
    val question: String,
    val difficulty: String,
    @SerialName("correct_answer") val correctAnswer: String,
    @SerialName("incorrect_answers") val incorrectAnswers: List<String>

) {
    val allAnswers: List<String> = (incorrectAnswers + correctAnswer).shuffled()
}
//{
//    "type": "multiple",
//    "difficulty": "medium",
//    "category": "Science: Computers",
//    "question": "What does AD stand for in relation to Windows Operating Systems? ",
//    "correct_answer": "Active Directory",
//    "incorrect_answers": [
//    "Alternative Drive",
//    "Automated Database",
//    "Active Department"
//    ]
//},

//{
//    "type": "boolean",
//    "difficulty": "easy",
//    "category": "Entertainment: Music",
//    "question": "The K-Pop Boy Band BTS debuted in the year 2013.",
//    "correct_answer": "True",
//    "incorrect_answers": [
//    "False"
//    ]
//},

