package com.alenniboris.nba_app.data.model.api.nba.team

import com.alenniboris.nba_app.data.model.api.nba.country.CountryModelData
import com.alenniboris.nba_app.data.model.api.nba.country.toModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.utils.LogPrinter
import com.google.gson.annotations.SerializedName

data class TeamModelData(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("national")
    val national: String?,
    @SerializedName("logo")
    val logo: String?,
    @SerializedName("country")
    val country: CountryModelData?
)

fun TeamModelData.toModelDomain(): TeamModelDomain? = runCatching {
    TeamModelDomain(
        id = this.id?.toInt()!!,
        isFollowed = false,
        name = this.name!!,
        isNational = this.national?.toBoolean(),
        logo = this.logo,
        country = this.country?.toModelDomain()
    )
}.getOrElse {
    LogPrinter.printLog("MappingError", "TeamModelData error: \n ${it.stackTraceToString()}")
    null
}