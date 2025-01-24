package com.alenniboris.nba_app.data.source.local.database.test

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alenniboris.nba_app.data.source.local.model.api.nba.GameEntityModelData
import kotlinx.coroutines.flow.Flow

@Dao
interface ITestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGameToDatabase(entity: TestEntity)

    @Delete
    suspend fun deleteGameFromDatabase(entity: TestEntity)

    @Query("SELECT * FROM table_test_1")
    fun getAllGamesForUser(): Flow<List<TestEntity>>

}