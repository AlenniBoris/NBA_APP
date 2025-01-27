package com.alenniboris.nba_app.data.source.local.model.api.nba

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.entity.api.nba.GameEntityModelDomain
import java.util.Date

@Entity(tableName = "table_games")
data class GameEntityModelData(
    @ColumnInfo("game_id")
    val gameId: Int,
    @ColumnInfo("user_id")
    val userId: String,
    @ColumnInfo("home_team_name")
    val homeTeamName: String,
    @ColumnInfo("visitors_team_name")
    val visitorsTeamName: String,
    @ColumnInfo("date_of_game")
    val dateOfTheGame: Long,
    @PrimaryKey(autoGenerate = false)
    val id: String = gameId.toString() + userId,
)


fun GameModelDomain.toEntityModel(userId: String): GameEntityModelData =
    GameEntityModelData(
        gameId = this.id,
        userId = userId,
        homeTeamName = this.homeTeam.name,
        visitorsTeamName = this.visitorsTeam.name,
        dateOfTheGame = this.dateOfTheGame.time
    )

fun GameEntityModelData.toModelDomain(): GameEntityModelDomain =
    GameEntityModelDomain(
        id = this.id,
        gameId = this.gameId,
        userId = this.userId,
        homeTeamName = this.homeTeamName,
        visitorsTeamName = this.visitorsTeamName,
        dateOfTheGame = Date(this.dateOfTheGame)
    )

