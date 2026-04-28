package com.antigravity.twentyfortyeight.engine

import org.junit.Assert.assertEquals
import org.junit.Test

class TileMergerTest {

    @Test
    fun mergeRow_allZeros_returnsSameRowWithNoScore() {
        assertMerge(
            row = listOf(0, 0, 0, 0),
            expectedRow = listOf(0, 0, 0, 0),
            expectedScore = 0
        )
    }

    @Test
    fun mergeRow_slidesTilesThroughZerosWithoutScoring() {
        assertMerge(
            row = listOf(0, 2, 0, 4),
            expectedRow = listOf(2, 4, 0, 0),
            expectedScore = 0
        )
    }

    @Test
    fun mergeRow_mergesSinglePair() {
        assertMerge(
            row = listOf(2, 2, 0, 0),
            expectedRow = listOf(4, 0, 0, 0),
            expectedScore = 4
        )
    }

    @Test
    fun mergeRow_mergesAcrossZeros() {
        assertMerge(
            row = listOf(2, 0, 2, 2),
            expectedRow = listOf(4, 2, 0, 0),
            expectedScore = 4
        )
    }

    @Test
    fun mergeRow_edgeCasePairThenDistinctTiles() {
        assertMerge(
            row = listOf(2, 2, 4, 8),
            expectedRow = listOf(4, 4, 8, 0),
            expectedScore = 4
        )
    }

    @Test
    fun mergeRow_twoIndependentPairsMergeOnceEach() {
        assertMerge(
            row = listOf(2, 2, 4, 4),
            expectedRow = listOf(4, 8, 0, 0),
            expectedScore = 12
        )
    }

    @Test
    fun mergeRow_fourEqualTilesMergeIntoTwoPairs() {
        assertMerge(
            row = listOf(2, 2, 2, 2),
            expectedRow = listOf(4, 4, 0, 0),
            expectedScore = 8
        )
    }

    @Test
    fun mergeRow_threeEqualTilesDoesNotChainMergeInOneMove() {
        assertMerge(
            row = listOf(4, 4, 4, 0),
            expectedRow = listOf(8, 4, 0, 0),
            expectedScore = 8
        )
    }

    @Test
    fun mergeRow_supportsLargerRows() {
        assertMerge(
            row = listOf(0, 2, 2, 4, 4),
            expectedRow = listOf(4, 8, 0, 0, 0),
            expectedScore = 12
        )
    }

    private fun assertMerge(
        row: List<Int>,
        expectedRow: List<Int>,
        expectedScore: Int
    ) {
        val (actualRow, actualScore) = TileMerger.mergeRow(row)

        assertEquals(expectedRow, actualRow)
        assertEquals(expectedScore, actualScore)
    }
}
