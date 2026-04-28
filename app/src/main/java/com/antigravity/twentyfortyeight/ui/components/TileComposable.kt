package com.antigravity.twentyfortyeight.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antigravity.twentyfortyeight.theme.*
import kotlinx.coroutines.launch

fun tileColor(value: Int): Color = when (value) {
    0 -> Color.Transparent
    2 -> Tile2
    4 -> Tile4
    8 -> Tile8
    16 -> Tile16
    32 -> Tile32
    64 -> Tile64
    128 -> Tile128
    256 -> Tile256
    512 -> Tile512
    1024 -> Tile1024
    2048 -> Tile2048Start
    else -> TileHigh
}

fun tileTextColor(value: Int): Color = when {
    value <= 4 -> TileDarkText
    else -> TileLightText
}

fun tileFontSize(value: Int, cellSize: Dp): Float {
    val base = cellSize.value * 0.36f
    return when {
        value >= 1024 -> base * 0.75f
        value >= 128 -> base * 0.85f
        else -> base
    }
}

@Composable
fun TileComposable(
    value: Int,
    cellSize: Dp,
    modifier: Modifier = Modifier,
    motionStart: DpOffset = DpOffset.Zero,
    motionEnd: DpOffset = DpOffset.Zero,
    isNew: Boolean = false,
    isMerged: Boolean = false
) {
    val offsetX = remember { Animatable(motionStart.x, Dp.VectorConverter) }
    val offsetY = remember { Animatable(motionStart.y, Dp.VectorConverter) }

    LaunchedEffect(motionStart, motionEnd, value) {
        offsetX.snapTo(motionStart.x)
        offsetY.snapTo(motionStart.y)

        val slideSpec = tween<Dp>(
            durationMillis = 170,
            easing = FastOutSlowInEasing
        )

        launch { offsetX.animateTo(motionEnd.x, slideSpec) }
        launch { offsetY.animateTo(motionEnd.y, slideSpec) }
    }

    val mergeScale = remember { Animatable(1f) }
    val spawnScale = remember { Animatable(if (isNew) 0.86f else 1f) }
    val spawnAlpha = remember { Animatable(if (isNew) 0f else 1f) }

    LaunchedEffect(isNew, value) {
        if (isNew && value != 0) {
            spawnScale.snapTo(0.86f)
            spawnAlpha.snapTo(0f)
            launch { spawnScale.animateTo(1f, tween(180, easing = FastOutSlowInEasing)) }
            launch { spawnAlpha.animateTo(1f, tween(140)) }
        } else {
            spawnScale.snapTo(1f)
            spawnAlpha.snapTo(1f)
        }
    }

    LaunchedEffect(isMerged, value) {
        if (isMerged && value != 0) {
            mergeScale.snapTo(1f)
            mergeScale.animateTo(
                1.12f,
                spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMediumLow
                )
            )
            mergeScale.animateTo(
                1f,
                spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
        } else {
            mergeScale.snapTo(1f)
        }
    }

    val bgColor = tileColor(value)
    val textColor = tileTextColor(value)
    val fontSize = tileFontSize(value, cellSize)

    val is2048 = value == 2048
    val glowColor = if (value >= 512) tileColor(value) else Color.Transparent

    Box(
        modifier = modifier
            .offset(x = offsetX.value, y = offsetY.value)
            .size(cellSize)
            .graphicsLayer {
                val scale = mergeScale.value * spawnScale.value.coerceAtLeast(0.01f)
                scaleX = scale
                scaleY = scale
                alpha = spawnAlpha.value
            }
            .shadow(
                elevation = if (value >= 512) 12.dp else 4.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = glowColor,
                ambientColor = glowColor
            )
            .then(
                if (is2048) Modifier.background(
                    Brush.linearGradient(listOf(Tile2048Start, Tile2048End)),
                    RoundedCornerShape(16.dp)
                )
                else Modifier.background(bgColor, RoundedCornerShape(16.dp))
            ),
        contentAlignment = Alignment.Center
    ) {
        if (value != 0) {
            Text(
                text = value.toString(),
                fontFamily = JetBrainsMonoFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = fontSize.sp,
                color = textColor
            )
        }
    }
}
