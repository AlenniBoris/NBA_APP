package com.alenniboris.nba_app.domain.model.statistics.api.nba.main

import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.extra.TeamEarnedPointsModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.extra.TeamPlayedGamesModelDomain

data class TeamStatisticsModelDomain(
    val league: LeagueModelDomain?,
    val country: CountryModelDomain?,
    val team: TeamModelDomain?,
    val games: TeamPlayedGamesModelDomain?,
    val points: TeamEarnedPointsModelDomain?
) : IStatisticsModel
