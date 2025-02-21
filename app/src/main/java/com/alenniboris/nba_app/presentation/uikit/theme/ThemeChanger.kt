package com.alenniboris.nba_app.presentation.uikit.theme

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


enum class AppTheme {
    LIGHT,
    DARK,
    SYSTEM
}

sealed class ThemeMode(
    val isThemeDark: Boolean,
    val theme: AppTheme
) {
    class Light() :
        ThemeMode(isThemeDark = false, theme = AppTheme.LIGHT)

    class Dark() :
        ThemeMode(isThemeDark = true, theme = AppTheme.DARK)

    class System(isThemeDark: Boolean) :
        ThemeMode(isThemeDark = isThemeDark, theme = AppTheme.SYSTEM)
}

private val _currentThemeMode = MutableStateFlow<ThemeMode>(ThemeMode.Light())
val currentThemeMode = _currentThemeMode.asStateFlow()

private val PREFERENCIES_NAME = "LAST_THEME"
private val THEME_STRING_NAME = "THEME"

private fun AppTheme.update(isThemeDark: Boolean) {
    _currentThemeMode.update {
        when (this) {
            AppTheme.LIGHT -> ThemeMode.Light()
            AppTheme.DARK -> ThemeMode.Dark()
            AppTheme.SYSTEM -> ThemeMode.System(isThemeDark = isThemeDark)
        }
    }
}

fun Context.setTheme(
    theme: AppTheme,
    isThemeDark: Boolean
) {
    applicationContext.getSharedPreferences(PREFERENCIES_NAME, Context.MODE_PRIVATE)
        .edit()
        .putString(THEME_STRING_NAME, theme.name)
        .apply()

    theme.update(isThemeDark = isThemeDark)
}


fun Context.getLastThemeAndApply(
    isSystemDarkMode: Boolean
): AppTheme {
    val theme = applicationContext.getSharedPreferences(PREFERENCIES_NAME, Context.MODE_PRIVATE)
        .getString(THEME_STRING_NAME, null)?.let { lastTheme ->
            AppTheme.entries.find { it.name == lastTheme }
        } ?: AppTheme.SYSTEM
    theme.update(isThemeDark = isSystemDarkMode)
    return _currentThemeMode.value.theme
}