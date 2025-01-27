package com.alenniboris.nba_app.data.model.api.nba.team

import android.util.Log
import com.alenniboris.nba_app.domain.model.statistics.api.nba.extra.TeamEarnedPointsModelDomain
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
    Log.e("MappingError", "TeamEarnedPointsModelData")
    null
}