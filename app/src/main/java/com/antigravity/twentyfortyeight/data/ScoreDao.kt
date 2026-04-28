package com.antigravity.twentyfortyeight.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {
    @Insert
    suspend fun insert(score: ScoreEntity)

    @Query("SELECT * FROM scores WHERE gridSize = :gridSize ORDER BY score DESC LIMIT 10")
    fun getTop10(gridSize: Int): Flow<List<ScoreEntity>>

    @Query("SELECT MAX(score) FROM scores WHERE gridSize = :gridSize")
    suspend fun getBestScore(gridSize: Int): Int?

    @Query("SELECT COUNT(*) FROM scores")
    suspend fun getTotalGamesPlayed(): Int

    @Query("SELECT MAX(maxTile) FROM scores")
    suspend fun getHighestTileEver(): Int?

    @Query("SELECT AVG(score) FROM scores")
    suspend fun getAverageScore(): Double?

    @Query("DELETE FROM scores")
    suspend fun clearAll()
}
