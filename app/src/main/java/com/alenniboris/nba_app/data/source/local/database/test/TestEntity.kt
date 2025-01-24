package com.alenniboris.nba_app.data.source.local.database.test

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "table_test_1")
data class TestEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val w: String = UUID.randomUUID().toString(),
    val c: String = UUID.randomUUID().toString(),
    @ColumnInfo(defaultValue = "")
    val d: String = UUID.randomUUID().toString(),
)
