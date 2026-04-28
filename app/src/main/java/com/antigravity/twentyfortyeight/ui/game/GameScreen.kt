package com.antigravity.twentyfortyeight.ui.game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antigravity.twentyfortyeight.engine.Direction
import com.antigravity.twentyfortyeight.game.GameViewModel
import com.antigravity.twentyfortyeight.theme.*
import com.antigravity.twentyfortyeight.ui.components.*
import com.antigravity.twentyfortyeight.ui.menu.PauseMenuSheet
import kotlin.math.abs

@Composable
fun GameScreen(
    gridSize: Int,
    onNavigateHome: () -> Unit,
    onNavigateSettings: () -> Unit,
    vm: GameViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsStateWithLifecycle()
    var showPauseMenu by remember { mutableStateOf(false) }
    var showExitDialog by remember { mutableStateOf(false) }
    val isDark = LocalIsDarkTheme.current
    val isActiveGame = !state.isGameOver && !state.isWon

    BackHandler(enabled = showPauseMenu) {
        showPauseMenu = false
    }

    BackHandler(enabled = isActiveGame && !showPauseMenu && !showExitDialog) {
        showExitDialog = true
    }

    // Start a new game with correct gridSize on first load
    LaunchedEffect(gridSize) {
        if (state.board.gridSize != gridSize) {
            vm.startNewGame(gridSize)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (isDark) BackgroundDark else BackgroundLight
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.statusBarsPadding())
            Spacer(Modifier.height(8.dp))

            // Score row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
            ) {
                ScoreCard(
                    label = "SCORE",
                    score = state.board.score,
                    modifier = Modifier.weight(1f),
                    accentColor = PrimaryStart
                )
                ScoreCard(
                    label = "BEST",
                    score = state.bestScore,
                    modifier = Modifier.weight(1f),
                    accentColor = Tile2048Start
                )
                ScoreCard(
                    label = "MOVES",
                    score = state.moveCount,
                    modifier = Modifier.weight(1f),
                    accentColor = AccentStart
                )
            }

            Spacer(Modifier.height(24.dp))

            // Grid — takes up remaining space, max 480dp
            val gridSize2 = state.board.gridSize
            val maxGridSize = minOf(LocalConfiguration.current.screenWidthDp - 32, 480).dp
            GridComposable(
                board = state.board,
                onSwipe = { vm.swipe(it) },
                modifier = Modifier.size(maxGridSize)
            )

            Spacer(Modifier.height(20.dp))

            // Bottom action row
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Undo FAB
                OutlinedIconButton(
                    onClick = { vm.undo() },
                    enabled = state.hasUndo,
                    modifier = Modifier.size(52.dp)
                ) {
                    Icon(
                        Icons.Default.Undo,
                        contentDescription = "Undo",
                        tint = if (state.hasUndo) PrimaryStart else Color.Gray
                    )
                }

                Text(
                    text = "${gridSize2}×${gridSize2}",
                    fontFamily = DmSansFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = if (isDark) Color(0xFF888899) else Color(0xFFAAABA8)
                )

                // Menu FAB
                IconButton(
                    onClick = { showPauseMenu = true },
                    modifier = Modifier
                        .size(52.dp)
                        .background(PrimaryStart, RoundedCornerShape(16.dp))
                ) {
                    Icon(
                        Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }
            }

            Spacer(Modifier.navigationBarsPadding())
        }

        // Win overlay
        AnimatedVisibility(
            visible = state.isWon,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            WinOverlay(
                score = state.board.score,
                onContinue = { vm.continueAfterWin() },
                onNewGame = { vm.startNewGame(gridSize) }
            )
        }

        // Game Over overlay
        AnimatedVisibility(
            visible = state.isGameOver,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            GameOverOverlay(
                score = state.board.score,
                bestScore = state.bestScore,
                onRetry = { vm.startNewGame(gridSize) },
                onMenu = onNavigateHome
            )
        }

        // Pause menu bottom sheet
        if (showPauseMenu) {
            PauseMenuSheet(
                onDismiss = { showPauseMenu = false },
                onResume = { showPauseMenu = false },
                onRestart = {
                    showPauseMenu = false
                    vm.startNewGame(gridSize)
                },
                onSettings = {
                    showPauseMenu = false
                    onNavigateSettings()
                },
                onHome = {
                    showPauseMenu = false
                    vm.saveCurrentGame()
                    onNavigateHome()
                }
            )
        }

        if (showExitDialog) {
            AlertDialog(
                onDismissRequest = { showExitDialog = false },
                shape = RoundedCornerShape(24.dp),
                containerColor = if (isDark) SurfaceDark else SurfaceLight,
                titleContentColor = if (isDark) OnSurfaceDark else OnSurfaceLight,
                textContentColor = if (isDark) Color(0xCCFFFFFF) else Color(0xCC1F1F2E),
                title = {
                    Text(
                        text = "Quit Game?",
                        fontFamily = SyneFontFamily,
                        fontWeight = FontWeight.ExtraBold
                    )
                },
                text = {
                    Text(
                        text = "Your progress is saved.",
                        fontFamily = DmSansFontFamily,
                        fontSize = 14.sp
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showExitDialog = false
                            vm.saveCurrentGame()
                            onNavigateHome()
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryStart)
                    ) {
                        Text(
                            text = "Quit",
                            fontFamily = DmSansFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showExitDialog = false }) {
                        Text(
                            text = "Cancel",
                            fontFamily = DmSansFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isDark) OnSurfaceDark else OnSurfaceLight
                        )
                    }
                }
            )
        }
    }
}
