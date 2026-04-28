package com.antigravity.twentyfortyeight.engine

/**
 * Core merge algorithm for a single row (left-direction).
 * All directional moves transform to left-merge by transposing/reversing.
 */
object TileMerger {

    /**
     * Merge a single row leftward.
     * @return Pair of merged row + points scored
     */
    fun mergeRow(row: List<Int>): Pair<List<Int>, Int> {
        val size = row.size
        // 1. Compact: remove zeros and slide left
        val compacted = row.filter { it != 0 }.toMutableList()

        var points = 0
        // 2. Merge adjacent equal tiles
        var i = 0
        while (i < compacted.size - 1) {
            if (compacted[i] == compacted[i + 1]) {
                val merged = compacted[i] * 2
                compacted[i] = merged
                compacted.removeAt(i + 1)
                points += merged
            }
            i++
        }

        // 3. Pad with zeros to original size
        while (compacted.size < size) {
            compacted.add(0)
        }

        return compacted to points
    }
}
