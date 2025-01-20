package com.alenniboris.nba_app.data.source.remote.api.nba.model

import com.alenniboris.nba_app.data.model.CountryModelData
import com.google.gson.annotations.SerializedName


data class CountriesResponseModel(
    @SerializedName("get")
    override val getHeader: String?,
    @SerializedName("parameters")
    override val queryParameters: Any?,
    @SerializedName("errors")
    override val responseErrors: Any?,
    @SerializedName("results")
    override val resultsCount: String?,
    @SerializedName("response")
    override val responseList: List<CountryModelData?>?
) : NbaApiResponse<CountryModelData>() {
    override val isSomePropertyNotReceived: Boolean
        get() = getHeader == null || queryParameters == null || responseErrors == null ||
                resultsCount == null || responseList == null
}

data class CountriesQueryParameters(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val countryName: String,
    @SerializedName("code")
    val countryCode: String,
    @SerializedName("search")
    val searchQuery: String
)

data class CountriesResponseErrorsModelData(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("search")
    val search: String,
    @SerializedName("required")
    val required: String
)