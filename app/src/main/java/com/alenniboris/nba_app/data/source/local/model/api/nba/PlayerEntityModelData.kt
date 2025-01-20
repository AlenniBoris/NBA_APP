package com.alenniboris.nba_app.data.source.local.model.api.nba

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alenniboris.nba_app.domain.model.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.entity.PlayerEntityModelDomain

@Entity(tableName = "table_players")
data class PlayerEntityModelData(
    @ColumnInfo("player_id")
    val playerId: Int,
    @ColumnInfo("user_id")
    val userId: String,
    @ColumnInfo("player_name")
    val playerName: String,
    @ColumnInfo("country_name")
    val countryName: String?,
    @ColumnInfo("player_position")
    val playerPosition: String?,
    @ColumnInfo("player_number")
    val playerNumber: String?,
    @PrimaryKey(autoGenerate = false)
    val id: String = playerId.toString() + userId
)

fun PlayerModelDomain.toEntityModel(userId: String): PlayerEntityModelData =
    PlayerEntityModelData(
        playerId = this.id,
        userId = userId,
        playerName = this.name,
        countryName = this.country,
        playerPosition = this.position,
        playerNumber = this.number
    )

fun PlayerEntityModelData.toModelDomain(): PlayerEntityModelDomain =
    PlayerEntityModelDomain(
        playerId = this.playerId,
        userId = this.userId,
        playerName = this.playerName,
        countryName = this.countryName,
        playerPosition = this.playerPosition,
        playerNumber = this.playerNumber,
        id = this.id
    )