package com.antigravity.twentyfortyeight.theme

import androidx.compose.ui.graphics.Color

// Aurora Palette — Light
val BackgroundLight = Color(0xFFF7F5F0)
val SurfaceLight = Color(0xFFFFFFFF)
val OnSurfaceLight = Color(0xFF1A1A2E)

// Aurora Palette — Dark
val BackgroundDark = Color(0xFF121214)
val SurfaceDark = Color(0xFF1E1E24)
val OnSurfaceDark = Color(0xFFF0EFE8)

// Primary & Accent Gradients
val PrimaryStart = Color(0xFF7C3AED) // Purple
val PrimaryEnd = Color(0xFF3B82F6)   // Blue
val AccentStart = Color(0xFFF97316)  // Orange
val AccentEnd = Color(0xFFEC4899)    // Pink

// Grid background
val GridBgLight = Color(0xFFBBADA0)
val GridBgDark = Color(0xFF1A1A22)
val TileEmptyLight = Color(0xFFCDC1B4)
val TileEmptyDark = Color(0xFF2A2A35)

// Score card glassmorphism
val GlassWhite = Color(0x26FFFFFF) // 15% white
val GlassBorder = Color(0x4DFFFFFF) // 30% white

// === Tile Colors — Classic Aurora Theme ===
val Tile2    = Color(0xFFEDE9E0)
val Tile4    = Color(0xFFE0D9C8)
val Tile8    = Color(0xFFF4A261)
val Tile16   = Color(0xFFE76F51)
val Tile32   = Color(0xFFEF4444)
val Tile64   = Color(0xFFDC2626)
val Tile128  = Color(0xFF8B5CF6)
val Tile256  = Color(0xFF7C3AED)
val Tile512  = Color(0xFF6D28D9)
val Tile1024 = Color(0xFF3B82F6)
val Tile2048Start = Color(0xFFFFD700) // Gold shimmer start
val Tile2048End   = Color(0xFFFFA500) // Gold shimmer end
val TileHigh = Color(0xFF1E1B4B)     // 4096+

// Text on tiles
val TileDarkText = Color(0xFF776E65)  // for light tiles (2, 4)
val TileLightText = Color(0xFFF9F6F2) // for colored tiles

data class GameColors(
    val primary: Color,
    val secondary: Color,
    val gridBackground: Color,
    val emptyTile: Color,
    val tile2: Color,
    val tile4: Color,
    val tile8: Color,
    val tile16: Color,
    val tile32: Color,
    val tile64: Color,
    val tile128: Color,
    val tile256: Color,
    val tile512: Color,
    val tile1024: Color,
    val tile2048Start: Color,
    val tile2048End: Color,
    val highTile: Color,
    val tileDarkText: Color,
    val tileLightText: Color
) {
    fun tileBackground(value: Int): Color = when (value) {
        0 -> Color.Transparent
        2 -> tile2
        4 -> tile4
        8 -> tile8
        16 -> tile16
        32 -> tile32
        64 -> tile64
        128 -> tile128
        256 -> tile256
        512 -> tile512
        1024 -> tile1024
        2048 -> tile2048Start
        else -> highTile
    }

    fun tileText(value: Int): Color =
        if (value <= 4) tileDarkText else tileLightText
}

val ClassicGameColors = GameColors(
    primary = PrimaryStart,
    secondary = AccentStart,
    gridBackground = GridBgLight,
    emptyTile = TileEmptyLight,
    tile2 = Tile2,
    tile4 = Tile4,
    tile8 = Tile8,
    tile16 = Tile16,
    tile32 = Tile32,
    tile64 = Tile64,
    tile128 = Tile128,
    tile256 = Tile256,
    tile512 = Tile512,
    tile1024 = Tile1024,
    tile2048Start = Tile2048Start,
    tile2048End = Tile2048End,
    highTile = TileHigh,
    tileDarkText = TileDarkText,
    tileLightText = TileLightText
)

