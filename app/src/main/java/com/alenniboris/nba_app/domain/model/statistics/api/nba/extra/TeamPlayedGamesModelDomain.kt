package com.alenniboris.nba_app.domain.model.statistics.api.nba.extra

data class TeamPlayedGamesModelDomain(
    val draws: GamesStatsModelDomain?,
    val loses: GamesStatsModelDomain?,
    val played: GamesSimpleStatsModelDomain?,
    val wins: GamesStatsModelDomain?
)
