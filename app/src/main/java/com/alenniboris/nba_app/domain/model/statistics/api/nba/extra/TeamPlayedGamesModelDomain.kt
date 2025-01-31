package com.alenniboris.nba_app.domain.model.statistics.api.nba.extra

data class TeamPlayedGamesModelDomain(
    val draws: GamesDetailedStatsModelDomain?,
    val loses: GamesDetailedStatsModelDomain?,
    val played: GamesSimpleStatsModelDomain?,
    val wins: GamesDetailedStatsModelDomain?
)
