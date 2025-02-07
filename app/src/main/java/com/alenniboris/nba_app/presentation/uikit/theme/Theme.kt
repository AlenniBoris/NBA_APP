package com.alenniboris.nba_app.presentation.uikit.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF673AB7),
    background = Color(0xFF3F51B5),
    onBackground = Color(0xFF2196F3),
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF673AB7),
    background = Color(0xFF3F51B5),
    onBackground = Color(0xFF2196F3)
)

@Composable
fun NbaAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {

    remember(darkTheme) {
        ThemeChooser.setThemeMode(
            if (darkTheme) ThemeChooser.ThemeMode.DARK else ThemeChooser.ThemeMode.LIGHT
        )
        null
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }


    val view = LocalView.current
    if (!view.isInEditMode) {

        val themeMode = ThemeChooser.themeMode.collectAsStateWithLifecycle()

        SideEffect {
            val window = (view.context as Activity).window

            val appBarColor = when (themeMode.value) {
                ThemeChooser.ThemeMode.LIGHT -> Color(0xfff77e56)
                ThemeChooser.ThemeMode.DARK -> Color(0xff050300)
            }

            window.statusBarColor = appBarColor.toArgb()
            window.navigationBarColor = appBarColor.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                themeMode.value == ThemeChooser.ThemeMode.LIGHT
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                themeMode.value == ThemeChooser.ThemeMode.LIGHT
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}