package com.alenniboris.nba_app.data.source.local.model.api.nba

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.entity.api.nba.TeamEntityModelDomain

@Entity(tableName = "table_teams")
data class TeamEntityModelData(
    @ColumnInfo("team_id")
    val teamId: Int,
    @ColumnInfo("user_id")
    val userId: String,
    @ColumnInfo("team_name")
    val teamName: String,
    @ColumnInfo("is_team_national")
    val isNational: Boolean?,
    @ColumnInfo("country_name")
    val countryName: String?,
    @PrimaryKey(autoGenerate = false)
    val id: String = teamId.toString() + userId
)

fun TeamModelDomain.toEntityModel(userId: String): TeamEntityModelData =
    TeamEntityModelData(
        teamId = this.id,
        userId = userId,
        teamName = this.name,
        isNational = this.isNational,
        countryName = this.country?.name,
    )

fun TeamEntityModelData.toModelDomain(): TeamEntityModelDomain =
    TeamEntityModelDomain(
        id = this.id,
        teamId = this.teamId,
        userId = this.userId,
        teamName = this.teamName,
        isNational = this.isNational,
        countryName = this.countryName,
    )
