package com.example.trivia.ui.quiz

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trivia.ui.theme.TriviaTheme

@Composable
fun FinishedDisplay(
    modifier: Modifier = Modifier,
    message: String,
    score: Int,
    questionCount: Int,
    goBackClick: () -> Unit
) {
    Surface {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.tertiary
            ) {
                Text(
                    "You got $score out of $questionCount",
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier
                        .padding(20.dp)
                )
            }
            Text(
                message,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(20.dp),
                textAlign = TextAlign.Center

            )
            OutlinedButton(
                onClick = goBackClick
            ) {
                Text("Return",
                    style = MaterialTheme.typography.displaySmall)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FinishedDisplayPreview() {
    TriviaTheme {
        FinishedDisplay(
            modifier = Modifier.fillMaxSize(),
            message = "You suck",
            score = 1,
            questionCount = 10,
            goBackClick = {}

        )
    }
}