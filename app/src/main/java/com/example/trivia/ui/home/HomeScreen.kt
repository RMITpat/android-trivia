package com.example.trivia.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.trivia.ui.theme.TriviaTheme

@Composable
fun HomeScreen(
    onPlayClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier,
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

@Composable
fun BottomBar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.secondary,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = { onClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    "Play",
                    style = MaterialTheme.typography.displayMedium
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

    TopAppBar(
        title = {
            Text(
                text = "Trivia!",
                style = MaterialTheme.typography.displayLarge,
                modifier = modifier,

            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TriviaTheme() {
        HomeScreen(
            onPlayClick = {},
        )
    }
}