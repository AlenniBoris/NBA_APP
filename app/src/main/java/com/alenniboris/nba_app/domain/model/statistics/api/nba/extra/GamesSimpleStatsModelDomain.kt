package com.alenniboris.nba_app.domain.model.statistics.api.nba.extra

data class GamesSimpleStatsModelDomain(
    val total: SimpleStatsModelDomain? = null,
    val average: SimpleStatsModelDomain? = null
)

data class SimpleStatsModelDomain(
    val home: Int?,
    val away: Int?,
    val all: Int?
)