package com.alenniboris.nba_app.data.source.local.database.api.nba

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alenniboris.nba_app.data.source.local.dao.api.nba.INbaGamesDao
import com.alenniboris.nba_app.data.source.local.dao.api.nba.INbaPlayersDao
import com.alenniboris.nba_app.data.source.local.dao.api.nba.INbaTeamsDao
import com.alenniboris.nba_app.data.source.local.model.api.nba.GameEntityModelData
import com.alenniboris.nba_app.data.source.local.model.api.nba.PlayerEntityModelData
import com.alenniboris.nba_app.data.source.local.model.api.nba.TeamEntityModelData


@Database(
    entities = [
        GameEntityModelData::class,
        PlayerEntityModelData::class,
        TeamEntityModelData::class
    ],
    version = 1,
    exportSchema = false
)
abstract class NbaApiDatabase : RoomDatabase() {

    abstract val nbaApiGamesDao: INbaGamesDao
    abstract val nbaApiTeamsDao: INbaTeamsDao
    abstract val nbaApiPlayersDao: INbaPlayersDao

    companion object {
        fun get(
            apl: Application
        ) =
            Room.databaseBuilder(
                context = apl,
                klass = NbaApiDatabase::class.java,
                name = NbaDatabaseValues.DATABASE_FILE
            ).build()
    }

}