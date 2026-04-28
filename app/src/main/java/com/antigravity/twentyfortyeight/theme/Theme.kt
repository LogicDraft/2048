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
val LocalGameColors = staticCompositionLocalOf { ClassicGameColors }

@Composable
fun TwentyFortyEightTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    currentTheme: String = "classic",
    content: @Composable () -> Unit
) {
    val gameColors = gameColorsFor(currentTheme, darkTheme)
    val baseColorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val colorScheme = baseColorScheme.copy(
        primary = gameColors.primary,
        secondary = gameColors.secondary,
        surfaceVariant = gameColors.gridBackground,
        onSurfaceVariant = gameColors.tileText(2)
    )

    CompositionLocalProvider(
        LocalIsDarkTheme provides darkTheme,
        LocalGameColors provides gameColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
            content = content
        )
    }
}

private fun gameColorsFor(theme: String, darkTheme: Boolean): GameColors =
    when (theme.lowercase()) {
        "neon" -> NeonGameColors
        "glass" -> FrostedGlassGameColors
        "retro" -> RetroGameColors
        "minimalist" -> MinimalistGameColors
        "dark" -> ClassicGameColors.copy(
            primary = PrimaryEnd,
            secondary = AccentEnd,
            gridBackground = GridBgDark,
            emptyTile = TileEmptyDark,
            tileDarkText = TileDarkText,
            tileLightText = TileLightText
        )
        else -> if (darkTheme) {
            ClassicGameColors.copy(
                primary = PrimaryEnd,
                secondary = AccentEnd,
                gridBackground = GridBgDark,
                emptyTile = TileEmptyDark
            )
        } else {
            ClassicGameColors
        }
    }
