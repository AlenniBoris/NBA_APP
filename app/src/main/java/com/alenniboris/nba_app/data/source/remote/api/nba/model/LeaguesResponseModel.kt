package com.alenniboris.nba_app.data.source.remote.api.nba.model

import com.alenniboris.nba_app.data.model.LeagueModelData
import com.google.gson.annotations.SerializedName

data class LeaguesResponseModel(
    @SerializedName("get")
    override val getHeader: String?,
    @SerializedName("parameters")
    override val queryParameters: LeaguesQueryParameters?,
    @SerializedName("errors")
    override val responseErrors: Any?,
    @SerializedName("results")
    override val resultsCount: String?,
    @SerializedName("response")
    override val responseList: List<LeagueModelData>?
) : NbaApiResponse<LeagueModelData>() {
    override val isSomePropertyNotReceived: Boolean
        get() = getHeader == null || queryParameters == null || responseErrors == null ||
                resultsCount == null || responseList == null
}

data class LeaguesQueryParameters(
    val id: Int,
    @SerializedName("name")
    val leagueName: String,
    @SerializedName("country_id")
    val countryId: String,
    @SerializedName("country")
    val countryName: String,
    @SerializedName("type")
    val leagueType: String,
    @SerializedName("season")
    val season: String,
    @SerializedName("search")
    val searchQuery: String,
    @SerializedName("code")
    val countryCode: String,
)

data class LeaguesResponseErrorsModelData(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("country_id")
    val country_id: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("season")
    val season: String,
    @SerializedName("search")
    val search: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("required")
    val required: String
)