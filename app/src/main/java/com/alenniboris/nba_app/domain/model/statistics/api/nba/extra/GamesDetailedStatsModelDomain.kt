package com.alenniboris.nba_app.domain.model.statistics.api.nba.extra

data class GamesDetailedStatsModelDomain(
    val all: DetailedStatsModelDomain?,
    val away: DetailedStatsModelDomain?,
    val home: DetailedStatsModelDomain?
)

data class DetailedStatsModelDomain(
    val percentage: Float?,
    val total: Int?
)
