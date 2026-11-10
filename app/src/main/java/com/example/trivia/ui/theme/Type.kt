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
    displayMedium = TextStyle(
        fontFamily = GameFont,
        fontWeight = FontWeight.Black,
        fontSize = 40.sp
    ),
    displayLarge = TextStyle(
        fontFamily = GameFont,
        fontWeight = FontWeight.Bold,
        fontSize = 60.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = GameFont,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    labelLarge = TextStyle(
        fontFamily = GameFont,
        fontWeight = FontWeight.Black,
        fontSize = 16.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = GameFont,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        lineHeight = 32.sp
    ),
    titleLarge = TextStyle(
        fontFamily = GameFont,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),

)