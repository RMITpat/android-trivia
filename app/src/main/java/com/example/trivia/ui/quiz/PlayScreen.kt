package com.example.trivia.ui.quiz

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun PlayScreen(
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = hiltViewModel<QuizViewModel>(),
) {
    val questionState by viewModel.questionState.collectAsStateWithLifecycle()

    when (val state = questionState) {
        is QuestionsState.Error -> Text("Error")
        is QuestionsState.Loading -> CircularProgressIndicator()
        is QuestionsState.Success -> QuizStart(
            category = state.questions[0].category
        )
    }

}

@Composable
fun QuizStart(
    category: String,
    modifier: Modifier = Modifier,
) {
    Column() {
        Text(
            text = "10 Questions!"
        )
        Text(
            text = "Your category is: $category"
        )
    }
}