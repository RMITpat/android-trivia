package com.example.trivia

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trivia.ui.theme.TriviaTheme

@Composable
fun HomeScreen(
    onPlayClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
    ) {
        Scaffold(
            topBar = { Topbar() },

            bottomBar = {
                BottomBar(
                    onClick = onPlayClick,
                    modifier = Modifier
                    .fillMaxWidth()
                )
            }
        ) { padding ->
            Text(
                text = "Past games",
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Composable
fun BottomBar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = { onClick() }
            ) {
                Text(
                    "Play",
                    style = MaterialTheme.typography.displaySmall
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Topbar(
    modifier: Modifier = Modifier
) {
    TopAppBar(title = {
        Text(
            text = "Trivia!",
            style = MaterialTheme.typography.displayMedium,
            modifier = modifier
        )
    }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TriviaTheme {
        HomeScreen(
            onPlayClick = {},
        )
    }
}