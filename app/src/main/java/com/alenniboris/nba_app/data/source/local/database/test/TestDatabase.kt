package com.alenniboris.nba_app.data.source.local.database.test

import android.app.Application
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.DeleteTable
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec

@Database(
    entities = [TestEntity::class],
    version = 5,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3, spec = TestDatabase.AutoMigrationFrom2To3::class),
        AutoMigration(from = 3, to = 4, spec = TestDatabase.AutoMigrationFrom3To4::class),
        AutoMigration(from = 4, to = 5, spec = TestDatabase.AutoMigrationFrom4To5::class)
    ]
)
abstract class TestDatabase : RoomDatabase(){

    abstract val testDao: ITestDao

    @DeleteColumn.Entries(
        DeleteColumn(tableName = "table_test", columnName = "a")
    )
    class AutoMigrationFrom2To3: AutoMigrationSpec

    @RenameColumn.Entries(
        RenameColumn(tableName = "table_test", fromColumnName = "b", toColumnName = "w")
    )
    class AutoMigrationFrom3To4: AutoMigrationSpec

    @DeleteTable.Entries(
        DeleteTable(tableName = "table_test")
    )
    class AutoMigrationFrom4To5 : AutoMigrationSpec

    companion object {
        fun get(
            apl: Application
        ) =
            Room.databaseBuilder(
                context = apl,
                klass = TestDatabase::class.java,
                name = "test-database.db"
            ).build()
    }

}