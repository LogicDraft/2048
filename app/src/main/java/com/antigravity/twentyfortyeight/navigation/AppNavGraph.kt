package com.antigravity.twentyfortyeight.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.antigravity.twentyfortyeight.ui.game.GameScreen
import com.antigravity.twentyfortyeight.ui.home.HomeScreen
import com.antigravity.twentyfortyeight.ui.multiplayer.MultiplayerScreen
import com.antigravity.twentyfortyeight.ui.scores.BestScoresScreen
import com.antigravity.twentyfortyeight.ui.settings.SettingsScreen
import com.antigravity.twentyfortyeight.ui.splash.SplashScreen

object Routes {
    const val SPLASH = "splash"
    const val HOME = "home"
    const val GAME = "game/{gridSize}"
    const val MULTIPLAYER = "multiplayer"
    const val SETTINGS = "settings"
    const val BEST_SCORES = "best_scores"

    fun game(gridSize: Int) = "game/$gridSize"
}

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(onNavigateToHome = {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            })
        }

        composable(Routes.HOME) {
            HomeScreen(
                onStartGame = { gridSize ->
                    navController.navigate(Routes.game(gridSize))
                },
                onBestScores = { navController.navigate(Routes.BEST_SCORES) },
                onMultiplayer = { navController.navigate(Routes.MULTIPLAYER) },
                onSettings = { navController.navigate(Routes.SETTINGS) }
            )
        }

        composable(Routes.GAME) { backStack ->
            val gridSize = backStack.arguments?.getString("gridSize")?.toIntOrNull() ?: 4
            GameScreen(
                gridSize = gridSize,
                onNavigateHome = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                },
                onNavigateSettings = { navController.navigate(Routes.SETTINGS) }
            )
        }

        composable(Routes.MULTIPLAYER) {
            MultiplayerScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.BEST_SCORES) {
            BestScoresScreen(onBack = { navController.popBackStack() })
        }
    }
}
