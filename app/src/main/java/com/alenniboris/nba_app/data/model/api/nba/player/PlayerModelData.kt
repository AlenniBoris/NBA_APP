package com.alenniboris.nba_app.data.model.api.nba.player

import android.util.Log
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain

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
    Log.e("MappingError", "LeagueModelData error: \n ${it.stackTraceToString()}")
    null
}