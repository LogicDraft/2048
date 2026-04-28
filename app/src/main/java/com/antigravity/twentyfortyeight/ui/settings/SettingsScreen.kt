package com.antigravity.twentyfortyeight.ui.settings

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antigravity.twentyfortyeight.theme.*
import com.antigravity.twentyfortyeight.settings.SettingsViewModel

private val gridSizes = listOf(3, 4, 5, 6, 8)
private val themes = listOf(
    "classic" to "🟫 Classic",
    "neon" to "⚡ Neon",
    "glass" to "💎 Glass",
    "dark" to "🖤 Dark",
    "retro" to "🕹️ Retro"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    vm: SettingsViewModel = hiltViewModel()
) {
    val isDark by vm.darkMode.collectAsStateWithLifecycle()
    val hapticsEnabled by vm.hapticsEnabled.collectAsStateWithLifecycle()
    var showClearConfirm by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDark) BackgroundDark else BackgroundLight)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.statusBarsPadding())

        // Top bar
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, null, tint = if (isDark) OnSurfaceDark else OnSurfaceLight)
            }
            Text(
                "SETTINGS",
                fontFamily = SyneFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp,
                color = if (isDark) OnSurfaceDark else OnSurfaceLight
            )
        }

        Column(modifier = Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {

            // ---- Toggles ----
            SectionLabel("Preferences", isDark)

            ToggleRow("Dark Mode", isDark, isDark) { vm.toggleDarkMode() }
            ToggleRow("Haptic Feedback", hapticsEnabled, isDark) { vm.toggleHaptics() }

            Spacer(Modifier.height(16.dp))

            // ---- Clear data button ----
            OutlinedButton(
                onClick = { showClearConfirm = true },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFEF4444)),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFEF4444))
            ) {
                Text("CLEAR ALL DATA", fontFamily = DmSansFontFamily, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }

            Spacer(Modifier.height(40.dp))
        }
    }

    // Confirm clear dialog
    if (showClearConfirm) {
        AlertDialog(
            onDismissRequest = { showClearConfirm = false },
            title = { Text("Clear All Data?", fontFamily = SyneFontFamily, fontWeight = FontWeight.Bold) },
            text = { Text("This will delete all scores and saved games.", fontFamily = DmSansFontFamily) },
            confirmButton = {
                TextButton(onClick = { vm.clearAllData(); showClearConfirm = false }) {
                    Text("CLEAR", color = Color(0xFFEF4444), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearConfirm = false }) { Text("CANCEL") }
            }
        )
    }
}

@Composable
private fun SectionLabel(text: String, isDark: Boolean) {
    Text(
        text,
        fontFamily = DmSansFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp,
        color = if (isDark) Color(0xFF6668AA) else Color(0xFF9997A1),
        modifier = Modifier.padding(start = 4.dp)
    )
}

@Composable
private fun ToggleRow(label: String, checked: Boolean, isDark: Boolean, onToggle: () -> Unit) {
    val trackColor by animateColorAsState(
        if (checked) PrimaryStart else if (isDark) Color(0xFF333344) else Color(0xFFDDD9D4),
        animationSpec = tween(300), label = "toggle"
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isDark) SurfaceDark else SurfaceLight, RoundedCornerShape(16.dp))
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            fontFamily = DmSansFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            color = if (isDark) OnSurfaceDark else OnSurfaceLight
        )
        Switch(
            checked = checked,
            onCheckedChange = { onToggle() },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = trackColor,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = trackColor
            )
        )
    }
}
