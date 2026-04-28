package com.antigravity.twentyfortyeight.ui.scores

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.antigravity.twentyfortyeight.data.ScoreEntity
import com.antigravity.twentyfortyeight.scores.ScoresViewModel
import com.antigravity.twentyfortyeight.theme.*
import com.antigravity.twentyfortyeight.ui.components.tileColor
import com.antigravity.twentyfortyeight.ui.components.tileTextColor

private val gridTabs = listOf(3 to "3×3", 4 to "4×4", 5 to "5×5", 6 to "6×6", 8 to "8×8")

@Composable
fun BestScoresScreen(
    onBack: () -> Unit,
    vm: ScoresViewModel = hiltViewModel()
) {
    val isDark = LocalIsDarkTheme.current
    var selectedTab by remember { mutableIntStateOf(1) } // default to 4×4
    val scores by vm.getTop10(gridTabs[selectedTab].first).collectAsState(initial = emptyList<ScoreEntity>())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDark) BackgroundDark else BackgroundLight)
    ) {
        Spacer(Modifier.statusBarsPadding())

        // Top bar
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, null, tint = if (isDark) OnSurfaceDark else OnSurfaceLight)
            }
            Text(
                "BEST SCORES",
                fontFamily = SyneFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp,
                color = if (isDark) OnSurfaceDark else OnSurfaceLight
            )
        }

        // Tab bar
        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.Transparent,
            contentColor = PrimaryStart,
            edgePadding = 16.dp
        ) {
            gridTabs.forEachIndexed { index, (_, label) ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            label,
                            fontFamily = DmSansFontFamily,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    }
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        if (scores.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🏆", fontSize = 48.sp)
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "No scores yet",
                        fontFamily = DmSansFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = if (isDark) Color(0xFF666680) else Color(0xFFAAAAAA)
                    )
                    Text(
                        "Play a game to set a record!",
                        fontFamily = DmSansFontFamily,
                        fontSize = 13.sp,
                        color = if (isDark) Color(0xFF444460) else Color(0xFFCCCCCC)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(scores) { index, score ->
                    ScoreRow(rank = index + 1, score = score, isDark = isDark)
                }
                item { Spacer(Modifier.navigationBarsPadding()) }
            }
        }
    }
}

@Composable
private fun ScoreRow(rank: Int, score: ScoreEntity, isDark: Boolean) {
    val rankBg = when (rank) {
        1 -> Brush.horizontalGradient(listOf(Color(0xFFFFD700), Color(0xFFFFA500)))
        2 -> Brush.horizontalGradient(listOf(Color(0xFFC0C0C0), Color(0xFF909090)))
        3 -> Brush.horizontalGradient(listOf(Color(0xFFCD7F32), Color(0xFF9D5F22)))
        else -> null
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isDark) SurfaceDark else SurfaceLight, RoundedCornerShape(16.dp))
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Rank badge
        Box(
            modifier = Modifier
                .size(38.dp)
                .then(
                    if (rankBg != null) Modifier.background(rankBg, CircleShape)
                    else Modifier.background(if (isDark) Color(0xFF333348) else Color(0xFFF0ECE6), CircleShape)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "#$rank",
                fontFamily = SyneFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 12.sp,
                color = if (rank <= 3) Color.White else if (isDark) Color(0xFFBBBBCC) else Color(0xFF777777)
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                score.score.toString(),
                fontFamily = SyneFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp,
                color = if (isDark) OnSurfaceDark else OnSurfaceLight
            )
            Text(
                score.date,
                fontFamily = DmSansFontFamily,
                fontSize = 12.sp,
                color = if (isDark) Color(0xFF666680) else Color(0xFFAAAAAA)
            )
        }

        // Max tile chip
        Box(
            modifier = Modifier
                .background(tileColor(score.maxTile), RoundedCornerShape(10.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Text(
                score.maxTile.toString(),
                fontFamily = JetBrainsMonoFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = tileTextColor(score.maxTile)
            )
        }
    }
}
