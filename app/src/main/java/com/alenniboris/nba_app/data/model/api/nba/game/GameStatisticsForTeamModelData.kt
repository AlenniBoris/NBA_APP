package com.alenniboris.nba_app.data.model.api.nba.game

import android.util.Log
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.TeamInGameStatisticsModelDomain
import com.google.gson.annotations.SerializedName

data class GameStatisticsForTeamModelData(
    @SerializedName("assists")
    val assists: String?,
    @SerializedName("blocks")
    val blocks: String?,
    @SerializedName("field_goals")
    val fieldGoals: GoalsStatisticModelData?,
    @SerializedName("freethrows_goals")
    val freeThrowsGoals: GoalsStatisticModelData?,
    @SerializedName("game")
    val game: Game?,
    @SerializedName("personal_fouls")
    val personalFouls: String?,
    @SerializedName("rebounds")
    val rebounds: ReboundsModelData?,
    @SerializedName("steals")
    val steals: String?,
    @SerializedName("team")
    val team: Team?,
    @SerializedName("threepoint_goals")
    val threePointGoals: GoalsStatisticModelData?,
    @SerializedName("turnovers")
    val turnovers: String?
) {

    data class Game(
        @SerializedName("id")
        val id: String?
    )

    data class Team(
        @SerializedName("id")
        val id: String?
    )

}

fun GameStatisticsForTeamModelData.toModelDomain(): TeamInGameStatisticsModelDomain? =
    runCatching {
        TeamInGameStatisticsModelDomain(
            teamId = this.team?.id?.toInt()!!,
            fieldGoals = this.fieldGoals?.toModelDomain(),
            threePointGoals = this.threePointGoals?.toModelDomain(),
            freeThrowsGoals = this.freeThrowsGoals?.toModelDomain(),
            rebounds = this.rebounds?.toModelDomain(),
            assists = this.assists?.toInt(),
            steals = this.steals?.toInt(),
            blocks = this.blocks?.toInt(),
            turnovers = this.turnovers?.toInt(),
            personalFouls = this.personalFouls?.toInt()
        )
    }.getOrElse {
        Log.e("MappingError", "GameStatisticsForTeamModelData")
        null
    }