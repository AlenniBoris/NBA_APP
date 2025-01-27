package com.alenniboris.nba_app.domain.model.statistics.api.nba.main

data class PlayersInGameStatisticsModelDomain(
    val homeTeamPlayersStatistics: List<PlayerStatisticsModelDomain>,
    val visitorsTeamPlayersStatistics: List<PlayerStatisticsModelDomain>
)
