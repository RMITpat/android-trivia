package com.example.trivia.network

data class Question (
    val question: String,
    val category: String,
    val difficulty: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>
)

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

