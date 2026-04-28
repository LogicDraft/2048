package com.antigravity.twentyfortyeight.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antigravity.twentyfortyeight.data.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repo: GameRepository
) : ViewModel() {

    val soundEnabled: StateFlow<Boolean> = repo.soundEnabled.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), true
    )
    val musicEnabled: StateFlow<Boolean> = repo.musicEnabled.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), false
    )
    val darkMode: StateFlow<Boolean> = repo.darkMode.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), false
    )
    val hapticsEnabled: StateFlow<Boolean> = repo.hapticsEnabled.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), true
    )
    val defaultGridSize: StateFlow<Int> = repo.defaultGridSize.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), 4
    )
    val currentTheme: StateFlow<String> = repo.currentTheme.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), "classic"
    )

    fun toggleSound() = viewModelScope.launch { repo.setSoundEnabled(!soundEnabled.value) }
    fun toggleMusic() = viewModelScope.launch { repo.setMusicEnabled(!musicEnabled.value) }
    fun toggleDarkMode() = viewModelScope.launch { repo.setDarkMode(!darkMode.value) }
    fun toggleHaptics() = viewModelScope.launch { repo.setHapticsEnabled(!hapticsEnabled.value) }
    fun setGridSize(size: Int) = viewModelScope.launch { repo.setDefaultGridSize(size) }
    fun setTheme(theme: String) = viewModelScope.launch { repo.setCurrentTheme(theme) }
    fun clearAllData() = viewModelScope.launch { repo.clearAllData() }
}
