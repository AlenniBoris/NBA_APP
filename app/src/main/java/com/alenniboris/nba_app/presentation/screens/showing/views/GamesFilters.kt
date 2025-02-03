package com.alenniboris.nba_app.presentation.screens.showing.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.utils.GameStatus
import com.alenniboris.nba_app.presentation.mappers.toStringMessageForStatus
import com.alenniboris.nba_app.presentation.model.ActionImplementedUiModel
import com.alenniboris.nba_app.presentation.screens.showing.IShowingScreenUpdateIntent
import com.alenniboris.nba_app.presentation.uikit.theme.CustomRowFilterTopPadding
import com.alenniboris.nba_app.presentation.uikit.views.AppDateFilter
import com.alenniboris.nba_app.presentation.uikit.views.AppRowFilter
import java.util.Date

@Composable
@Preview
fun GamesFilters(
    currentDateTime: Date = Date(),
    currentDateTimeText: String = "",

    countries: List<CountryModelDomain> = emptyList(),
    currentSelectedCountry: CountryModelDomain? = null,
    isCountriesLoading: Boolean = false,

    seasons: List<SeasonModelDomain> = emptyList(),
    currentSelectedSeason: SeasonModelDomain? = null,
    isSeasonsLoading: Boolean = false,

    statuses: List<GameStatus> =
        GameStatus.entries.toList(),
    currentSelectedStatus: GameStatus? = null,

    leagues: List<LeagueModelDomain> = emptyList(),
    currentSelectedLeague: LeagueModelDomain? = null,
    isLeaguesLoading: Boolean = false,

    proceedIntentAction: (IShowingScreenUpdateIntent) -> Unit = {}
) {

    Column {
        AppDateFilter(
            currentDateTime = currentDateTime,
            currentDateTimeText = currentDateTimeText,
            onDateSelectedAction = { date ->
                proceedIntentAction(
                    IShowingScreenUpdateIntent.UpdateSelectedDate(
                        date
                    )
                )
            },
        )

        AppRowFilter(
            modifier = Modifier
                .padding(CustomRowFilterTopPadding)
                .fillMaxWidth(),
            headerText = stringResource(R.string.status_filter),
            elements = statuses.map {
                ActionImplementedUiModel(
                    name = stringResource(it.toStringMessageForStatus()),
                    onClick = {
                        proceedIntentAction(
                            IShowingScreenUpdateIntent.UpdateSelectedStatus(it)
                        )
                    }
                )
            },
            currentSelectedElement = ActionImplementedUiModel(
                name = stringResource(
                    currentSelectedStatus?.toStringMessageForStatus() ?: R.string.nan_text
                )
            ),
        )

        AppRowFilter(
            modifier = Modifier
                .padding(CustomRowFilterTopPadding)
                .fillMaxWidth(),
            headerText = stringResource(R.string.season_filter),
            isLoading = isSeasonsLoading,
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
            currentSelectedElement = ActionImplementedUiModel(
                name = currentSelectedSeason?.name ?: stringResource(R.string.nan_text)
            ),
        )

        AppRowFilter(
            modifier = Modifier
                .padding(CustomRowFilterTopPadding)
                .fillMaxWidth(),
            headerText = stringResource(R.string.country_filter),
            isLoading = isCountriesLoading,
            elements = countries.filter { it.name != null }.map {
                ActionImplementedUiModel(
                    name = it.name ?: stringResource(R.string.unknown_country_text),
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
            isLoading = isLeaguesLoading,
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
            currentSelectedElement = ActionImplementedUiModel(
                name = currentSelectedLeague?.name ?: stringResource(R.string.nan_text)
            ),
        )
    }
}