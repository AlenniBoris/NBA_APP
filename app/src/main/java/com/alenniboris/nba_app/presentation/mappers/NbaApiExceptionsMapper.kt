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

    NbaApiExceptionModelDomain.DeletingElementError -> R.string.request_team_season_smth_is_empty

    NbaApiExceptionModelDomain.SavingElementError -> R.string.request_team_season_smth_is_empty

    NbaApiExceptionModelDomain.ErrorGettingFollowedGames -> R.string.getting_followed_games_exception

    NbaApiExceptionModelDomain.ErrorGettingFollowedPlayers -> R.string.getting_followed_players_exception

    NbaApiExceptionModelDomain.ErrorGettingFollowedTeams -> R.string.getting_followed_teams_exception

    NbaApiExceptionModelDomain.NoInternetException -> R.string.web_exception

    NbaApiExceptionModelDomain.TryAnotherSeason -> R.string.another_season_statistics_exception
}