package com.antigravity.twentyfortyeight.engine

/**
 * Utility to calculate score-related stats.
 * Score accumulation is handled directly in GameBoard,
 * this is used for stats (max tile, etc).
 */
object ScoreCalculator {

    /** Find the highest tile value on the board */
    fun maxTile(board: GameBoard): Int =
        board.grid.flatten().maxOrNull() ?: 0

    /** Convert raw grid to a serializable string for DataStore */
    fun serializeGrid(board: GameBoard): String =
        board.grid.flatten().joinToString(",")

    /** Restore grid from serialized string */
    fun deserializeGrid(data: String, gridSize: Int): GameBoard {
        return try {
            val values = data.split(",").map { it.trim().toInt() }
            val grid = List(gridSize) { r -> List(gridSize) { c -> values[r * gridSize + c] } }
            GameBoard(grid = grid, gridSize = gridSize)
        } catch (e: Exception) {
            GameBoard.create(gridSize)
        }
    }
}
