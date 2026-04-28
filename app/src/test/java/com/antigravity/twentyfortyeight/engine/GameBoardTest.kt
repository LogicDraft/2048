package com.antigravity.twentyfortyeight.engine

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class GameBoardTest {

    @Test
    fun isWon_returnsTrueWhenBoardContains2048OrHigher() {
        assertTrue(
            board(
                listOf(2, 4, 8, 16),
                listOf(32, 64, 128, 256),
                listOf(512, 1024, 2048, 0),
                listOf(0, 0, 0, 0)
            ).isWon()
        )

        assertTrue(
            board(
                listOf(4096, 4, 8, 16),
                listOf(32, 64, 128, 256),
                listOf(512, 1024, 2, 0),
                listOf(0, 0, 0, 0)
            ).isWon()
        )
    }

    @Test
    fun isWon_returnsFalseWhenBoardHasNoWinningTile() {
        assertFalse(
            board(
                listOf(2, 4, 8, 16),
                listOf(32, 64, 128, 256),
                listOf(512, 1024, 2, 0),
                listOf(0, 0, 0, 0)
            ).isWon()
        )
    }

    @Test
    fun isGameOver_returnsTrueForFullBoardWithNoPossibleMerges() {
        assertTrue(
            board(
                listOf(2, 4, 2, 4),
                listOf(4, 2, 4, 2),
                listOf(2, 4, 2, 4),
                listOf(4, 2, 4, 2)
            ).isGameOver()
        )
    }

    @Test
    fun isGameOver_returnsFalseWhenAnyEmptyCellExists() {
        assertFalse(
            board(
                listOf(2, 4, 2, 4),
                listOf(4, 0, 4, 2),
                listOf(2, 4, 2, 4),
                listOf(4, 2, 4, 2)
            ).isGameOver()
        )
    }

    @Test
    fun isGameOver_returnsFalseWhenHorizontalMergeExists() {
        assertFalse(
            board(
                listOf(2, 2, 4, 8),
                listOf(16, 32, 64, 128),
                listOf(256, 512, 1024, 2),
                listOf(4, 8, 16, 32)
            ).isGameOver()
        )
    }

    @Test
    fun isGameOver_returnsFalseWhenVerticalMergeExists() {
        assertFalse(
            board(
                listOf(2, 4, 8, 16),
                listOf(2, 32, 64, 128),
                listOf(256, 512, 1024, 2),
                listOf(4, 8, 16, 32)
            ).isGameOver()
        )
    }

    @Test
    fun moveLeft_mergesRowsAndAccumulatesScore() {
        val initial = board(
            listOf(2, 0, 2, 4),
            listOf(2, 2, 2, 2),
            listOf(0, 4, 4, 8),
            listOf(16, 0, 0, 16),
            score = 10
        )

        val (actual, moved) = initial.moveLeft()

        assertTrue(moved)
        assertEquals(62, actual.score)
        assertBoardAfterSpawn(
            expectedBeforeSpawn = listOf(
                listOf(4, 4, 0, 0),
                listOf(4, 4, 0, 0),
                listOf(8, 8, 0, 0),
                listOf(32, 0, 0, 0)
            ),
            actual = actual
        )
    }

    @Test
    fun moveRight_mergesRowsAndAccumulatesScore() {
        val initial = board(
            listOf(2, 0, 2, 4),
            listOf(2, 2, 2, 2),
            listOf(0, 4, 4, 8),
            listOf(16, 0, 0, 16),
            score = 10
        )

        val (actual, moved) = initial.moveRight()

        assertTrue(moved)
        assertEquals(62, actual.score)
        assertBoardAfterSpawn(
            expectedBeforeSpawn = listOf(
                listOf(0, 0, 4, 4),
                listOf(0, 0, 4, 4),
                listOf(0, 0, 8, 8),
                listOf(0, 0, 0, 32)
            ),
            actual = actual
        )
    }

    @Test
    fun moveUp_mergesColumnsAndAccumulatesScore() {
        val initial = board(
            listOf(2, 0, 2, 4),
            listOf(2, 2, 0, 4),
            listOf(4, 2, 2, 0),
            listOf(4, 0, 2, 4),
            score = 7
        )

        val (actual, moved) = initial.moveUp()

        assertTrue(moved)
        assertEquals(35, actual.score)
        assertBoardAfterSpawn(
            expectedBeforeSpawn = listOf(
                listOf(4, 4, 4, 8),
                listOf(8, 0, 2, 4),
                listOf(0, 0, 0, 0),
                listOf(0, 0, 0, 0)
            ),
            actual = actual
        )
    }

    @Test
    fun moveDown_mergesColumnsAndAccumulatesScore() {
        val initial = board(
            listOf(2, 0, 2, 4),
            listOf(2, 2, 0, 4),
            listOf(4, 2, 2, 0),
            listOf(4, 0, 2, 4),
            score = 7
        )

        val (actual, moved) = initial.moveDown()

        assertTrue(moved)
        assertEquals(35, actual.score)
        assertBoardAfterSpawn(
            expectedBeforeSpawn = listOf(
                listOf(0, 0, 0, 0),
                listOf(0, 0, 0, 0),
                listOf(4, 0, 2, 4),
                listOf(8, 4, 4, 8)
            ),
            actual = actual
        )
    }

    @Test
    fun moveWithoutGridChangeReturnsFalseAndDoesNotSpawn() {
        val initial = board(
            listOf(2, 4, 8, 16),
            listOf(32, 64, 128, 256),
            listOf(512, 1024, 2, 4),
            listOf(8, 16, 32, 64),
            score = 123
        )

        val (actual, moved) = initial.moveLeft()

        assertFalse(moved)
        assertEquals(initial, actual)
    }

    private fun board(
        vararg rows: List<Int>,
        score: Int = 0
    ): GameBoard =
        GameBoard(
            grid = rows.toList(),
            score = score,
            gridSize = rows.size
        )

    private fun assertBoardAfterSpawn(
        expectedBeforeSpawn: List<List<Int>>,
        actual: GameBoard
    ) {
        assertEquals(expectedBeforeSpawn.size, actual.gridSize)
        assertEquals(expectedBeforeSpawn.size, actual.grid.size)

        var spawnedTiles = 0
        expectedBeforeSpawn.forEachIndexed { rowIndex, expectedRow ->
            assertEquals(expectedBeforeSpawn.size, actual.grid[rowIndex].size)
            expectedRow.forEachIndexed { colIndex, expectedValue ->
                val actualValue = actual.grid[rowIndex][colIndex]
                if (expectedValue == 0 && actualValue in setOf(2, 4)) {
                    spawnedTiles += 1
                } else {
                    assertEquals(
                        "Mismatch at row $rowIndex, col $colIndex",
                        expectedValue,
                        actualValue
                    )
                }
            }
        }

        assertEquals("A valid move should spawn exactly one new tile", 1, spawnedTiles)
    }
}