val NeonGameColors = GameColors(
    primary = Color(0xFF22D3EE),
    secondary = Color(0xFFFF2E88),
    gridBackground = Color(0xFF111827),
    emptyTile = Color(0xFF1F2937),
    tile2 = Color(0xFFBFF7FF),
    tile4 = Color(0xFFE4D7FF),
    tile8 = Color(0xFF22D3EE),
    tile16 = Color(0xFF38BDF8),
    tile32 = Color(0xFFA855F7),
    tile64 = Color(0xFFFF2E88),
    tile128 = Color(0xFFFF7A18),
    tile256 = Color(0xFFFACC15),
    tile512 = Color(0xFF34D399),
    tile1024 = Color(0xFF14B8A6),
    tile2048Start = Color(0xFFFF2E88),
    tile2048End = Color(0xFF22D3EE),
    highTile = Color(0xFF0F172A),
    tileDarkText = Color(0xFF111827),
    tileLightText = Color.White
)

val FrostedGlassGameColors = GameColors(
    primary = Color(0xFF7DD3FC),
    secondary = Color(0xFFC4B5FD),
    gridBackground = Color(0x33FFFFFF),
    emptyTile = Color(0x22FFFFFF),
    tile2 = Color(0xBFEFF6FF),
    tile4 = Color(0xBFEDE9FE),
    tile8 = Color(0x99BAE6FD),
    tile16 = Color(0x99A5B4FC),
    tile32 = Color(0x99C4B5FD),
    tile64 = Color(0x99F0ABFC),
    tile128 = Color(0x99F9A8D4),
    tile256 = Color(0x99FDE68A),
    tile512 = Color(0x99A7F3D0),
    tile1024 = Color(0x9986EFAC),
    tile2048Start = Color(0xCCFDE68A),
    tile2048End = Color(0xCC7DD3FC),
    highTile = Color(0xAA312E81),
    tileDarkText = Color(0xFF243044),
    tileLightText = Color.White
)

val RetroGameColors = GameColors(
    primary = Color(0xFFEAB308),
    secondary = Color(0xFFEF4444),
    gridBackground = Color(0xFF3D2C1F),
    emptyTile = Color(0xFF5A4634),
    tile2 = Color(0xFFF8E7B0),
    tile4 = Color(0xFFECCB8B),
    tile8 = Color(0xFFD88A3D),
    tile16 = Color(0xFFC75C2E),
    tile32 = Color(0xFFB83A2D),
    tile64 = Color(0xFF7F1D1D),
    tile128 = Color(0xFF4D7C0F),
    tile256 = Color(0xFF166534),
    tile512 = Color(0xFF155E75),
    tile1024 = Color(0xFF3730A3),
    tile2048Start = Color(0xFFFFD166),
    tile2048End = Color(0xFFEF476F),
    highTile = Color(0xFF1F130B),
    tileDarkText = Color(0xFF3D2C1F),
    tileLightText = Color(0xFFFFF7ED)
)

val MinimalistGameColors = GameColors(
    primary = Color(0xFF111827),
    secondary = Color(0xFF64748B),
    gridBackground = Color(0xFFE5E7EB),
    emptyTile = Color(0xFFF8FAFC),
    tile2 = Color(0xFFFFFFFF),
    tile4 = Color(0xFFF3F4F6),
    tile8 = Color(0xFFE5E7EB),
    tile16 = Color(0xFFD1D5DB),
    tile32 = Color(0xFF9CA3AF),
    tile64 = Color(0xFF6B7280),
    tile128 = Color(0xFF4B5563),
    tile256 = Color(0xFF374151),
    tile512 = Color(0xFF1F2937),
    tile1024 = Color(0xFF111827),
    tile2048Start = Color(0xFF0F172A),
    tile2048End = Color(0xFF475569),
    highTile = Color(0xFF020617),
    tileDarkText = Color(0xFF111827),
    tileLightText = Color.White
)

// Multiplayer
val PlayerYellow = Color(0xFFFFCC02)
val PlayerBlue = Color(0xFF4A90D9)
val PlayerYellowDark = Color(0xFF7A5F00)
val PlayerBlueDark = Color(0xFF1A3F6F)

// Timer colors
val TimerGreen = Color(0xFF22C55E)
val TimerYellow = Color(0xFFEAB308)
val TimerRed = Color(0xFFEF4444)

// Rank colors
val RankGold = Color(0xFFFFD700)
val RankSilver = Color(0xFFC0C0C0)
val RankBronze = Color(0xFFCD7F32)
