package com.aziz.myjuice.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Black

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Red,
    secondary = Green,
    background = Pink,
    surface = Gray,
    onPrimary = Black,
    onSecondary = Orange,
    onBackground = Black,
    onSurface = Black
)

@Composable
fun MyJuiceTheme(lightTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (lightTheme) {
        LightColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        content = content
    )
}