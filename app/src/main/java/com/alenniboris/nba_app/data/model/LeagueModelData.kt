package com.alenniboris.nba_app.data.model

import android.util.Log
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.google.gson.annotations.SerializedName

data class LeagueModelData(
    val id: String?,
    val name: String?,
    val type: String?,
    val logo: String?,
    val country: CountryModelData?,
    @SerializedName("seasons")
    val playedSeasons: List<SeasonModelData?>?
){
    data class SeasonModelData(
        val season: String?,
        @SerializedName("start")
        val startDate: String?,
        @SerializedName("end")
        val endDate: String?,
        @SerializedName("coverage")
        val seasonDataCoverage: SeasonDataCoverageModelData?
    )

    data class SeasonDataCoverageModelData(
        @SerializedName("games")
        val gamesStatisticsCoverage: GamesStatisticsCoverageModelData?,
        @SerializedName("standings")
        val isStandingsCovered: String?,
        @SerializedName("players")
        val isPlayersCovered: String?,
        @SerializedName("odds")
        val isOddsCovered: String?
    )

    data class GamesStatisticsCoverageModelData(
        @SerializedName("teams")
        val isTeamsCovered: String?,
        @SerializedName("players")
        val isPlayersCovered: String?
    )
}

fun LeagueModelData.toModelDomain(): LeagueModelDomain? = runCatching {
    LeagueModelDomain(
        id = this.id!!.toInt(),
        name = this.name!!,
        type = this.type,
        logo = this.logo,
        country = this.country?.toModelDomain()
    )
}.getOrElse {
    Log.e("MappingError", "LeagueModelData error: \n ${it.stackTraceToString()}")
    null
}

