package com.alenniboris.nba_app.data.model.api.nba.team

import com.alenniboris.nba_app.domain.model.statistics.api.nba.extra.GamesSimpleStatsModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.extra.SimpleStatsModelDomain
import com.alenniboris.nba_app.domain.utils.LogPrinter

data class GamesSimpleStatsModelData(
    val total: SimpleStatsModelData?,
    val average: SimpleStatsModelData?
)

data class SimpleStatsModelData(
    val home: String?,
    val away: String?,
    val all: String?
)

fun SimpleStatsModelData.toModelDomain(): SimpleStatsModelDomain? = runCatching {
    SimpleStatsModelDomain(
        home = this.home?.toInt(),
        away = this.away?.toInt(),
        all = this.all?.toInt(),
    )
}.getOrElse {
    LogPrinter.printLog("MappingError", "SimpleStatsModelData")
    null
}

fun GamesSimpleStatsModelData.toModelDomain(): GamesSimpleStatsModelDomain? = runCatching {
    GamesSimpleStatsModelDomain(
        total = this.total?.toModelDomain(),
        average = this.average?.toModelDomain()
    )
}.getOrElse {
    LogPrinter.printLog("MappingError", "GamesSimpleStatsModelData")
    null
}