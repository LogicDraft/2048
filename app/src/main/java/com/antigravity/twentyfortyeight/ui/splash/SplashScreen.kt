package com.antigravity.twentyfortyeight.ui.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antigravity.twentyfortyeight.theme.JetBrainsMonoFontFamily
import com.antigravity.twentyfortyeight.theme.PrimaryEnd
import com.antigravity.twentyfortyeight.theme.PrimaryStart
import com.antigravity.twentyfortyeight.theme.Tile2048End
import com.antigravity.twentyfortyeight.theme.Tile2048Start
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToHome: () -> Unit) {
    val scale = remember { Animatable(0.4f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // Animate logo in
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
        )
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 300)
        )
        delay(1000L) // Show for 1s after animation
        onNavigateToHome()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0D0D1A), PrimaryStart, PrimaryEnd, Color(0xFF0D0D1A))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Glowing tile logo
        Box(
            modifier = Modifier
                .scale(scale.value)
                .alpha(alpha.value)
                .size(140.dp)
                .shadow(
                    elevation = 32.dp,
                    shape = RoundedCornerShape(24.dp),
                    ambientColor = Tile2048Start,
                    spotColor = Tile2048Start
                )
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Tile2048Start, Tile2048End)
                    ),
                    shape = RoundedCornerShape(24.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "2048",
                fontFamily = JetBrainsMonoFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 42.sp,
                color = Color.White
            )
        }
    }
}
