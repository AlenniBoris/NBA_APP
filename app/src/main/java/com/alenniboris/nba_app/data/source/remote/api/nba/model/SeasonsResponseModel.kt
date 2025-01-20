package com.alenniboris.nba_app.data.source.remote.api.nba.model

import com.google.gson.annotations.SerializedName

data class SeasonsResponseModel(
    @SerializedName("get")
    override val getHeader: String?,
    @SerializedName("parameters")
    override val queryParameters: Any?,
    @SerializedName("errors")
    override val responseErrors: Any?,
    @SerializedName("results")
    override val resultsCount: String?,
    @SerializedName("response")
    override val responseList: List<String?>?
) : NbaApiResponse<String>() {
    override val isSomePropertyNotReceived: Boolean
        get() = getHeader == null || queryParameters == null || responseErrors == null ||
                resultsCount == null || responseList == null
}

data class SeasonsResponseErrorModelData(
    val error: String?
)