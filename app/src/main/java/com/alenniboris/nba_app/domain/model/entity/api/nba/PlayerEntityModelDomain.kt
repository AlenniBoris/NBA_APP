package com.alenniboris.nba_app.domain.model.entity.api.nba

import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain

data class PlayerEntityModelDomain(
    val playerId: Int,
    val userId: String,
    val playerName: String,
    val countryName: String?,
    val playerPosition: String?,
    val playerNumber: String?,
    val id: String
) : IEntityModelDomain

fun PlayerEntityModelDomain.toPlayerModelDomain() =
    PlayerModelDomain(
        id = this.playerId,
        isFollowed = true,
        name = this.playerName,
        country = this.countryName,
        position = this.playerPosition,
        number = this.playerNumber
    )