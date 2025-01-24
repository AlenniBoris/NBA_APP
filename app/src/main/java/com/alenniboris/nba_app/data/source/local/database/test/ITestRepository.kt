package com.alenniboris.nba_app.data.source.local.database.test

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.UserModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import kotlinx.coroutines.flow.Flow

interface ITestRepository {

    suspend fun addEntityToDatabase(
        entity: TestEntity
    )

    suspend fun deleteEntityFromDatabase(
        entity: TestEntity
    )

    fun getAllEntities(): Flow<List<TestEntity>>

}