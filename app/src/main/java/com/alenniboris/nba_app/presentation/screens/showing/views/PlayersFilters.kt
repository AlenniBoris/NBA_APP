package com.alenniboris.nba_app.presentation.screens.showing.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.presentation.model.ActionImplementedUiModel
import com.alenniboris.nba_app.presentation.model.filter.LeagueFilterUiModel
import com.alenniboris.nba_app.presentation.model.filter.SeasonFilterUiModel
import com.alenniboris.nba_app.presentation.screens.showing.ShowingScreenUpdateIntent
import com.alenniboris.nba_app.presentation.uikit.theme.CustomRowFilterTopPadding
import com.alenniboris.nba_app.presentation.uikit.theme.ESCustomTextFieldPadding
import com.alenniboris.nba_app.presentation.uikit.theme.ESCustomTextFieldShape
import com.alenniboris.nba_app.presentation.uikit.theme.EnterValueTextFieldTopPaddingInPlayersFilter
import com.alenniboris.nba_app.presentation.uikit.theme.enterTextFieldColor
import com.alenniboris.nba_app.presentation.uikit.views.AppDividerWithHeader
import com.alenniboris.nba_app.presentation.uikit.views.AppRowFilter
import com.alenniboris.nba_app.presentation.uikit.views.AppTextField

@Composable
@Preview
fun PlayersFilters(
    seasons: List<SeasonFilterUiModel> = emptyList(),
    currentSelectedSeason: SeasonFilterUiModel = SeasonFilterUiModel(),
    leagues: List<LeagueFilterUiModel> = emptyList(),
    currentSelectedLeague: LeagueFilterUiModel = LeagueFilterUiModel(),
    enteredQueryValue: String = "",
    proceedIntentAction: (ShowingScreenUpdateIntent) -> Unit = {}
) {

    Column {

        AppDividerWithHeader(
            headerText = stringResource(R.string.search_text)
        )

        AppTextField(
            modifier = Modifier
                .padding(EnterValueTextFieldTopPaddingInPlayersFilter)
                .fillMaxWidth()
                .background(
                    enterTextFieldColor,
                    shape = ESCustomTextFieldShape
                )
                .padding(ESCustomTextFieldPadding),
            value = enteredQueryValue,
            onValueChanged = { newValue ->
                proceedIntentAction(
                    ShowingScreenUpdateIntent.UpdateEnteredSearch(
                        newValue
                    )
                )
            },
            placeholder = stringResource(R.string.query_filter_placeholder)
        )

        AppRowFilter(
            modifier = Modifier
                .padding(CustomRowFilterTopPadding)
                .fillMaxWidth(),
            headerText = stringResource(R.string.season_filter),
            elements = seasons.map {
                ActionImplementedUiModel(
                    name = it.name ?: stringResource(R.string.nan_text),
                    onClick = {
                        proceedIntentAction(
                            ShowingScreenUpdateIntent.UpdateSelectedSeason(it)
                        )
                    }
                )
            },
            currentSelectedElement = ActionImplementedUiModel(
                name = currentSelectedSeason.name ?: stringResource(R.string.nan_text)
            )
        )

        AppRowFilter(
            modifier = Modifier
                .padding(CustomRowFilterTopPadding)
                .fillMaxWidth(),
            headerText = stringResource(R.string.league_filter),
            elements = leagues.map {
                ActionImplementedUiModel(
                    name = it.name ?: stringResource(R.string.nan_text),
                    onClick = {
                        proceedIntentAction(
                            ShowingScreenUpdateIntent.UpdateSelectedLeague(it)
                        )
                    }
                )
            },
            currentSelectedElement = ActionImplementedUiModel(
                name = currentSelectedLeague.name ?: stringResource(R.string.nan_text)
            ),
        )

    }

}