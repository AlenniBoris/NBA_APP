package com.alenniboris.nba_app.data.source.local.database.test

import kotlinx.coroutines.flow.SharedFlow

interface ITestManager {

    val elementsFlow: SharedFlow<List<TestEntity>>

    suspend fun addEntity(testEntity: TestEntity)

    suspend fun deleteEntity(testEntity: TestEntity)

}