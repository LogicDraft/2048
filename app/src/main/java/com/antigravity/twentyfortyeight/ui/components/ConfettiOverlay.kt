package com.antigravity.twentyfortyeight.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

private data class Particle(
    var x: Float, var y: Float,
    val vx: Float, val vy: Float,
    val color: Color,
    val size: Float,
    val rotation: Float
)

private val confettiColors = listOf(
    Color(0xFFFFD700), Color(0xFFFF6B6B), Color(0xFF4ECDC4),
    Color(0xFF45B7D1), Color(0xFF96CEB4), Color(0xFFFF8B94),
    Color(0xFFFECEA8), Color(0xFFA8E6CF), Color(0xFF7C3AED)
)

@Composable
fun ConfettiOverlay(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "confetti")
    val tick by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(16, easing = LinearEasing)),
        label = "tick"
    )

    val particles = remember {
        List(80) {
            Particle(
                x = Random.nextFloat(),
                y = Random.nextFloat() * -0.5f, // start above screen
                vx = (Random.nextFloat() - 0.5f) * 0.004f,
                vy = Random.nextFloat() * 0.006f + 0.003f,
                color = confettiColors.random(),
                size = Random.nextFloat() * 12f + 6f,
                rotation = Random.nextFloat() * 360f
            )
        }.toMutableList()
    }

    // Advance particles on each tick
    LaunchedEffect(tick) {
        particles.forEachIndexed { i, p ->
            particles[i] = p.copy(
                x = (p.x + p.vx + 1f) % 1f,
                y = if (p.y > 1.2f) Random.nextFloat() * -0.2f else p.y + p.vy
            )
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        particles.forEach { p ->
            val px = p.x * size.width
            val py = p.y * size.height
            drawCircle(
                color = p.color.copy(alpha = 0.85f),
                radius = p.size,
                center = Offset(px, py)
            )
        }
    }
}
