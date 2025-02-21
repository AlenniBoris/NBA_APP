package com.alenniboris.nba_app.presentation.screens.settings.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.presentation.screens.settings.ISettingsScreenEvent
import com.alenniboris.nba_app.presentation.screens.settings.ISettingsScreenUpdateIntent
import com.alenniboris.nba_app.presentation.screens.settings.SettingsScreenState
import com.alenniboris.nba_app.presentation.screens.settings.SettingsScreenVM
import com.alenniboris.nba_app.presentation.uikit.theme.AppLanguage
import com.alenniboris.nba_app.presentation.uikit.theme.AppTheme
import com.alenniboris.nba_app.presentation.uikit.theme.TBShowingScreenPadding
import com.alenniboris.nba_app.presentation.uikit.theme.appColor
import com.alenniboris.nba_app.presentation.uikit.theme.pagerSectionPadding
import com.alenniboris.nba_app.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator
) {

    val settingsScreenVM = koinViewModel<SettingsScreenVM>()
    val state by settingsScreenVM.screenState.collectAsStateWithLifecycle()
    val proceedIntentAction by remember { mutableStateOf(settingsScreenVM::proceedUpdateIntent) }
    val event by remember { mutableStateOf(settingsScreenVM.event) }

    LaunchedEffect(event) {
        launch {
            event.filterIsInstance<ISettingsScreenEvent.NavigateToPreviousPage>().collect() {
                navigator.popBackStack()
            }
        }
    }

    SettingsScreenUi(
        state = state,
        proceedIntentAction = proceedIntentAction
    )
}

@Composable
private fun SettingsScreenUi(
    state: SettingsScreenState,
    proceedIntentAction: (ISettingsScreenUpdateIntent) -> Unit
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
            .padding(TBShowingScreenPadding),
        topBar = {
            AppTopBar(
                modifier = Modifier
                    .fillMaxWidth(),
                headerTextString = stringResource(R.string.settings_screen_header),
                leftBtnPainter = painterResource(R.drawable.icon_navigate_to_previous_page),
                onLeftBtnClicked = {
                    proceedIntentAction(
                        ISettingsScreenUpdateIntent.NavigateToPreviousScreen
                    )
                }
            )
        }
    ) { pv ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(appColor)
                .padding(pv)
                .verticalScroll(rememberScrollState()),
        ) {

            LanguageSettingsPagerSection(
                modifier = Modifier
                    .padding(pagerSectionPadding)
                    .fillMaxWidth(),
                currentLanguage = state.selectedLanguage,
                elements = state.applicationLanguages,
            )

            ThemeSettingsPagerSection(
                modifier = Modifier
                    .padding(pagerSectionPadding)
                    .fillMaxWidth(),
                currentTheme = state.selectedTheme,
                elements = state.applicationThemes,
            )
        }

    }

}


@Composable
@Preview
private fun SettingsPreviewUi() {

    SettingsScreenUi(
        state = SettingsScreenState(
            selectedLanguage = AppLanguage.English,
            selectedTheme = AppTheme.LIGHT
        ),
        proceedIntentAction = {}
    )

}