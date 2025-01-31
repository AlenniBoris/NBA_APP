package com.alenniboris.nba_app.data.model.api.nba.player

import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.utils.LogPrinter

data class PlayerModelData(
    val id: String?,
    val name: String?,
    val number: String?,
    val country: String?,
    val position: String?,
    val age: String?
)

fun PlayerModelData.toModelDomain(): PlayerModelDomain? = runCatching {
    PlayerModelDomain(
        id = this.id!!.toInt(),
        isFollowed = false,
        name = this.name!!,
        number = this.number,
        country = this.country,
        position = this.position,
        age = this.age?.toInt()
    )
}.getOrElse {
    LogPrinter.printLog("MappingError", "LeagueModelData error: \n ${it.stackTraceToString()}")
    null
}