package com.antigravity.twentyfortyeight.ui.multiplayer

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antigravity.twentyfortyeight.engine.Direction
import com.antigravity.twentyfortyeight.engine.GameBoard
import com.antigravity.twentyfortyeight.theme.*
import com.antigravity.twentyfortyeight.ui.components.GridComposable
import kotlinx.coroutines.delay

@Composable
fun MultiplayerScreen(onBack: () -> Unit) {
    val isDark = LocalIsDarkTheme.current

    // 3-minute countdown timer
    var secondsLeft by remember { mutableIntStateOf(180) }
    var gameRunning by remember { mutableStateOf(true) }

    // Player boards
    var playerBoard by remember { mutableStateOf(GameBoard.create(4)) }
    var opponentBoard by remember { mutableStateOf(GameBoard.create(4)) }

    // Simulate opponent moves
    LaunchedEffect(gameRunning) {
        while (gameRunning && secondsLeft > 0) {
            delay(1000L)
            secondsLeft--
            // Simulate opponent making random move
            if (secondsLeft % 3 == 0) {
                val dirs = listOf(Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN)
                val (newBoard, moved) = when (dirs.random()) {
                    Direction.LEFT -> opponentBoard.moveLeft()
                    Direction.RIGHT -> opponentBoard.moveRight()
                    Direction.UP -> opponentBoard.moveUp()
                    Direction.DOWN -> opponentBoard.moveDown()
                }
                if (moved) opponentBoard = newBoard
            }
            if (secondsLeft <= 0) gameRunning = false
        }
    }

    // Timer color
    val timerColor by animateColorAsState(
        targetValue = when {
            secondsLeft > 60 -> TimerGreen
            secondsLeft > 30 -> TimerYellow
            else -> TimerRed
        },
        animationSpec = tween(500), label = "timer"
    )

    val minutes = secondsLeft / 60
    val secs = secondsLeft % 60
    val timeStr = "%d:%02d".format(minutes, secs)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDark) BackgroundDark else BackgroundLight),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.statusBarsPadding())

        // Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.Home, null, tint = PlayerBlue)
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(Brush.horizontalGradient(listOf(PrimaryStart, PlayerBlue)))
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) {
                Text(
                    "MULTIPLAYER VS",
                    fontFamily = SyneFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
            Spacer(Modifier.width(48.dp))
        }

        // Timer badge
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(timerColor.copy(alpha = 0.15f))
                .padding(horizontal = 24.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "TIME",
                    fontFamily = DmSansFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 11.sp,
                    color = timerColor
                )
                Text(
                    timeStr,
                    fontFamily = SyneFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 28.sp,
                    color = timerColor
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        // Score row — YOU vs OPPONENT
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            PlayerScoreCard("YOU", playerBoard.score, PlayerYellow, PlayerYellowDark)
            Text(
                "VS",
                fontFamily = SyneFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp,
                color = if (isDark) Color(0xFF666680) else Color(0xFFAAAAAA),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            PlayerScoreCard("OPPONENT", opponentBoard.score, PlayerBlue, PlayerBlueDark)
        }

        Spacer(Modifier.height(20.dp))

        // Player interactive grid
        val gridMaxSize = 340.dp
        GridComposable(
            board = playerBoard,
            onSwipe = { dir ->
                val (newBoard, moved) = when (dir) {
                    Direction.LEFT -> playerBoard.moveLeft()
                    Direction.RIGHT -> playerBoard.moveRight()
                    Direction.UP -> playerBoard.moveUp()
                    Direction.DOWN -> playerBoard.moveDown()
                }
                if (moved) playerBoard = newBoard
            },
            modifier = Modifier.size(gridMaxSize).padding(horizontal = 16.dp)
        )

        // Game Over overlay when time runs out
        if (!gameRunning) {
            val playerWon = playerBoard.score > opponentBoard.score
            Box(
                modifier = Modifier.fillMaxSize().background(Color(0xCC000000)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .clip(RoundedCornerShape(24.dp))
                        .background(if (playerWon) Brush.verticalGradient(listOf(Color(0xFF1A3A1A), Color(0xFF0D1F0D)))
                        else Brush.verticalGradient(listOf(Color(0xFF3A1A1A), Color(0xFF1F0D0D))))
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(if (playerWon) "🏆" else "😔", fontSize = 56.sp)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        if (playerWon) "YOU WIN!" else "DEFEAT",
                        fontFamily = SyneFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 32.sp,
                        color = if (playerWon) TimerGreen else TimerRed
                    )
                    Spacer(Modifier.height(20.dp))
                    Button(
                        onClick = onBack,
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryStart)
                    ) {
                        Text("MAIN MENU", fontFamily = DmSansFontFamily, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }

        Spacer(Modifier.navigationBarsPadding())
    }
}

@Composable
private fun PlayerScoreCard(name: String, score: Int, cardColor: Color, darkCardColor: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            name,
            fontFamily = DmSansFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = cardColor
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(cardColor)
                .padding(horizontal = 24.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                score.toString(),
                fontFamily = SyneFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp,
                color = Color.White
            )
        }
    }
}
