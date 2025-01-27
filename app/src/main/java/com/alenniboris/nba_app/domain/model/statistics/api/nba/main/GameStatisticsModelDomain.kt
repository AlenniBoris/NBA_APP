package com.alenniboris.nba_app.domain.model.statistics.api.nba.main

data class GameStatisticsModelDomain(
    val homeTeamStatistics: TeamInGameStatisticsModelDomain? = TeamInGameStatisticsModelDomain(),
    val homePlayersStatistics: List<PlayerStatisticsModelDomain> = emptyList(),
    val visitorsTeamStatistics: TeamInGameStatisticsModelDomain? = TeamInGameStatisticsModelDomain(),
    val visitorPlayersStatistics: List<PlayerStatisticsModelDomain> = emptyList()
)