package com.alenniboris.nba_app.data.source.remote.api.nba.model.response.game


import com.alenniboris.nba_app.data.model.api.nba.game.GameStatisticsForTeamModelData
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.NbaApiResponse
import com.google.gson.annotations.SerializedName

data class GameStatisticsForTeamsResponseModel(
    @SerializedName("get")
    override val getHeader: String?,
    @SerializedName("parameters")
    override val queryParameters: GameTeamStatisticsParameters?,
    @SerializedName("errors")
    override val responseErrors: Any?,
    @SerializedName("results")
    override val resultsCount: String?,
    @SerializedName("response")
    override val responseList: List<GameStatisticsForTeamModelData?>?,
) : NbaApiResponse<GameStatisticsForTeamModelData>() {
    override val isSomePropertyNotReceived: Boolean
        get() = getHeader == null || queryParameters == null || responseErrors == null ||
                resultsCount == null || responseList == null
}

data class GameTeamStatisticsParameters(
    @SerializedName("id")
    val id: String
)

data class GameTeamStatisticsErrorsModelData(
    @SerializedName("id")
    val id: String
)