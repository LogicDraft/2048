package com.antigravity.twentyfortyeight.engine

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Utility to calculate score-related stats.
 * Score accumulation is handled directly in GameBoard,
 * this is used for stats (max tile, etc).
 */
object ScoreCalculator {

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    /** Find the highest tile value on the board */
    fun maxTile(board: GameBoard): Int =
        board.grid.flatten().maxOrNull() ?: 0

    /** Convert the full board state to a JSON string for DataStore */
    fun serializeGrid(board: GameBoard): String =
        json.encodeToString(board)

    /** Restore board state from a JSON string */
    fun deserializeGrid(data: String, gridSize: Int): GameBoard {
        return try {
            val board = json.decodeFromString<GameBoard>(data)
            if (board.hasValidGrid()) board else GameBoard.create(gridSize)
        } catch (e: Exception) {
            deserializeLegacyGrid(data, gridSize) ?: GameBoard.create(gridSize)
        }
    }

    private fun GameBoard.hasValidGrid(): Boolean =
        grid.size == gridSize && grid.all { row -> row.size == gridSize }

    private fun deserializeLegacyGrid(data: String, gridSize: Int): GameBoard? {
        return try {
            val values = data.split(",").map { it.trim().toInt() }
            if (values.size != gridSize * gridSize) return null
            val grid = List(gridSize) { r -> List(gridSize) { c -> values[r * gridSize + c] } }
            GameBoard(grid = grid, gridSize = gridSize)
        } catch (e: Exception) {
            null
        }
    }
}
