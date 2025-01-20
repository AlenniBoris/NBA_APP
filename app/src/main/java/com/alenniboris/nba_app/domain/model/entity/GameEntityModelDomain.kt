package com.alenniboris.nba_app.domain.model.entity

import java.util.Date

data class GameEntityModelDomain(
    val gameId: Int,
    val userId: String,
    val homeTeamName: String,
    val visitorsTeamName: String,
    val dateOfTheGame: Date,
    val id: String
) : IEntityModelDomain
