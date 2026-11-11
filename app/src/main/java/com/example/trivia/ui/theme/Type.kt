package com.example.trivia.ui.theme

import android.os.Build
import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.trivia.R

// Set of Material typography styles to start with
object DisplayLargeVFConfig {
    const val WEIGHT = 300
    const val WIDTH = 30f
    const val ASCENDER_HEIGHT = 800f
    const val COUNTER_WIDTH = 500
}


@OptIn(ExperimentalTextApi::class)
val GameFont = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    FontFamily(
        Font(
            R.font.fredoka_variable,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(DisplayLargeVFConfig.WEIGHT),
            )
        )
    )
} else {
    FontFamily.Default
}

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = GameFont,
        fontWeight = FontWeight.Black,
        fontSize = 42.sp,
        lineHeight = 48.sp
    ),
    displayMedium = TextStyle(
        fontFamily = GameFont,
        fontWeight = FontWeight.Black,
        fontSize = 30.sp,
        lineHeight = 46.sp
    ),
    displaySmall = TextStyle(
        fontFamily = GameFont,
        fontWeight = FontWeight.Black,
        fontSize = 21.sp,
        lineHeight = 39.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = GameFont,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 26.sp
    ),
    titleLarge = TextStyle(
        fontFamily = GameFont,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = GameFont,
        fontWeight = FontWeight.Black,
        fontSize = 34.sp,
        lineHeight = 39.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = GameFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 19.sp,
        lineHeight = 26.sp
    ),
    labelLarge = TextStyle(
        fontFamily = GameFont,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        lineHeight = 16.sp
    )
)