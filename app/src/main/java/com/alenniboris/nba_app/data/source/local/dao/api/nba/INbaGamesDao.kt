package com.alenniboris.nba_app.data.source.local.dao.api.nba

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alenniboris.nba_app.data.source.local.model.api.nba.GameEntityModelData
import kotlinx.coroutines.flow.Flow

@Dao
interface INbaGamesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGameToDatabase(game: GameEntityModelData)

    @Delete
    suspend fun deleteGameFromDatabase(game: GameEntityModelData)

    @Query("SELECT * FROM table_games WHERE user_id=:userId")
    fun getAllGamesForUser(userId: String): Flow<List<GameEntityModelData>>

    @Query("SELECT * FROM table_games WHERE game_id=:gameId")
    suspend fun getGameById(gameId: Int): GameEntityModelData

}