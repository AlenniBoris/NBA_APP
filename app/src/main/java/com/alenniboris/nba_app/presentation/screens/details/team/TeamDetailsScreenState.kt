package com.alenniboris.nba_app.presentation.screens.details.team

import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.TeamStatisticsModelDomain

data class TeamDetailsScreenState(
    val team: TeamModelDomain,
    val teamStatistics: TeamStatisticsModelDomain? = null,

    val isTeamDataReloading: Boolean = false,

    val isTeamDataReloadedWithError: Boolean = false,

    val isStatisticsDataLoading: Boolean = false,

    val isTeamPlayersLoading: Boolean = false,
    val teamPlayers: List<PlayerModelDomain> = emptyList(),

    val listOfLeagues: List<LeagueModelDomain> = emptyList(),
    val selectedLeague: LeagueModelDomain? = null,

    val isSeasonsLoading: Boolean = false,
    val listOfSeasons: List<SeasonModelDomain> = emptyList(),
    val selectedSeason: SeasonModelDomain? = null,
)
