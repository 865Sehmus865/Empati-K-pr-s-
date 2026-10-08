package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = MintGreenDarkPrimary,
    onPrimary = Color(0xFF03381B),
    primaryContainer = MintGreenDarkContainer,
    onPrimaryContainer = Color(0xFFD6F0E0),
    secondary = SageGreenDarkSecondary,
    onSecondary = Color(0xFF063B1F),
    secondaryContainer = Color(0xFF1B3D2B),
    onSecondaryContainer = Color(0xFFD3EEDF),
    tertiary = TealCalmDark,
    background = ForestGreenDarkBackground,
    onBackground = Color(0xFFE5F2EA),
    surface = ForestGreenDarkSurface,
    onSurface = Color(0xFFE5F2EA),
    surfaceVariant = ForestGreenDarkSurfaceVariant,
    onSurfaceVariant = Color(0xFFC0D5C8),
    outline = Color(0xFF4C6656)
)

private val LightColorScheme = lightColorScheme(
    primary = ForestGreenPrimary,
    onPrimary = Color.White,
    primaryContainer = MintGreenPrimaryContainer,
    onPrimaryContainer = OnMintGreenContainer,
    secondary = SageGreenSecondary,
    onSecondary = Color.White,
    secondaryContainer = SageGreenContainer,
    onSecondaryContainer = Color(0xFF0D3B20),
    tertiary = TealCalm,
    background = PeacefulBackgroundLight,
    onBackground = Color(0xFF14241B),
    surface = PeacefulSurfaceLight,
    onSurface = Color(0xFF14241B),
    surfaceVariant = PeacefulSurfaceVariantLight,
    onSurfaceVariant = Color(0xFF3B5244),
    outline = BorderLight
)

@Composable
fun EmpatiKoprusuTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
