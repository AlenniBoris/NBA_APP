package com.alenniboris.nba_app.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.nba_app.domain.utils.SingleFlowEvent
import com.alenniboris.nba_app.presentation.uikit.theme.currentLanguageMode
import com.alenniboris.nba_app.presentation.uikit.theme.currentThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsScreenVM : ViewModel() {

    private val _screenState = MutableStateFlow(SettingsScreenState())
    val screenState = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<ISettingsScreenEvent>(viewModelScope)
    val event = _event.flow

    init {
        _screenState.update {
            it.copy(
                selectedLanguage = currentLanguageMode.value.language,
                selectedTheme = currentThemeMode.value.theme
            )
        }
    }

    fun proceedUpdateIntent(intent: ISettingsScreenUpdateIntent) {
        when (intent) {
            is ISettingsScreenUpdateIntent.NavigateToPreviousScreen ->
                navigateToPreviousScreen()

        }
    }

    private fun navigateToPreviousScreen() {
        _event.emit(ISettingsScreenEvent.NavigateToPreviousPage)
    }

}