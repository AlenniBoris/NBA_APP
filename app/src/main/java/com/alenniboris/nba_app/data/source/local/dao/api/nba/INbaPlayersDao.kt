package com.alenniboris.nba_app.data.source.local.dao.api.nba

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alenniboris.nba_app.data.source.local.model.api.nba.PlayerEntityModelData
import kotlinx.coroutines.flow.Flow

@Dao
interface INbaPlayersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlayerToDatabase(player: PlayerEntityModelData)

    @Delete
    suspend fun deletePlayerFromDatabase(player: PlayerEntityModelData)

    @Query("SELECT * FROM table_players WHERE user_id=:userId")
    fun getAllPlayersForUser(userId: String): Flow<List<PlayerEntityModelData>>

}