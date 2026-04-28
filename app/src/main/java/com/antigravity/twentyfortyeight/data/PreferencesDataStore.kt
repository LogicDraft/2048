package com.antigravity.twentyfortyeight.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "2048_prefs")

class PreferencesDataStore(private val context: Context) {

    companion object {
        val KEY_SOUND = booleanPreferencesKey("sound_enabled")
        val KEY_MUSIC = booleanPreferencesKey("music_enabled")
        val KEY_DARK_MODE = booleanPreferencesKey("dark_mode")
        val KEY_HAPTICS = booleanPreferencesKey("haptics_enabled")
        val KEY_DEFAULT_GRID = intPreferencesKey("default_grid_size")
        val KEY_THEME = stringPreferencesKey("current_theme")
        val KEY_SAVED_GRID = stringPreferencesKey("saved_grid")
        val KEY_SAVED_SCORE = intPreferencesKey("saved_score")
        val KEY_SAVED_GRID_SIZE = intPreferencesKey("saved_grid_size")
    }

    val soundEnabled: Flow<Boolean> = context.dataStore.data.map { it[KEY_SOUND] ?: true }
    val musicEnabled: Flow<Boolean> = context.dataStore.data.map { it[KEY_MUSIC] ?: false }
    val darkMode: Flow<Boolean> = context.dataStore.data.map { it[KEY_DARK_MODE] ?: false }
    val hapticsEnabled: Flow<Boolean> = context.dataStore.data.map { it[KEY_HAPTICS] ?: true }
    val defaultGridSize: Flow<Int> = context.dataStore.data.map { it[KEY_DEFAULT_GRID] ?: 4 }
    val currentTheme: Flow<String> = context.dataStore.data.map { it[KEY_THEME] ?: "classic" }
    val savedGrid: Flow<String?> = context.dataStore.data.map { it[KEY_SAVED_GRID] }
    val savedScore: Flow<Int> = context.dataStore.data.map { it[KEY_SAVED_SCORE] ?: 0 }
    val savedGridSize: Flow<Int> = context.dataStore.data.map { it[KEY_SAVED_GRID_SIZE] ?: 4 }

    suspend fun setSoundEnabled(v: Boolean) = context.dataStore.edit { it[KEY_SOUND] = v }
    suspend fun setMusicEnabled(v: Boolean) = context.dataStore.edit { it[KEY_MUSIC] = v }
    suspend fun setDarkMode(v: Boolean) = context.dataStore.edit { it[KEY_DARK_MODE] = v }
    suspend fun setHapticsEnabled(v: Boolean) = context.dataStore.edit { it[KEY_HAPTICS] = v }
    suspend fun setDefaultGridSize(v: Int) = context.dataStore.edit { it[KEY_DEFAULT_GRID] = v }
    suspend fun setCurrentTheme(v: String) = context.dataStore.edit { it[KEY_THEME] = v }

    suspend fun saveGameState(grid: String, score: Int, gridSize: Int) {
        context.dataStore.edit {
            it[KEY_SAVED_GRID] = grid
            it[KEY_SAVED_SCORE] = score
            it[KEY_SAVED_GRID_SIZE] = gridSize
        }
    }

    suspend fun clearSavedGame() {
        context.dataStore.edit {
            it.remove(KEY_SAVED_GRID)
            it.remove(KEY_SAVED_SCORE)
        }
    }
}
