package com.antigravity.twentyfortyeight.theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.antigravity.twentyfortyeight.R

val googleFontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val SyneFontFamily = FontFamily(
    Font(GoogleFont("Syne"), googleFontProvider, weight = FontWeight.Bold),
    Font(GoogleFont("Syne"), googleFontProvider, weight = FontWeight.ExtraBold),
    Font(GoogleFont("Syne"), googleFontProvider, weight = FontWeight.Normal),
)

val DmSansFontFamily = FontFamily(
    Font(GoogleFont("DM Sans"), googleFontProvider, weight = FontWeight.Normal),
    Font(GoogleFont("DM Sans"), googleFontProvider, weight = FontWeight.Medium),
    Font(GoogleFont("DM Sans"), googleFontProvider, weight = FontWeight.SemiBold),
    Font(GoogleFont("DM Sans"), googleFontProvider, weight = FontWeight.Bold),
)

val JetBrainsMonoFontFamily = FontFamily(
    Font(GoogleFont("JetBrains Mono"), googleFontProvider, weight = FontWeight.Bold),
    Font(GoogleFont("JetBrains Mono"), googleFontProvider, weight = FontWeight.SemiBold),
    Font(GoogleFont("JetBrains Mono"), googleFontProvider, weight = FontWeight.Normal),
)
