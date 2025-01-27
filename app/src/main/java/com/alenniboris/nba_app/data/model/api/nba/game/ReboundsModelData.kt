package com.alenniboris.nba_app.data.model.api.nba.game

import com.alenniboris.nba_app.domain.model.statistics.api.nba.extra.ReboundsModelDomain
import com.google.gson.annotations.SerializedName

data class ReboundsModelData(
    @SerializedName("defense")
    val defense: String?,
    @SerializedName("offence")
    val offence: String?,
    @SerializedName("total")
    val total: String?
)

fun ReboundsModelData.toModelDomain(): ReboundsModelDomain =
    ReboundsModelDomain(
        defense = this.defense?.toInt(),
        offence = this.offence?.toInt(),
        total = this.total?.toInt()
    )