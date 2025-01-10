package com.alenniboris.nba_app.presentation.mappers

import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain

fun NbaApiExceptionModelDomain.toUiMessageString(): Int = when (this) {

    NbaApiExceptionModelDomain.LeagueAndSeasonFieldIsNeededToBeFilledBoth ->
        R.string.league_and_season_exception

    NbaApiExceptionModelDomain.SomeUnknownExceptionOccurred -> R.string.other_exception

    NbaApiExceptionModelDomain.CountryIsNotSelectedForThisQuery -> R.string.request_country_missing

    NbaApiExceptionModelDomain.SearchQuerySizeToSmall -> R.string.request_query_size_to_small

    NbaApiExceptionModelDomain.LeagueOrTeamIsNotChosen -> R.string.request_team_season_smth_is_empty

}