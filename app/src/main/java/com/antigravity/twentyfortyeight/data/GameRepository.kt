package com.antigravity.twentyfortyeight.data

import com.antigravity.twentyfortyeight.engine.GameBoard
import com.antigravity.twentyfortyeight.engine.ScoreCalculator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class GameRepository(
    private val prefs: PreferencesDataStore,
    private val dao: ScoreDao
) {

    // ----  Preferences ----
    val soundEnabled: Flow<Boolean> get() = prefs.soundEnabled
    val musicEnabled: Flow<Boolean> get() = prefs.musicEnabled
    val darkMode: Flow<Boolean> get() = prefs.darkMode
    val hapticsEnabled: Flow<Boolean> get() = prefs.hapticsEnabled
    val defaultGridSize: Flow<Int> get() = prefs.defaultGridSize
    val currentTheme: Flow<String> get() = prefs.currentTheme

    suspend fun setSoundEnabled(v: Boolean) = prefs.setSoundEnabled(v)
    suspend fun setMusicEnabled(v: Boolean) = prefs.setMusicEnabled(v)
    suspend fun setDarkMode(v: Boolean) = prefs.setDarkMode(v)
    suspend fun setHapticsEnabled(v: Boolean) = prefs.setHapticsEnabled(v)
    suspend fun setDefaultGridSize(v: Int) = prefs.setDefaultGridSize(v)
    suspend fun setCurrentTheme(v: String) = prefs.setCurrentTheme(v)

    // ---- Game State Persistence ----
    suspend fun saveGame(board: GameBoard) {
        prefs.saveGameState(
            grid = ScoreCalculator.serializeGrid(board),
            score = board.score,
            gridSize = board.gridSize
        )
    }

    suspend fun loadGame(): GameBoard? {
        val gridStr = prefs.savedGrid.first() ?: return null
        val score = prefs.savedScore.first()
        val size = prefs.savedGridSize.first()
        return ScoreCalculator.deserializeGrid(gridStr, size).copy(score = score)
    }

    suspend fun clearSavedGame() = prefs.clearSavedGame()

    // ---- Scores ----
    fun getTop10Scores(gridSize: Int): Flow<List<ScoreEntity>> = dao.getTop10(gridSize)

    suspend fun getBestScore(gridSize: Int): Int = dao.getBestScore(gridSize) ?: 0

    suspend fun saveScore(gridSize: Int, score: Int, maxTile: Int) {
        dao.insert(ScoreEntity(gridSize = gridSize, score = score, maxTile = maxTile))
    }

    // Stats
    suspend fun getTotalGamesPlayed(): Int = dao.getTotalGamesPlayed()
    suspend fun getHighestTile(): Int = dao.getHighestTileEver() ?: 0
    suspend fun getAverageScore(): Double = dao.getAverageScore() ?: 0.0

    suspend fun clearAllData() {
        dao.clearAll()
        prefs.clearSavedGame()
    }
}
