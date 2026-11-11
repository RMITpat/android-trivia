package com.example.trivia.ui.quiz

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.trivia.R
import com.example.trivia.network.Question
import com.example.trivia.ui.theme.CorrectAnswer
import com.example.trivia.ui.theme.TriviaTheme
import com.example.trivia.ui.theme.WrongAnswer

@Composable
fun PlayScreen(
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = hiltViewModel<QuizViewModel>(),
    goBackClick: () -> Unit,
) {
    val questionState by viewModel.questionState.collectAsStateWithLifecycle()
    val selectedAnswer by viewModel.selectedAnswer.collectAsStateWithLifecycle()
    val questionEnabled by viewModel.questionEnabled.collectAsStateWithLifecycle()
    val questionTimer by viewModel.questionTimer.collectAsStateWithLifecycle()

    when (val state = questionState) {
        is QuestionsState.Error -> ErrorDisplay(
            goBackClick = { goBackClick() },
            retryClick = { viewModel.fetchQuestions() },
            modifier = modifier.fillMaxSize()
        )
        is QuestionsState.Loading -> LoadingDisplay(
            modifier = modifier.fillMaxSize()
        )
        is QuestionsState.Success -> QuestionDisplay(
            modifier = modifier,
            question = state.questions[state.currentIndex],
            questionIndex = state.currentIndex,
            questionCount = state.questions.size,
            selectAnswer = viewModel::selectAnswer,
            selectedAnswer = selectedAnswer,
            submitAnswer = viewModel::submitAnswer,
            questionEnabled = questionEnabled,
            questionTimer = questionTimer,
            nextQuestion = viewModel::nextQuestion,
            goBackClick = goBackClick

        )
    }


}

@Composable
fun QuestionDisplay(
    question: Question,
    questionIndex: Int,
    questionCount: Int,
    modifier: Modifier = Modifier,
    selectAnswer: (String) -> Unit,
    selectedAnswer: String,
    submitAnswer: () -> Unit,
    questionEnabled: Boolean,
    questionTimer: Float,
    nextQuestion: () -> Unit,
    goBackClick: () -> Unit
) {

    Scaffold (
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier,
        topBar = {
            QuestionTopBar(
                modifier = Modifier.fillMaxWidth(),
                questionIndex = questionIndex,
                questionCount = questionCount,
                goBackClick = goBackClick
            )
        }
    ) { paddingValues ->
        
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxHeight()

        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {
                QuestionTimer(
                    currentProgress = questionTimer
                )
                // Category
                Surface(
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp )
                ) {
                    Text(
                        text = question.category,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(10.dp),
                        maxLines = 2,
                        autoSize = TextAutoSize.StepBased(minFontSize = 5.sp, maxFontSize = 26.sp)

                    )
                }

                // Question
                Text(
                    text = question.question,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(15.dp),
                    maxLines = 4,
                    autoSize = TextAutoSize.StepBased(minFontSize = 5.sp, maxFontSize = 40.sp)

                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 10.dp)
            ) {
                // Answers
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier,
                ) {
                    items(
                        items = question.allAnswers,
                    ) { answer ->
                        AnswerBox(
                            answer, questionEnabled, question, selectedAnswer, selectAnswer
                        )
                    }

                }
                if (questionEnabled) {
                    // Submit
                    ElevatedButton(
                        onClick = submitAnswer,
                        enabled = selectedAnswer.isNotEmpty()
                    ) {
                        Text(
                            stringResource(R.string.lock_it_in_eddy),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                } else {
                    Button(
                        onClick = nextQuestion
                    ) {
                        Text(
                            stringResource(R.string.next_question),
                            style = MaterialTheme.typography.titleLarge
                        )

                    }
                }
            }



        }
    }
}

@Composable
fun AnswerBox(
    answer: String,
    questionEnabled: Boolean,
    question: Question,
    selectedAnswer: String,
    selectAnswer: (String) -> Unit
) {
    val answerColor by animateColorAsState(
        answerColor(
            questionEnabled = questionEnabled,
            answerCorrect = answer == question.correctAnswer
             ),
        animationSpec = tween(
            durationMillis = 1000, // 1 second duration
            delayMillis = 100,     // Wait 100ms before starting
            easing = LinearOutSlowInEasing // Smooth slowdown at the end
        ),
    )
    Surface(
        color = answerColor,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            //.heightIn(min = 150.dp)
            .size(200.dp)
            .padding(10.dp),
        border = if (selectedAnswer == answer)
            BorderStroke(6.dp,
                Color.Black
            )
        else null,
        onClick = {
            selectAnswer(answer)
        },
        enabled = questionEnabled

    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = answer,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                autoSize = TextAutoSize.StepBased(minFontSize = 5.sp, maxFontSize = 28.sp)
            )
        }
    }
}

@Composable
private fun answerColor(
    questionEnabled: Boolean,
    answerCorrect: Boolean,
): Color {

    return if (questionEnabled) {
        MaterialTheme.colorScheme.secondary
    } else if (answerCorrect) {
        CorrectAnswer
    } else {
        WrongAnswer
    }
}
@Composable
fun QuestionTimer(
    currentProgress: Float
) {

    // Animate between progress updates so the indicator fills smoothly.
    val animatedProgress by animateFloatAsState(
        targetValue = currentProgress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
    )

    LinearProgressIndicator(
        progress = { animatedProgress },
        modifier = Modifier.fillMaxWidth(),
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionTopBar(
    modifier: Modifier = Modifier,
    questionIndex: Int,
    questionCount: Int,
    goBackClick: () -> Unit
) {

    TopAppBar(
        modifier = modifier,
        title = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(end = 10.dp)
            ) {
                Text(
                    text = "Question ${questionIndex + 1} of $questionCount",
                    style = MaterialTheme.typography.headlineMedium
                )
                IconButton(
                    onClick = goBackClick
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Exit quiz",
                    )
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun QuestionPreview() {
    TriviaTheme {
        QuestionDisplay(
            question = Question(
                category = "The best ijjiij",
                question = "Who is going to Google",
                difficulty = "Hard",
                correctAnswer = "Me",
                incorrectAnswers = listOf("Tommy", "Solash", "Noah Bloomfielddwdjwaojdwaioajwiowjaioddijowajiowadijodwaijowadiowajiodwa")
            ),
            questionIndex = 0,
            questionCount = 10,
            modifier = Modifier,
            selectAnswer = {},
            selectedAnswer = "Tommy",
            submitAnswer = {},
            questionEnabled = true,
            questionTimer = 0.7f,
            nextQuestion = {},
            goBackClick = {}
        )
    }
}