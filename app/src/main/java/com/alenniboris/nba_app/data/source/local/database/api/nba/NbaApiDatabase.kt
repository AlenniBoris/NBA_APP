package com.alenniboris.nba_app.data.source.local.database.api.nba

import android.app.Application
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
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
    version = 5,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = NbaApiDatabase.MigrationToVersion2::class),
        AutoMigration(from = 2, to = 3, spec = NbaApiDatabase.MigrationToVersion3::class),
        AutoMigration(from = 3, to = 4, spec = NbaApiDatabase.MigrationToVersion4::class),
        AutoMigration(from = 4, to = 5, spec = NbaApiDatabase.MigrationToVersion5::class)
    ]
)
abstract class NbaApiDatabase : RoomDatabase() {

    abstract val nbaApiGamesDao: INbaGamesDao
    abstract val nbaApiTeamsDao: INbaTeamsDao
    abstract val nbaApiPlayersDao: INbaPlayersDao

    @DeleteColumn.Entries(
        DeleteColumn(tableName = "table_teams", columnName = "team_logo")
    )
    class MigrationToVersion2 : AutoMigrationSpec

    class MigrationToVersion3 : AutoMigrationSpec

    class MigrationToVersion4 : AutoMigrationSpec

    class MigrationToVersion5 : AutoMigrationSpec

    companion object {
        fun get(
            apl: Application
        ) =
            Room.databaseBuilder(
                context = apl,
                klass = NbaApiDatabase::class.java,
                name = NbaDatabaseValues.DATABASE_FILE
            )
                .build()
    }

}