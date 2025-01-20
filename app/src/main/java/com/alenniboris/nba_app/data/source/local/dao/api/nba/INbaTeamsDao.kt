package com.alenniboris.nba_app.data.source.local.dao.api.nba

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alenniboris.nba_app.data.source.local.model.api.nba.TeamEntityModelData
import kotlinx.coroutines.flow.Flow

@Dao
interface INbaTeamsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTeamToDatabase(team: TeamEntityModelData)

    @Delete
    suspend fun deleteTeamFromDatabase(team: TeamEntityModelData)

    @Query("SELECT * FROM table_teams WHERE user_id=:userId")
    fun getAllTeamsForUser(userId: String): Flow<List<TeamEntityModelData>>

    @Query("SELECT * FROM table_teams WHERE team_id=:teamId")
    suspend fun getTeamById(teamId: Int): TeamEntityModelData

}