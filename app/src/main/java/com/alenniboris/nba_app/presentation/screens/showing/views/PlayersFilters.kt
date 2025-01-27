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
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.presentation.model.ActionImplementedUiModel
import com.alenniboris.nba_app.presentation.screens.showing.IShowingScreenUpdateIntent
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
    seasons: List<SeasonModelDomain> = emptyList(),
    currentSelectedSeason: SeasonModelDomain? = null,
    isSeasonsLoading: Boolean = false,

    leagues: List<LeagueModelDomain> = emptyList(),
    currentSelectedLeague: LeagueModelDomain? = null,
    isLeaguesLoading: Boolean = false,

    enteredQueryValue: String = "",

    countries: List<CountryModelDomain> = emptyList(),
    currentSelectedCountry: CountryModelDomain? = null,
    isCountriesLoading: Boolean = false,

    teams: List<TeamModelDomain> = emptyList(),
    currentSelectedTeam: TeamModelDomain? = null,
    isTeamsLoading: Boolean = false,

    proceedIntentAction: (IShowingScreenUpdateIntent) -> Unit = {}
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
                    IShowingScreenUpdateIntent.UpdateEnteredSearch(
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
                    name = it.name,
                    onClick = {
                        proceedIntentAction(
                            IShowingScreenUpdateIntent.UpdateSelectedSeason(it)
                        )
                    }
                )
            },
            isLoading = isSeasonsLoading,
            currentSelectedElement = ActionImplementedUiModel(
                name = currentSelectedSeason?.name ?: stringResource(R.string.nan_text)
            )
        )

        AppRowFilter(
            modifier = Modifier
                .padding(CustomRowFilterTopPadding)
                .fillMaxWidth(),
            headerText = stringResource(R.string.country_filter),
            isLoading = isCountriesLoading,
            elements = countries.map {
                ActionImplementedUiModel(
                    name = it.name,
                    onClick = {
                        proceedIntentAction(
                            IShowingScreenUpdateIntent.UpdateSelectedCountry(it)
                        )
                    }
                )
            },
            currentSelectedElement = ActionImplementedUiModel(
                name = currentSelectedCountry?.name ?: stringResource(R.string.nan_text)
            ),
        )

        AppRowFilter(
            modifier = Modifier
                .padding(CustomRowFilterTopPadding)
                .fillMaxWidth(),
            headerText = stringResource(R.string.league_filter),
            emptyText = stringResource(R.string.empty_leagues_text),
            elements = leagues.map {
                ActionImplementedUiModel(
                    name = it.name,
                    onClick = {
                        proceedIntentAction(
                            IShowingScreenUpdateIntent.UpdateSelectedLeague(it)
                        )
                    }
                )
            },
            isLoading = isLeaguesLoading,
            currentSelectedElement = ActionImplementedUiModel(
                name = currentSelectedLeague?.name ?: stringResource(R.string.nan_text)
            ),
        )

        AppRowFilter(
            modifier = Modifier
                .padding(CustomRowFilterTopPadding)
                .fillMaxWidth(),
            headerText = stringResource(R.string.team_filter),
            emptyText = stringResource(R.string.empty_teams_text),
            elements = teams.map {
                ActionImplementedUiModel(
                    name = it.name,
                    onClick = {
                        proceedIntentAction(
                            IShowingScreenUpdateIntent.UpdateSelectedTeam(it)
                        )
                    }
                )
            },
            isLoading = isTeamsLoading,
            currentSelectedElement = ActionImplementedUiModel(
                name = currentSelectedTeam?.name ?: stringResource(R.string.nan_text)
            ),
        )

    }

}