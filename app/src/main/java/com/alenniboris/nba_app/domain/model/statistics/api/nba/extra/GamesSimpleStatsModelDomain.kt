package com.alenniboris.nba_app.domain.model.statistics.api.nba.extra

data class GamesSimpleStatsModelDomain(
    val total: SimpleStatsModelDomain?,
    val average: SimpleStatsModelDomain?
)

data class SimpleStatsModelDomain(
    val home: Int?,
    val away: Int?,
    val all: Int?
)