package com.antigravity.twentyfortyeight.ui.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsMartialArts
import androidx.compose.material3.*
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
import com.antigravity.twentyfortyeight.ui.components.MiniGridPreview

data class GridOption(val size: Int, val label: String)

private val gridOptions = listOf(
    GridOption(3, "Small · 3×3"),
    GridOption(4, "Classic · 4×4"),
    GridOption(5, "Large · 5×5"),
    GridOption(6, "XL · 6×6"),
    GridOption(8, "Mega · 8×8")
)

@Composable
fun HomeScreen(
    onStartGame: (Int) -> Unit,
    onBestScores: () -> Unit,
    onMultiplayer: () -> Unit,
    onSettings: () -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 1) { gridOptions.size }
    val selectedOption = gridOptions[pagerState.currentPage]
    val isDark = LocalIsDarkTheme.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (isDark) Brush.verticalGradient(listOf(BackgroundDark, SurfaceDark))
                else Brush.verticalGradient(listOf(BackgroundLight, Color(0xFFEDE8DF)))
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.statusBarsPadding())

            // Top bar
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // App name + logo
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                Brush.linearGradient(listOf(Tile2048Start, Tile2048End)),
                                RoundedCornerShape(10.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "2048",
                            fontFamily = JetBrainsMonoFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                            color = Color.White
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    Text(
                        "2048",
                        fontFamily = SyneFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 28.sp,
                        color = if (isDark) OnSurfaceDark else OnSurfaceLight
                    )
                }

                // Settings button
                IconButton(
                    onClick = onSettings,
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            if (isDark) SurfaceDark else Color(0xFFF0EBE0),
                            RoundedCornerShape(12.dp)
                        )
                ) {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = if (isDark) OnSurfaceDark else OnSurfaceLight
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            // Grid size label
            Text(
                text = selectedOption.label,
                fontFamily = DmSansFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = PrimaryStart
            )

            Spacer(Modifier.height(16.dp))

            // Glassmorphism card with grid preview carousel
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(if (isDark) Color(0x26FFFFFF) else Color(0x40FFFFFF))
                    .border(
                        1.dp,
                        if (isDark) Color(0x4DFFFFFF) else Color(0x60FFFFFF),
                        RoundedCornerShape(28.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 40.dp)
                ) { page ->
                    Box(
                        modifier = Modifier.fillMaxSize().padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        MiniGridPreview(gridSize = gridOptions[page].size)
                    }
                }
            }

            // Page indicator dots
            Spacer(Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                gridOptions.forEachIndexed { index, _ ->
                    val isSelected = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .size(if (isSelected) 10.dp else 6.dp)
                            .background(
                                if (isSelected) PrimaryStart else Color(0xFFBBB5AF),
                                CircleShape
                            )
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            // START GAME button
            Button(
                onClick = { onStartGame(selectedOption.size) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
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
                    Text(
                        "START GAME",
                        fontFamily = DmSansFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // HIGH SCORE outlined button
            OutlinedButton(
                onClick = onBestScores,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(50.dp),
                border = androidx.compose.foundation.BorderStroke(
                    1.5.dp, Brush.horizontalGradient(listOf(PrimaryStart, PrimaryEnd))
                ),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryStart)
            ) {
                Text(
                    "HIGH SCORES",
                    fontFamily = DmSansFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
            }

            Spacer(Modifier.height(12.dp))

            // Multiplayer VS button
            OutlinedButton(
                onClick = onMultiplayer,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(50.dp),
                border = androidx.compose.foundation.BorderStroke(
                    1.5.dp, Brush.horizontalGradient(listOf(AccentStart, AccentEnd))
                ),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = AccentStart)
            ) {
                Text(
                    "MULTIPLAYER VS",
                    fontFamily = DmSansFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
            }

            Spacer(Modifier.weight(1f))

            // Bottom nav area
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavItem(Icons.Default.GridView, "Home", true)
                BottomNavItem(Icons.Default.SportsMartialArts, "VS", false, onMultiplayer)
            }

            Spacer(Modifier.navigationBarsPadding())
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit = {}
) {
    val isDark = LocalIsDarkTheme.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Icon(
            icon,
            contentDescription = label,
            tint = if (selected) PrimaryStart else if (isDark) Color(0xFF666680) else Color(0xFFAAADAF),
            modifier = Modifier.size(26.dp)
        )
        Text(
            label,
            fontFamily = DmSansFontFamily,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            fontSize = 11.sp,
            color = if (selected) PrimaryStart else if (isDark) Color(0xFF666680) else Color(0xFFAAADAF)
        )
    }
}
