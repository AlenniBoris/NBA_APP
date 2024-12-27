package com.alenniboris.nba_app.presentation.screens.showing.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.domain.utils.EnumValues.GameStatus
import com.alenniboris.nba_app.presentation.mappers.toStringMessageForStatus
import com.alenniboris.nba_app.presentation.model.ActionImplementedUiModel
import com.alenniboris.nba_app.presentation.model.filter.CountryFilterUiModel
import com.alenniboris.nba_app.presentation.model.filter.LeagueFilterUiModel
import com.alenniboris.nba_app.presentation.model.filter.SeasonFilterUiModel
import com.alenniboris.nba_app.presentation.screens.showing.ShowingScreenUpdateIntent
import com.alenniboris.nba_app.presentation.uikit.theme.CustomRowFilterTopPadding
import com.alenniboris.nba_app.presentation.uikit.views.AppDateFilter
import com.alenniboris.nba_app.presentation.uikit.views.AppRowFilter
import java.util.Date

@Composable
@Preview
fun GamesFilters(
    currentDateTime: Date = Date(),
    currentDateTimeText: String = "",
    countries: List<CountryFilterUiModel> = emptyList(),
    currentSelectedCountry: CountryFilterUiModel = CountryFilterUiModel(),
    seasons: List<SeasonFilterUiModel> = emptyList(),
    currentSelectedSeason: SeasonFilterUiModel = SeasonFilterUiModel(),
    statuses: List<GameStatus> =
        GameStatus.entries.toList(),
    currentSelectedStatus: GameStatus? = null,
    leagues: List<LeagueFilterUiModel> = emptyList(),
    currentSelectedLeague: LeagueFilterUiModel = LeagueFilterUiModel(),
    proceedIntentAction: (ShowingScreenUpdateIntent) -> Unit = {}
) {

    Column {
        AppDateFilter(
            currentDateTime = currentDateTime,
            currentDateTimeText = currentDateTimeText,
            onDateSelectedAction = { date ->
                proceedIntentAction(
                    ShowingScreenUpdateIntent.UpdateSelectedDate(
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
                            ShowingScreenUpdateIntent.UpdateSelectedStatus(it)
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
            headerText = stringResource(R.string.country_filter),
            elements = countries.map {
                ActionImplementedUiModel(
                    name = it.name ?: stringResource(R.string.nan_text),
                    onClick = {
                        proceedIntentAction(
                            ShowingScreenUpdateIntent.UpdateSelectedCountry(it)
                        )
                    }
                )
            },
            currentSelectedElement = ActionImplementedUiModel(
                name = currentSelectedCountry.name ?: stringResource(R.string.nan_text)
            ),
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