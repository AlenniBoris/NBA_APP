package com.alenniboris.nba_app.data.model.api.nba.team

import com.alenniboris.nba_app.domain.model.statistics.api.nba.extra.DetailedStatsModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.extra.GamesDetailedStatsModelDomain
import com.alenniboris.nba_app.domain.utils.LogPrinter
import com.google.gson.annotations.SerializedName

data class GamesDetailedStatsModelData(
    val all: DetailedStatsModelData?,
    val away: DetailedStatsModelData?,
    val home: DetailedStatsModelData?
)

data class DetailedStatsModelData(
    @SerializedName("percentage")
    val percentage: String?,
    @SerializedName("total")
    val total: String?
)

fun DetailedStatsModelData.toModelDomain(): DetailedStatsModelDomain? = runCatching {
    DetailedStatsModelDomain(
        percentage = this.percentage?.toFloat(),
        total = this.total?.toInt()
    )
}.getOrElse {
    LogPrinter.printLog("MappingError", "DetailedStatsModelData")
    null
}

fun GamesDetailedStatsModelData.toModelDomain(): GamesDetailedStatsModelDomain? = runCatching {
    GamesDetailedStatsModelDomain(
        all = this.all?.toModelDomain(),
        away = this.away?.toModelDomain(),
        home = this.home?.toModelDomain()
    )
}.getOrElse {
    LogPrinter.printLog("MappingError", "GamesStatsModelData")
    null
}