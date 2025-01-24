package com.alenniboris.nba_app.data.source.local.database.test

import com.alenniboris.nba_app.domain.model.IAppDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.withContext

class TestManager(
    private val repos: ITestRepository,
    private val dispatchers: IAppDispatchers
) : ITestManager {

    override val elementsFlow: SharedFlow<List<TestEntity>> =
        repos.getAllEntities()
            .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
            .distinctUntilChanged()
            .shareIn(
                scope = CoroutineScope(SupervisorJob() + dispatchers.IO),
                started = SharingStarted.WhileSubscribed(20_000, 0),
                replay = 1
            )

    override suspend fun addEntity(testEntity: TestEntity) = withContext(dispatchers.IO) {
        repos.addEntityToDatabase(testEntity)
    }

    override suspend fun deleteEntity(testEntity: TestEntity) = withContext(dispatchers.IO) {
        repos.deleteEntityFromDatabase(testEntity)
    }


}