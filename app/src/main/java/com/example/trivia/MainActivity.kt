package com.example.trivia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trivia.quiz.PlayScreen
import com.example.trivia.ui.theme.TriviaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TriviaApp()
        }
    }
}


@Composable
fun TriviaApp() {
    TriviaTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Home.route
        ) {
            composable(route = Home.route)  {
                HomeScreen(
                    onPlayClick = { navController.navigateSingleTopTo(Play.route) }
                )
            }
            composable(route = Play.route) {
                PlayScreen()
            }

        }
    }
}


fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        )
        launchSingleTop = true
    }