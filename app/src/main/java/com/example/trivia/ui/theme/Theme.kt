package com.example.trivia.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF9DD8AE),
    onPrimary = Color(0xFF123522),

    secondary = Color(0xFFB4CCB7),
    onSecondary = Color(0xFF24372A),

    tertiary = Color(0xFFE5BF8E),
    onTertiary = Color(0xFF432B12),

    background = Color(0xFF101D16),
    onBackground = Color(0xFFE2EEE4),

    surface = Color(0xFF1B2B20),
    onSurface = Color(0xFFE2EEE4)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF004FA3),
    onPrimary = Color.White,

    secondary = Color(0xFF3168A0),
    onSecondary = Color.White,

    tertiary = Color(0xFF3656B8),
    onTertiary = Color.White,

    background = Color(0xFFBBD9FA),
    onBackground = Color(0xFF102B4A),

    surface = Color(0xFFDCEBFF),
    onSurface = Color(0xFF102B4A)
)

@Composable
fun TriviaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}