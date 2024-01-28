package dev.syoritohatsuki.fstatsmobile.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Blue,
    onPrimary = Light,
    primaryContainer = Light,
    background = Dark,
    surface = Light,
    onBackground = Color.White,
    onSurface = Color.White,
    surfaceVariant = Light,
    onSecondaryContainer = Color.White
)

@Composable
fun FStatsTheme(content: @Composable () -> Unit) {

    val view = LocalView.current
    if (!view.isInEditMode) SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = LightColorScheme.primaryContainer.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
    }

    MaterialTheme(
        colorScheme = LightColorScheme, typography = Typography, content = content
    )
}