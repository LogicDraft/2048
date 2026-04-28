package com.antigravity.twentyfortyeight.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antigravity.twentyfortyeight.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PauseMenuSheet(
    onDismiss: () -> Unit,
    onResume: () -> Unit,
    onRestart: () -> Unit,
    onSettings: () -> Unit,
    onHome: () -> Unit
) {
    val isDark = LocalIsDarkTheme.current

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = if (isDark) SurfaceDark else SurfaceLight,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "MENU",
                fontFamily = SyneFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp,
                color = if (isDark) OnSurfaceDark else OnSurfaceLight,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 8.dp)
            )

            MenuRow(Icons.Default.PlayArrow, "Resume", PrimaryStart, isDark, onResume)
            MenuRow(Icons.Default.Refresh, "Restart", AccentStart, isDark, onRestart)
            MenuRow(Icons.Default.Settings, "Settings", Color(0xFF22C55E), isDark, onSettings)
            MenuRow(Icons.Default.Home, "Home", PlayerBlue, isDark, onHome)

            Spacer(Modifier.navigationBarsPadding())
        }
    }
}

@Composable
private fun MenuRow(
    icon: ImageVector,
    label: String,
    iconColor: Color,
    isDark: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(
                if (isDark) Color(0xFF2A2A38) else Color(0xFFF4F0EC),
                RoundedCornerShape(18.dp)
            )
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .background(iconColor.copy(alpha = 0.15f), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = label, tint = iconColor, modifier = Modifier.size(22.dp))
        }
        Text(
            label,
            fontFamily = DmSansFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 17.sp,
            color = if (isDark) OnSurfaceDark else OnSurfaceLight
        )
    }
}
