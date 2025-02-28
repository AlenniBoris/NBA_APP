package com.alenniboris.nba_app.data.source.remote.api.nba.model.response.player

import com.alenniboris.nba_app.data.model.api.nba.player.PlayerModelData
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.NbaApiResponse
import com.google.gson.annotations.SerializedName

data class PlayerResponseModel(
    @SerializedName("get")
    override val getHeader: String?,
    @SerializedName("parameters")
    override val queryParameters: PlayerQueryParameters?,
    @SerializedName("errors")
    override val responseErrors: Any?,
    @SerializedName("results")
    override val resultsCount: String?,
    @SerializedName("response")
    override val responseList: List<PlayerModelData?>?
) : NbaApiResponse<PlayerModelData>() {
    override val isSomePropertyNotReceived: Boolean
        get() = getHeader == null || queryParameters == null || responseErrors == null ||
                resultsCount == null || responseList == null
}

data class PlayerQueryParameters(
    val id: Int,
    val team: Int,
    val season: String,
    val search: String
)

data class PlayerResponseErrorsModelData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("team")
    val team: Int,
    @SerializedName("season")
    val season: String,
    @SerializedName("search")
    val search: String,
    @SerializedName("required")
    val required: String
)