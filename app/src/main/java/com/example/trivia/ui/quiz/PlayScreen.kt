package com.example.trivia.ui.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.trivia.network.Question
import com.example.trivia.ui.home.HomeScreen
import com.example.trivia.ui.theme.TriviaTheme

@Composable
fun PlayScreen(
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = hiltViewModel<QuizViewModel>(),
) {
    val questionState by viewModel.questionState.collectAsStateWithLifecycle()

    when (val state = questionState) {
        is QuestionsState.Error -> Text("Error")
        is QuestionsState.Loading -> CircularProgressIndicator()
        is QuestionsState.Success -> QuestionDisplay(
            modifier = modifier,
            question = state.questions[state.currentIndex],
            questionIndex = state.currentIndex,
            questionCount = state.questions.size

        )
    }


}

@Composable
fun QuestionDisplay(
    question: Question,
    questionIndex: Int,
    questionCount: Int,
    modifier: Modifier = Modifier,
) {

    Scaffold (
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier,
        topBar = {
            QuestionNumber(
                questionIndex = questionIndex,
                questionCount = questionCount
            )
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)

        ) {
            Text(
                text = question.category,
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = question.question,
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp)

            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionNumber(
    modifier: Modifier = Modifier,
    questionIndex: Int,
    questionCount: Int,
) {

    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = "Question ${questionIndex + 1} of $questionCount",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    )
}


@Preview(showBackground = true)
@Composable
fun QuestionPreview() {
    TriviaTheme {
        QuestionDisplay(
            question = Question(
                category = "The best category",
                question = "Who is going to Google",
                difficulty = "Hard",
                correctAnswer = "Me",
                incorrectAnswers = listOf("Tommy", "Solash", "My mum", "Noah Bloomfield")
            ),
            questionIndex = 0,
            questionCount = 10,
            modifier = Modifier
        )
    }
}