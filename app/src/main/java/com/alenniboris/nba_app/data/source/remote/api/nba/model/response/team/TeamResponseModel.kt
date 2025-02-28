package com.alenniboris.nba_app.data.source.remote.api.nba.model.response.team

import com.alenniboris.nba_app.data.model.api.nba.team.TeamModelData
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.NbaApiResponse
import com.google.gson.annotations.SerializedName

data class TeamResponseModel(
    @SerializedName("get")
    override val getHeader: String?,
    @SerializedName("parameters")
    override val queryParameters: TeamQueryParameters?,
    @SerializedName("errors")
    override val responseErrors: Any?,
    @SerializedName("results")
    override val resultsCount: String?,
    @SerializedName("response")
    override val responseList: List<TeamModelData?>?
) : NbaApiResponse<TeamModelData>() {
    override val isSomePropertyNotReceived: Boolean
        get() = getHeader == null || queryParameters == null || responseErrors == null ||
                resultsCount == null || responseList == null
}

data class TeamQueryParameters(
    val id: Int,
    val search: String,
    @SerializedName("country_id")
    val countryId: Int,
    val country: String,
    @SerializedName("league")
    val leagueId: Int,
    val season: String
)

data class TeamResponseErrorsModelData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("search")
    val search: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("league")
    val league: Int,
    @SerializedName("season")
    val season: String,
    @SerializedName("required")
    val required: String
)