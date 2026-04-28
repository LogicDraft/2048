package com.antigravity.twentyfortyeight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antigravity.twentyfortyeight.navigation.AppNavGraph
import com.antigravity.twentyfortyeight.settings.SettingsViewModel
import com.antigravity.twentyfortyeight.theme.TwentyFortyEightTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsVm: SettingsViewModel = hiltViewModel()
            val darkMode by settingsVm.darkMode.collectAsStateWithLifecycle()
            val currentTheme by settingsVm.currentTheme.collectAsStateWithLifecycle()

            TwentyFortyEightTheme(
                darkTheme = darkMode,
                currentTheme = currentTheme
            ) {
                AppNavGraph()
            }
        }
    }
}
