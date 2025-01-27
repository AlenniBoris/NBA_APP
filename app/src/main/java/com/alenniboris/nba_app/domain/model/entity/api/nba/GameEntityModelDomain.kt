package com.alenniboris.nba_app.domain.model.entity.api.nba

import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import java.util.Date

data class GameEntityModelDomain(
    val gameId: Int,
    val userId: String,
    val homeTeamName: String,
    val homeTeamId: Int,
    val visitorsTeamName: String,
    val visitorsTeamId: Int,
    val dateOfTheGame: Date,
    val id: String
) : IEntityModelDomain

fun GameEntityModelDomain.toGameModelDomain(): GameModelDomain =
    GameModelDomain(
        id = this.gameId,
        isFollowed = true,
        homeTeam = TeamModelDomain(
            id = this.homeTeamId,
            name = this.homeTeamName
        ),
        visitorsTeam = TeamModelDomain(
            id = this.visitorsTeamId,
            name = this.visitorsTeamName
        ),
        dateOfTheGame = this.dateOfTheGame
    )
