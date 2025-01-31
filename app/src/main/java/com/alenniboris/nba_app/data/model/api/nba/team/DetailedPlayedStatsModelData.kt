package com.alenniboris.nba_app.data.model.api.nba.team

import com.alenniboris.nba_app.domain.model.statistics.api.nba.extra.DetailedPlayedStatsModelDomain
import com.alenniboris.nba_app.domain.utils.LogPrinter
import com.google.gson.annotations.SerializedName


data class DetailedPlayedStatsModelData(
    @SerializedName("percentage")
    val percentage: String?,
    @SerializedName("total")
    val total: String?
)

fun DetailedPlayedStatsModelData.toModelDomain(): DetailedPlayedStatsModelDomain? = runCatching {
    DetailedPlayedStatsModelDomain(
        percentage = this.percentage?.toFloat(),
        total = this.total?.toInt()
    )
}.getOrElse {
    LogPrinter.printLog("MappingError", "DetailedPlayedStatsModelData")
    null
}