package com.example.trivia.ui.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.trivia.network.Question
import com.example.trivia.ui.theme.TriviaTheme

@Composable
fun LoadingDisplay(
    modifier: Modifier = Modifier

) {
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        Text("The Quiz is Coming!!!")
    }
}

@Preview(showBackground = true)
@Composable
fun PlayScreenLoading() {
    TriviaTheme {
        LoadingDisplay(modifier = Modifier.fillMaxSize())
    }
}