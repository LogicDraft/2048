package com.antigravity.twentyfortyeight.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antigravity.twentyfortyeight.theme.*

@Composable
fun WinOverlay(
    score: Int,
    onContinue: () -> Unit,
    onNewGame: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x88000000)),
        contentAlignment = Alignment.Center
    ) {
        ConfettiOverlay()

        // Win Card
        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .clip(RoundedCornerShape(28.dp))
                .background(
                    Brush.verticalGradient(listOf(Color(0xFF2A1060), Color(0xFF1A306A)))
                )
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "🎉",
                fontSize = 56.sp
            )
            Spacer(Modifier.height(12.dp))
            Text(
                "You Win!",
                fontFamily = SyneFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 36.sp,
                color = Tile2048Start
            )
            Text(
                "You reached 2048!",
                fontFamily = DmSansFontFamily,
                fontSize = 16.sp,
                color = Color(0xAAFFFFFF)
            )
            Spacer(Modifier.height(20.dp))
            Text(
                "SCORE",
                fontFamily = DmSansFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = Color(0x99FFFFFF)
            )
            Text(
                score.toString(),
                fontFamily = SyneFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 42.sp,
                color = Color.White
            )
            Spacer(Modifier.height(28.dp))

            // Continue button
            Button(
                onClick = onContinue,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(listOf(Tile2048Start, Tile2048End)),
                            RoundedCornerShape(50.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "KEEP GOING",
                        fontFamily = DmSansFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
            OutlinedButton(
                onClick = onNewGame,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
            ) {
                Text(
                    "NEW GAME",
                    fontFamily = DmSansFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
            }
        }
    }
}

@Composable
fun GameOverOverlay(
    score: Int,
    bestScore: Int,
    onRetry: () -> Unit,
    onMenu: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xBB000000)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .clip(RoundedCornerShape(28.dp))
                .background(
                    Brush.verticalGradient(listOf(Color(0xFF1A1A2E), Color(0xFF16213E)))
                )
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("😔", fontSize = 56.sp)
            Spacer(Modifier.height(8.dp))
            Text(
                "Game Over",
                fontFamily = SyneFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp,
                color = Color.White
            )
            Text(
                "No more moves left",
                fontFamily = DmSansFontFamily,
                fontSize = 14.sp,
                color = Color(0xAAFFFFFF)
            )
            Spacer(Modifier.height(24.dp))

            // Score summary row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("SCORE", fontFamily = DmSansFontFamily, fontSize = 11.sp, color = Color(0x99FFFFFF), fontWeight = FontWeight.Medium)
                    Text(score.toString(), fontFamily = SyneFontFamily, fontWeight = FontWeight.ExtraBold, fontSize = 28.sp, color = PrimaryEnd)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("BEST", fontFamily = DmSansFontFamily, fontSize = 11.sp, color = Color(0x99FFFFFF), fontWeight = FontWeight.Medium)
                    Text(bestScore.toString(), fontFamily = SyneFontFamily, fontWeight = FontWeight.ExtraBold, fontSize = 28.sp, color = Tile2048Start)
                }
            }

            Spacer(Modifier.height(28.dp))

            Button(
                onClick = onRetry,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(listOf(PrimaryStart, PrimaryEnd)),
                            RoundedCornerShape(50.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("TRY AGAIN", fontFamily = DmSansFontFamily, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
                }
            }
            Spacer(Modifier.height(10.dp))
            OutlinedButton(
                onClick = onMenu,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
            ) {
                Text("MAIN MENU", fontFamily = DmSansFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            }
        }
    }
}
