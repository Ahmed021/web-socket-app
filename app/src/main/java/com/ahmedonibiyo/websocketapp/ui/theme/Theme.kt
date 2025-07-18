package com.ahmedonibiyo.websocketapp.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val WebSocketLightColorScheme = lightColorScheme(
    primary = WebSocketPrimaryLight,
    onPrimary = Color.White,
    secondary = WebSocketSecondaryLight,
    onSecondary = Color.White,
    tertiary = WebSocketTertiaryLight,
    onTertiary = Color.White,
    background = WebSocketBackgroundLight,
    onBackground = Color.Black,
    surface = WebSocketSurfaceLight,
    onSurface = Color.Black,
    outline = WebSocketOutlineLight
)

@Composable
fun WebSocketAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = WebSocketLightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window

            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}