package com.alenniboris.nba_app.data.model.api.nba.game

import android.util.Log
import com.alenniboris.nba_app.data.model.api.nba.team.TeamModelData
import com.alenniboris.nba_app.data.model.api.nba.team.toModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.utils.GameStatus
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Locale

data class GameModelData(
    val id: String?,
    @SerializedName("date")
    val dateOfTheGame: String?,
    @SerializedName("time")
    val startingTime: String?,
    @SerializedName("stage")
    val gameStage: String?,
    @SerializedName("week")
    val weekOfTheGame: String?,
    @SerializedName("venue")
    val venueOfTheGame: String?,
    @SerializedName("status")
    val gameStatus: GameStatusInfo?,
    @SerializedName("league")
    val gameLeague: GameLeagueInfo?,
    @SerializedName("country")
    val gameHostingCountry: GameCountryInfo?,
    @SerializedName("teams")
    val teamsData: GameTeamsInfo?,
    @SerializedName("scores")
    val scoresData: GameScoresResponseInfo?,
) {

    data class GameCountryInfo(
        val id: String?,
        val name: String?,
        val code: String?,
        val flag: String?
    )

    data class GameLeagueInfo(
        val id: String?,
        val name: String?,
        val type: String?,
        val season: String?,
        val logo: String?
    )

    data class GameTeamsInfo(
        @SerializedName("home")
        val homeTeam: TeamModelData?,
        @SerializedName("away")
        val awayTeam: TeamModelData?
    )

    data class GameScoresResponseInfo(
        @SerializedName("home")
        val homeTeam: ScoresModelData?,
        @SerializedName("away")
        val awayTeam: ScoresModelData?
    )

    data class GameStatusInfo(
        @SerializedName("long")
        val longStatus: String?,
        @SerializedName("short")
        val shortStatus: String?,
        @SerializedName("timer")
        val currentGameTime: String?
    )

}

fun GameModelData.toModelDomain(): GameModelDomain? = runCatching {

    val date = this.dateOfTheGame?.let {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
            .parse(it)
    }

    GameModelDomain(
        id = this.id?.toInt()!!,
        isFollowed = false,
        dateOfTheGame = date!!,
        gameStage = this.gameStage,
        weekOfTheGame = this.weekOfTheGame,
        venueOfTheGame = this.venueOfTheGame,
        gameStatus = when ((this.gameStatus?.longStatus)) {

            "Not Started" -> GameStatus.Not_Started
            "Quarter 1", "Quarter 2", "Quarter 3", "Quarter 4",
            "Over Time", "Break Time", "Halftime" -> GameStatus.In_Play

            "Game Finished" -> GameStatus.Game_Finished
            "After Over Time" -> GameStatus.Game_Finished
            "Postponed" -> GameStatus.Postponed
            "Cancelled" -> GameStatus.Cancelled
            "Suspended" -> GameStatus.Suspended
            "Awarded" -> GameStatus.Awarded
            "Abandoned" -> GameStatus.Abandoned
            else -> GameStatus.NotDefined
        },
        leagueId = this.gameLeague?.id?.toInt()!!,
        leagueName = this.gameLeague.name!!,
        leagueType = this.gameLeague.type,
        leagueSeason = this.gameLeague.season,
        leagueLogoUrl = this.gameLeague.logo,
        countryId = this.gameHostingCountry?.id?.toInt()!!,
        countryName = this.gameHostingCountry.name!!,
        countryCode = this.gameHostingCountry.code,
        countryFlagUrl = this.gameHostingCountry.flag,
        homeTeam = this.teamsData?.homeTeam?.toModelDomain()!!,
        homeScores = this.scoresData?.homeTeam?.toModelDomain(),
        visitorsTeam = this.teamsData.awayTeam?.toModelDomain()!!,
        visitorsScores = this.scoresData?.awayTeam?.toModelDomain(),
    )
}.getOrElse {
    Log.e("MappingError", "GameModelData error: \n ${it.stackTraceToString()}")
    null
}
