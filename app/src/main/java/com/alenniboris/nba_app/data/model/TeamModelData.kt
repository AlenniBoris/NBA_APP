package com.alenniboris.nba_app.data.model

import android.util.Log
import com.alenniboris.nba_app.domain.model.TeamModelDomain
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
    Log.e("MappingError", "TeamModelData error: \n ${it.stackTraceToString()}")
    null
}