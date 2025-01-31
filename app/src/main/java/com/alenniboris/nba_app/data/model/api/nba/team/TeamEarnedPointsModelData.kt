package com.alenniboris.nba_app.data.model.api.nba.team

import com.alenniboris.nba_app.domain.model.statistics.api.nba.extra.TeamEarnedPointsModelDomain
import com.alenniboris.nba_app.domain.utils.LogPrinter
import com.google.gson.annotations.SerializedName

data class TeamEarnedPointsModelData(
    @SerializedName("for")
    val playingFor: GamesSimpleStatsModelData?,
    @SerializedName("against")
    val playingAgainst: GamesSimpleStatsModelData?,
)


fun TeamEarnedPointsModelData.toModelDomain(): TeamEarnedPointsModelDomain? = runCatching {
    TeamEarnedPointsModelDomain(
        playingFor = this.playingFor?.toModelDomain(),
        playingAgainst = this.playingAgainst?.toModelDomain()
    )
}.getOrElse {
    LogPrinter.printLog("MappingError", "TeamEarnedPointsModelData")
    null
}