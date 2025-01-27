package com.alenniboris.nba_app.data.model.api.nba.game

import com.alenniboris.nba_app.domain.model.statistics.api.nba.extra.GoalsStatisticsModelDomain
import com.google.gson.annotations.SerializedName

data class GoalsStatisticModelData(
    @SerializedName("attempts")
    val attempts: String?,
    @SerializedName("percentage")
    val percentage: String?,
    @SerializedName("total")
    val total: String?
)

fun GoalsStatisticModelData.toModelDomain(): GoalsStatisticsModelDomain =
    GoalsStatisticsModelDomain(
        attempts = this.attempts?.toInt(),
        total = this.total?.toInt(),
        percentage = this.percentage?.toInt()
    )