package com.antigravity.twentyfortyeight.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

// ---- Color Schemes ----
private val LightColorScheme = lightColorScheme(
    primary = PrimaryStart,
    secondary = AccentStart,
    background = BackgroundLight,
    surface = SurfaceLight,
    onBackground = OnSurfaceLight,
    onSurface = OnSurfaceLight,
    onPrimary = TileLightText,
    onSecondary = TileLightText,
    surfaceVariant = GridBgLight,
    onSurfaceVariant = TileDarkText
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryEnd,
    secondary = AccentEnd,
    background = BackgroundDark,
    surface = SurfaceDark,
    onBackground = OnSurfaceDark,
    onSurface = OnSurfaceDark,
    onPrimary = TileLightText,
    onSecondary = TileLightText,
    surfaceVariant = GridBgDark,
    onSurfaceVariant = TileLightText
)

// ---- Composition Local for dark mode override ----
val LocalIsDarkTheme = staticCompositionLocalOf { false }

@Composable
fun TwentyFortyEightTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    CompositionLocalProvider(LocalIsDarkTheme provides darkTheme) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
            content = content
        )
    }
}
