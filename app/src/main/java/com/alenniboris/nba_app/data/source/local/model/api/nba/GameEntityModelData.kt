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
    @ColumnInfo(name = "home_team_id", defaultValue = "0")
    val homeTeamId: Int,
    @ColumnInfo("visitors_team_name")
    val visitorsTeamName: String,
    @ColumnInfo(name = "visitors_team_id", defaultValue = "0")
    val visitorsTeamId: Int,
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
        homeTeamId = this.homeTeam.id,
        visitorsTeamName = this.visitorsTeam.name,
        visitorsTeamId = this.visitorsTeam.id,
        dateOfTheGame = this.dateOfTheGame.time
    )

fun GameEntityModelData.toModelDomain(): GameEntityModelDomain =
    GameEntityModelDomain(
        id = this.id,
        gameId = this.gameId,
        userId = this.userId,
        homeTeamName = this.homeTeamName,
        homeTeamId = this.homeTeamId,
        visitorsTeamName = this.visitorsTeamName,
        visitorsTeamId = this.visitorsTeamId,
        dateOfTheGame = Date(this.dateOfTheGame)
    )

