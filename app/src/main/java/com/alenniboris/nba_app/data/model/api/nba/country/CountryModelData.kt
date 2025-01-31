package com.alenniboris.nba_app.data.model.api.nba.country

import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain
import com.alenniboris.nba_app.domain.utils.LogPrinter

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
    LogPrinter.printLog("MappingError", "CountryModelData error: \n ${it.stackTraceToString()}")
    null
}