package com.antigravity.twentyfortyeight.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antigravity.twentyfortyeight.theme.JetBrainsMonoFontFamily
import com.antigravity.twentyfortyeight.theme.TileEmptyLight
import com.antigravity.twentyfortyeight.theme.GridBgLight
import com.antigravity.twentyfortyeight.theme.TileDarkText

// Sample data for each grid size preview
private fun sampleGrid(size: Int): List<List<Int>> = when (size) {
    3 -> listOf(
        listOf(0, 4, 16),
        listOf(2, 8, 2),
        listOf(2, 8, 2)
    )
    4 -> listOf(
        listOf(0, 4, 0, 0),
        listOf(2, 0, 0, 0),
        listOf(0, 0, 4, 0),
        listOf(0, 0, 0, 0)
    )
    5 -> listOf(
        listOf(2, 0, 4, 0, 2),
        listOf(0, 8, 0, 4, 0),
        listOf(4, 0, 16, 0, 4),
        listOf(0, 4, 0, 8, 0),
        listOf(2, 0, 4, 0, 2)
    )
    6 -> listOf(
        listOf(2, 4, 0, 0, 2, 0),
        listOf(0, 0, 4, 2, 0, 4),
        listOf(4, 0, 8, 0, 4, 0),
        listOf(0, 2, 0, 4, 0, 2),
        listOf(2, 0, 4, 0, 8, 0),
        listOf(0, 4, 0, 2, 0, 4)
    )
    else -> List(size) { r -> List(size) { c -> if ((r + c) % 3 == 0) 2 else 0 } }
}

@Composable
fun MiniGridPreview(gridSize: Int) {
    val grid = sampleGrid(gridSize)
    val gap = 4.dp
    val cornerRadius = 8.dp

    BoxWithConstraints(
        modifier = Modifier
            .background(GridBgLight, RoundedCornerShape(16.dp))
            .padding(8.dp)
    ) {
        val totalGaps = (gridSize - 1) * gap.value
        val cellSize = ((maxWidth.value - totalGaps - 16f) / gridSize).dp

        Column(verticalArrangement = Arrangement.spacedBy(gap)) {
            for (row in grid) {
                Row(horizontalArrangement = Arrangement.spacedBy(gap)) {
                    for (value in row) {
                        Box(
                            modifier = Modifier
                                .size(cellSize)
                                .background(
                                    if (value == 0) TileEmptyLight else tileColor(value),
                                    RoundedCornerShape(cornerRadius)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (value != 0 && gridSize <= 5) {
                                Text(
                                    text = value.toString(),
                                    fontFamily = JetBrainsMonoFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = (cellSize.value * 0.32f).sp,
                                    color = tileTextColor(value)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
