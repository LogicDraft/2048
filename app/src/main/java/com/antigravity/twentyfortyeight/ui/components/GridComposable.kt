package com.antigravity.twentyfortyeight.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.antigravity.twentyfortyeight.engine.Direction
import com.antigravity.twentyfortyeight.engine.GameBoard
import com.antigravity.twentyfortyeight.theme.GridBgDark
import com.antigravity.twentyfortyeight.theme.GridBgLight
import com.antigravity.twentyfortyeight.theme.LocalIsDarkTheme
import com.antigravity.twentyfortyeight.theme.TileEmptyDark
import com.antigravity.twentyfortyeight.theme.TileEmptyLight
import kotlin.math.abs

@Composable
fun GridComposable(
    board: GameBoard,
    onSwipe: (Direction) -> Unit,
    modifier: Modifier = Modifier,
    gridPadding: Dp = 12.dp,
    tileGap: Dp = 8.dp
) {
    val isDark = LocalIsDarkTheme.current
    val gridBg = if (isDark) GridBgDark else GridBgLight
    val tileEmpty = if (isDark) TileEmptyDark else TileEmptyLight
    val n = board.gridSize
    val motionState = remember(n) { GridMotionState(previousBoard = board) }

    BoxWithConstraints(
        modifier = modifier
            .aspectRatio(1f)
            .background(gridBg, RoundedCornerShape(20.dp))
            .padding(gridPadding)
            .pointerInput(Unit) {
                var dragStartX = 0f
                var dragStartY = 0f
                var swiped = false
                val threshold = 30.dp.toPx()

                detectDragGestures(
                    onDragStart = { offset ->
                        dragStartX = offset.x
                        dragStartY = offset.y
                        swiped = false
                    },
                    onDragEnd = {},
                    onDragCancel = {},
                    onDrag = { change, _ ->
                        change.consume()
                        if (!swiped) {
                            val dx = change.position.x - dragStartX
                            val dy = change.position.y - dragStartY
                            val dist = abs(dx) + abs(dy)
                            if (dist > threshold) {
                                swiped = true
                                if (abs(dx) > abs(dy)) {
                                    if (dx > 0) {
                                        motionState.direction = Direction.RIGHT
                                        onSwipe(Direction.RIGHT)
                                    } else {
                                        motionState.direction = Direction.LEFT
                                        onSwipe(Direction.LEFT)
                                    }
                                } else {
                                    if (dy > 0) {
                                        motionState.direction = Direction.DOWN
                                        onSwipe(Direction.DOWN)
                                    } else {
                                        motionState.direction = Direction.UP
                                        onSwipe(Direction.UP)
                                    }
                                }
                            }
                        }
                    }
                )
            }
    ) {
        val totalGaps = (n - 1) * tileGap.value
        val availableSpace = maxWidth.value - gridPadding.value * 0 - totalGaps
        val cellSize = (availableSpace / n).dp
        val pitch = cellSize + tileGap
        val animatedTiles = remember(board.grid) {
            buildAnimatedTiles(
                previousBoard = motionState.previousBoard,
                board = board,
                direction = motionState.direction
            )
        }

        SideEffect {
            motionState.previousBoard = board
            motionState.direction = null
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(tileGap)
        ) {
            for (row in 0 until n) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(tileGap)
                ) {
                    for (col in 0 until n) {
                        key(row, col) {
                            Box(
                                modifier = Modifier
                                    .size(cellSize)
                                    .background(tileEmpty, RoundedCornerShape(12.dp))
                            )
                        }
                    }
                }
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            animatedTiles.forEach { tile ->
                key(tile.key) {
                    TileComposable(
                        value = tile.value,
                        cellSize = cellSize,
                        motionStart = DpOffset(
                            x = tile.originCol.toGridOffset(pitch),
                            y = tile.originRow.toGridOffset(pitch)
                        ),
                        motionEnd = DpOffset(
                            x = tile.col.toGridOffset(pitch),
                            y = tile.row.toGridOffset(pitch)
                        ),
                        isNew = tile.isNew,
                        isMerged = tile.isMerged
                    )
                }
            }
        }
    }
}

