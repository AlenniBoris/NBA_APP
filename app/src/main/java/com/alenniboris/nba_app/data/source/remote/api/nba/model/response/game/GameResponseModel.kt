package com.alenniboris.nba_app.data.source.remote.api.nba.model.response.game

import com.alenniboris.nba_app.data.model.api.nba.game.GameModelData
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.NbaApiResponse
import com.google.gson.annotations.SerializedName

data class GameResponseModel(
    @SerializedName("get")
    override val getHeader: String?,
    @SerializedName("parameters")
    override val queryParameters: GamesQueryParameters?,
    @SerializedName("errors")
    override val responseErrors: Any?,
    @SerializedName("results")
    override val resultsCount: String?,
    @SerializedName("response")
    override val responseList: List<GameModelData?>?
) : NbaApiResponse<GameModelData>() {
    override val isSomePropertyNotReceived: Boolean
        get() = getHeader == null || queryParameters == null || responseErrors == null ||
                resultsCount == null || responseList == null
}

data class GamesQueryParameters(
    val id: Int,
    @SerializedName("date")
    val dateOfTheGame: String,
    @SerializedName("league")
    val leagueId: Int,
    val season: String,
    @SerializedName("team")
    val teamId: Int,
    val timezone: String
)

data class GamesResponseErrorsModelData(
    @SerializedName("id")
    val id: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("league")
    val league: String,
    @SerializedName("season")
    val season: String,
    @SerializedName("team")
    val team: String,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("required")
    val required: String
)