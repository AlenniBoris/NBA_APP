package com.alenniboris.nba_app.data.source.remote.api.nba.model.response.game


import com.alenniboris.nba_app.data.model.api.nba.player.StatisticsForPlayerModelData
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.NbaApiResponse
import com.google.gson.annotations.SerializedName

data class GameStatisticsForPlayersResponseModel(
    @SerializedName("get")
    override val getHeader: String?,
    @SerializedName("parameters")
    override val queryParameters: GamePlayersStatisticsParameters?,
    @SerializedName("errors")
    override val responseErrors: List<Any>?,
    @SerializedName("response")
    override val responseList: List<StatisticsForPlayerModelData?>?,
    @SerializedName("results")
    override val resultsCount: String?
) : NbaApiResponse<StatisticsForPlayerModelData>() {
    override val isSomePropertyNotReceived: Boolean
        get() = getHeader == null || queryParameters == null || responseErrors == null ||
                resultsCount == null || responseList == null
}

data class GamePlayersStatisticsParameters(
    @SerializedName("id")
    val id: String
)

data class GamePlayerStatisticsErrorsModelData(
    @SerializedName("id")
    val id: String
)