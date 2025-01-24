package com.alenniboris.nba_app.data.source.local.database.test

import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.UserModelDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TestRepository(
    private val dao: ITestDao,
    private val dispatchers: IAppDispatchers
) : ITestRepository {

    override suspend fun addEntityToDatabase(entity: TestEntity) =
        withContext(dispatchers.IO) {
            dao.addGameToDatabase(entity)
        }

    override suspend fun deleteEntityFromDatabase(entity: TestEntity) =
        withContext(dispatchers.IO) {
            dao.deleteGameFromDatabase(entity)
        }

    override fun getAllEntities(): Flow<List<TestEntity>> =
        dao.getAllGamesForUser()

}