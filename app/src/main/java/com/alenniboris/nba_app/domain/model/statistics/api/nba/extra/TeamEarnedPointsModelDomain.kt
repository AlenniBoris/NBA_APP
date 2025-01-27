package com.alenniboris.nba_app.domain.model.statistics.api.nba.extra

data class TeamEarnedPointsModelDomain(
    val playingFor: GamesSimpleStatsModelDomain?,
    val playingAgainst: GamesSimpleStatsModelDomain?,
)

