package com.example.trivia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trivia.ui.quiz.PlayScreen
import com.example.trivia.ui.home.HomeScreen
import com.example.trivia.ui.quiz.QuizViewModel
import com.example.trivia.ui.theme.TriviaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val insetsController =
            WindowCompat.getInsetsController(window, window.decorView)

        insetsController.hide(WindowInsetsCompat.Type.navigationBars())
        insetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

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
                PlayScreen(
                    goBackClick = { navController.popBackStack() },
                )
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