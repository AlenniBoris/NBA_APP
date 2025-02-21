package com.alenniboris.nba_app.presentation.screens.settings

import com.alenniboris.nba_app.presentation.uikit.theme.AppLanguage
import com.alenniboris.nba_app.presentation.uikit.theme.AppTheme

interface ISettingsScreenUpdateIntent {

    data class UpdateApplicationLanguage(
        val language: AppLanguage
    ) : ISettingsScreenUpdateIntent

    data class UpdateApplicationTheme(
        val theme: AppTheme,
        val isSystemDark: Boolean
    ) : ISettingsScreenUpdateIntent

    data object NavigateToPreviousScreen : ISettingsScreenUpdateIntent

}