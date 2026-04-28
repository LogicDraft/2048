package com.antigravity.twentyfortyeight.engine

import kotlinx.serialization.Serializable

/**
 * Pure game logic — no Android/Compose dependencies.
 * Represents the current state of a 2048 board.
 */
@Serializable
data class GameBoard(
    val grid: List<List<Int>>,
    val score: Int = 0,
    val gridSize: Int = 4
) {
    // Access a cell
    fun get(row: Int, col: Int): Int = grid[row][col]

    companion object {
        /** Create a fresh board with 2 random tiles spawned */
        fun create(gridSize: Int = 4): GameBoard {
            val emptyGrid = List(gridSize) { List(gridSize) { 0 } }
            val board = GameBoard(emptyGrid, 0, gridSize)
            return board.spawnTile().spawnTile()
        }
    }

    /** Return all positions (row, col) that are 0 */
    fun emptyPositions(): List<Pair<Int, Int>> =
        grid.flatMapIndexed { r, row ->
            row.mapIndexedNotNull { c, v -> if (v == 0) r to c else null }
        }

    /** Spawn a new tile (90% chance = 2, 10% chance = 4) at a random empty position */
    fun spawnTile(): GameBoard {
        val empties = emptyPositions()
        if (empties.isEmpty()) return this
        val (r, c) = empties.random()
        val value = if (Math.random() < 0.9) 2 else 4
        val newGrid = grid.mapIndexed { ri, row ->
            if (ri == r) row.mapIndexed { ci, v -> if (ci == c) value else v }
            else row
        }
        return copy(grid = newGrid)
    }

    /** Check if any tile equals 2048 (win condition) */
    fun isWon(): Boolean = grid.any { row -> row.any { it >= 2048 } }

    /** Check if no moves are possible (game over) */
    fun isGameOver(): Boolean {
        if (emptyPositions().isNotEmpty()) return false
        // Check horizontal merges
        for (r in 0 until gridSize) {
            for (c in 0 until gridSize - 1) {
                if (grid[r][c] == grid[r][c + 1]) return false
            }
        }
        // Check vertical merges
        for (r in 0 until gridSize - 1) {
            for (c in 0 until gridSize) {
                if (grid[r][c] == grid[r + 1][c]) return false
            }
        }
        return true
    }

    // ---- Move operations ----

    fun moveLeft(): Pair<GameBoard, Boolean> {
        var moved = false
        var points = 0
        val newGrid = grid.map { row ->
            val (merged, pts) = TileMerger.mergeRow(row)
            if (merged != row) moved = true
            points += pts
            merged
        }
        return if (moved) {
            val b = copy(grid = newGrid, score = score + points).spawnTile()
            b to true
        } else this to false
    }

    fun moveRight(): Pair<GameBoard, Boolean> {
        var moved = false
        var points = 0
        val newGrid = grid.map { row ->
            val reversed = row.reversed()
            val (merged, pts) = TileMerger.mergeRow(reversed)
            val result = merged.reversed()
            if (result != row) moved = true
            points += pts
            result
        }
        return if (moved) {
            val b = copy(grid = newGrid, score = score + points).spawnTile()
            b to true
        } else this to false
    }

    fun moveUp(): Pair<GameBoard, Boolean> {
        var moved = false
        var points = 0
        val transposed = transpose(grid)
        val newTransposed = transposed.map { row ->
            val (merged, pts) = TileMerger.mergeRow(row)
            if (merged != row) moved = true
            points += pts
            merged
        }
        val newGrid = transpose(newTransposed)
        return if (moved) {
            val b = copy(grid = newGrid, score = score + points).spawnTile()
            b to true
        } else this to false
    }

    fun moveDown(): Pair<GameBoard, Boolean> {
        var moved = false
        var points = 0
        val transposed = transpose(grid)
        val newTransposed = transposed.map { row ->
            val reversed = row.reversed()
            val (merged, pts) = TileMerger.mergeRow(reversed)
            val result = merged.reversed()
            if (result != row) moved = true
            points += pts
            result
        }
        val newGrid = transpose(newTransposed)
        return if (moved) {
            val b = copy(grid = newGrid, score = score + points).spawnTile()
            b to true
        } else this to false
    }

    private fun transpose(g: List<List<Int>>): List<List<Int>> {
        val n = g.size
        return List(n) { r -> List(n) { c -> g[c][r] } }
    }
}

enum class Direction { LEFT, RIGHT, UP, DOWN }
