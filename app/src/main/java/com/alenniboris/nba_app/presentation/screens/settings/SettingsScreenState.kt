package com.alenniboris.nba_app.presentation.screens.settings

import com.alenniboris.nba_app.presentation.uikit.theme.AppLanguage
import com.alenniboris.nba_app.presentation.uikit.theme.AppTheme

data class SettingsScreenState(
    val selectedLanguage: AppLanguage = AppLanguage.English,
    val applicationLanguages: List<AppLanguage> = AppLanguage.entries.toList(),
    val selectedTheme: AppTheme = AppTheme.SYSTEM,
    val applicationThemes: List<AppTheme> = AppTheme.entries.toList()
)
