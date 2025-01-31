package com.alenniboris.nba_app.presentation.screens.details.team

import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain

sealed interface ITeamDetailsScreenUpdateIntent {

    data class UpdateSelectedLeague(val league: LeagueModelDomain) : ITeamDetailsScreenUpdateIntent

    data class UpdateSelectedSeason(val season: SeasonModelDomain) : ITeamDetailsScreenUpdateIntent

    data object ProceedIsFollowedAction : ITeamDetailsScreenUpdateIntent

    data object NavigateToPreviousScreen : ITeamDetailsScreenUpdateIntent

}