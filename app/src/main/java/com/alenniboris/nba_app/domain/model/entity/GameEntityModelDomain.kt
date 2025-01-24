package com.alenniboris.nba_app.domain.model.entity

import com.alenniboris.nba_app.domain.model.GameModelDomain
import com.alenniboris.nba_app.domain.model.TeamModelDomain
import java.util.Date

data class GameEntityModelDomain(
    val gameId: Int,
    val userId: String,
    val homeTeamName: String,
    val visitorsTeamName: String,
    val dateOfTheGame: Date,
    val id: String
) : IEntityModelDomain

fun GameEntityModelDomain.toGameModelDomain() : GameModelDomain =
    GameModelDomain(
        id = this.gameId,
        isFollowed = true,
        homeTeam = TeamModelDomain(
            name = this.homeTeamName
        ),
        visitorsTeam = TeamModelDomain(
            name = this.visitorsTeamName
        ),
        dateOfTheGame = this.dateOfTheGame
    )
