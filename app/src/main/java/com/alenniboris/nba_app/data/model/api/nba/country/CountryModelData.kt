package com.alenniboris.nba_app.data.model.api.nba.country

import android.util.Log
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain

data class CountryModelData(
    val id: String?,
    val name: String?,
    val code: String?,
    val flag: String?
)

fun CountryModelData.toModelDomain(): CountryModelDomain? = runCatching {
    CountryModelDomain(
        id = this.id?.toInt()!!,
        name = this.name!!,
        code = this.code,
        flag = this.flag
    )
}.getOrElse {
    Log.e("MappingError", "CountryModelData error: \n ${it.stackTraceToString()}")
    null
}