private class GridMotionState(
    var previousBoard: GameBoard,
    var direction: Direction? = null
)

private data class AnimatedGridTile(
    val row: Int,
    val col: Int,
    val value: Int,
    val originRow: Int,
    val originCol: Int,
    val isNew: Boolean,
    val isMerged: Boolean
) {
    val key: String = "$row-$col-$value-$originRow-$originCol-$isNew-$isMerged"
}

private data class PlannedTile(
    val row: Int,
    val col: Int,
    val value: Int,
    val originRow: Int,
    val originCol: Int,
    val isMerged: Boolean
)

private data class GridCell(
    val row: Int,
    val col: Int,
    val value: Int
)

private fun buildAnimatedTiles(
    previousBoard: GameBoard,
    board: GameBoard,
    direction: Direction?
): List<AnimatedGridTile> {
    if (previousBoard.gridSize != board.gridSize || previousBoard.grid == board.grid) {
        return board.nonEmptyCells().map { cell ->
            AnimatedGridTile(
                row = cell.row,
                col = cell.col,
                value = cell.value,
                originRow = cell.row,
                originCol = cell.col,
                isNew = false,
                isMerged = false
            )
        }
    }

    val plannedTiles = direction
        ?.let { planMove(previousBoard, it) }
        .orEmpty()

    return board.nonEmptyCells().map { cell ->
        val planned = plannedTiles[cell.row to cell.col]
        if (planned != null && planned.value == cell.value) {
            AnimatedGridTile(
                row = cell.row,
                col = cell.col,
                value = cell.value,
                originRow = planned.originRow,
                originCol = planned.originCol,
                isNew = false,
                isMerged = planned.isMerged
            )
        } else {
            AnimatedGridTile(
                row = cell.row,
                col = cell.col,
                value = cell.value,
                originRow = cell.row,
                originCol = cell.col,
                isNew = true,
                isMerged = false
            )
        }
    }
}

private fun planMove(
    board: GameBoard,
    direction: Direction
): Map<Pair<Int, Int>, PlannedTile> {
    val n = board.gridSize
    val planned = mutableMapOf<Pair<Int, Int>, PlannedTile>()

    val lines = when (direction) {
        Direction.LEFT -> (0 until n).map { row -> (0 until n).map { col -> row to col } }
        Direction.RIGHT -> (0 until n).map { row -> (n - 1 downTo 0).map { col -> row to col } }
        Direction.UP -> (0 until n).map { col -> (0 until n).map { row -> row to col } }
        Direction.DOWN -> (0 until n).map { col -> (n - 1 downTo 0).map { row -> row to col } }
    }

    lines.forEach { positions ->
        val source = positions.mapNotNull { (row, col) ->
            val value = board.get(row, col)
            if (value == 0) null else GridCell(row, col, value)
        }

        var sourceIndex = 0
        var targetIndex = 0
        while (sourceIndex < source.size) {
            val first = source[sourceIndex]
            val second = source.getOrNull(sourceIndex + 1)
            val target = positions[targetIndex]
            val merged = second != null && first.value == second.value
            val value = if (merged) first.value * 2 else first.value

            planned[target] = PlannedTile(
                row = target.first,
                col = target.second,
                value = value,
                originRow = first.row,
                originCol = first.col,
                isMerged = merged
            )

            sourceIndex += if (merged) 2 else 1
            targetIndex += 1
        }
    }

    return planned
}

private fun GameBoard.nonEmptyCells(): List<GridCell> =
    grid.flatMapIndexed { row, values ->
        values.mapIndexedNotNull { col, value ->
            if (value == 0) null else GridCell(row, col, value)
        }
    }

private fun Int.toGridOffset(pitch: Dp): Dp = (pitch.value * this).dp
