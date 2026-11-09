package com.example.trivia

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument


/**
 * Contract for information needed on every navigation destination
 */
interface RallyDestination {
    val route: String
}

/**
 * App navigation destinations
 */
object Home : RallyDestination {
    override val route = "home"
}

object Play : RallyDestination {
    override val route = "play"
}



// Screens to be displayed in the top RallyTabRow
val rallyTabRowScreens = listOf(Home, Play)