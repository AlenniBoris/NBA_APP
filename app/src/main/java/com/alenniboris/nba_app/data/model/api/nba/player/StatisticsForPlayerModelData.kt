package com.alenniboris.nba_app.data.model.api.nba.player

import com.alenniboris.nba_app.data.model.api.nba.game.GoalsStatisticModelData
import com.alenniboris.nba_app.data.model.api.nba.game.ReboundsModelData
import com.alenniboris.nba_app.data.model.api.nba.game.toModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.PlayerStatisticsModelDomain
import com.alenniboris.nba_app.domain.utils.LogPrinter
import com.google.gson.annotations.SerializedName

data class StatisticsForPlayerModelData(
    val assists: String?,
    @SerializedName("field_goals")
    val fieldGoals: GoalsStatisticModelData?,
    @SerializedName("freethrows_goals")
    val freeThrowsGoals: GoalsStatisticModelData?,
    @SerializedName("game")
    val game: Game?,
    @SerializedName("minutes")
    val minutes: String?,
    @SerializedName("player")
    val player: Player?,
    @SerializedName("points")
    val points: String?,
    @SerializedName("rebounds")
    val rebounds: ReboundsModelData?,
    @SerializedName("team")
    val team: Team?,
    @SerializedName("threepoint_goals")
    val threePointGoals: GoalsStatisticModelData?,
    @SerializedName("type")
    val type: String?
) {

    data class Game(
        @SerializedName("id")
        val id: String?
    )

    data class Player(
        @SerializedName("id")
        val id: String?,
        val name: String?
    )

    data class Team(
        @SerializedName("id")
        val id: String?
    )

}

fun StatisticsForPlayerModelData.toModelDomain(): PlayerStatisticsModelDomain? = runCatching {
    PlayerStatisticsModelDomain(
        gameId = this.game?.id?.toInt()!!,
        playerId = this.player?.id?.toInt()!!,
        playerName = this.player.name!!,
        teamId = this.team?.id?.toInt()!!,
        playedMinutes = this.minutes,
        fieldGoals = this.fieldGoals?.toModelDomain(),
        threePointGoals = this.threePointGoals?.toModelDomain(),
        freeThrowsGoals = this.freeThrowsGoals?.toModelDomain(),
        rebounds = this.rebounds?.toModelDomain(),
        assists = this.assists?.toInt(),
        earnedPoints = this.points?.toInt()
    )
}.getOrElse {
    LogPrinter.printLog("MappingError", "StatisticsForPlayerModelData")
    null
}