package com.alenniboris.nba_app.data.source.remote.api.nba.model.response.team

import com.alenniboris.nba_app.data.model.api.nba.team.StatisticsForTeamModelData
import com.google.gson.annotations.SerializedName

data class TeamStatisticsResponseModel(
    @SerializedName("get")
    val getHeader: String?,
    @SerializedName("parameters")
    val queryParameters: TeamStatisticsParameters?,
    @SerializedName("errors")
    val responseErrors: List<Any>?,
    @SerializedName("response")
    val response: StatisticsForTeamModelData?,
    @SerializedName("results")
    val resultsCount: String?
) {
    val isSomePropertyNotReceived: Boolean
        get() = getHeader == null || queryParameters == null || responseErrors == null ||
                resultsCount == null || response == null
}


data class TeamStatisticsParameters(
    val league: String?,
    val season: String?,
    val team: String?
)

data class TeamStatisticsResponseErrorsModelData(
    val league: String?,
    val season: String?,
    val team: String?,
    val plan: String?
)