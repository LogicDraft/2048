package com.antigravity.twentyfortyeight.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antigravity.twentyfortyeight.theme.*

@Composable
fun ScoreCard(
    label: String,
    score: Int,
    modifier: Modifier = Modifier,
    accentColor: Color = PrimaryStart
) {
    val isDark = LocalIsDarkTheme.current

    // Animated count-up
    var displayedScore by remember { mutableIntStateOf(0) }
    val animatedScore by animateIntAsState(
        targetValue = score,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
        label = "scoreAnim"
    )

    Box(
        modifier = modifier
            .background(
                if (isDark) Color(0x26FFFFFF) else Color(0x40FFFFFF),
                RoundedCornerShape(20.dp)
            )
            .border(
                1.dp,
                if (isDark) Color(0x4DFFFFFF) else Color(0x60CCCCCC),
                RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = label,
                fontFamily = DmSansFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                color = accentColor
            )
            Text(
                text = animatedScore.toString(),
                fontFamily = SyneFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
                color = if (isDark) OnSurfaceDark else OnSurfaceLight
            )
        }
    }
}
