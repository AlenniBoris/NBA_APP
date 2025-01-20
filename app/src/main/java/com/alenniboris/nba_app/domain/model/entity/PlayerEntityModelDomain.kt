package com.alenniboris.nba_app.domain.model.entity

data class PlayerEntityModelDomain(
    val playerId: Int,
    val userId: String,
    val playerName: String,
    val countryName: String?,
    val playerPosition: String?,
    val playerNumber: String?,
    val id: String
) : IEntityModelDomain
