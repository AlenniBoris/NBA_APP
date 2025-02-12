package com.alenniboris.nba_app.data.repository.database.api.nba

import com.alenniboris.nba_app.data.source.local.dao.api.nba.INbaGamesDao
import com.alenniboris.nba_app.data.source.local.model.api.nba.GameEntityModelData
import com.alenniboris.nba_app.di.myModules
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.UserModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.repository.database.api.nba.INbaApiGamesDatabaseRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

@OptIn(ExperimentalCoroutinesApi::class)
class NbaApiGamesDatabaseRepositoryImplTest : KoinTest {

    private val ioDispatcher = StandardTestDispatcher()

    private val databaseFlow = MutableStateFlow<List<GameEntityModelData>>(emptyList())
    private val testModule = module {

        single<INbaGamesDao> {
            object : INbaGamesDao {
                override suspend fun addGameToDatabase(game: GameEntityModelData) {
                    if (!databaseFlow.value.contains(game)) {
                        databaseFlow.update { it + game }
                    }
                }

                override suspend fun deleteGameFromDatabase(game: GameEntityModelData) {
                    if (databaseFlow.value.contains(game)) {
                        databaseFlow.update { it - game }
                    }
                }

                override fun getAllGamesForUser(userId: String): Flow<List<GameEntityModelData>> {
                    return databaseFlow
                }
            }
        }

    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(myModules + testModule)
    }

    private val dao: INbaGamesDao by inject()
    private val repository: INbaApiGamesDatabaseRepository by inject()

    private val notNullUser =
        UserModelDomain(userUid = "firstUser", userEmail = "firstUser@gmail.com")
    private val nullUser: UserModelDomain? = null
    private val firstGame = GameModelDomain(id = 1)
    private val secondGame = GameModelDomain(id = 2)

    @Before
    fun setUp() {
        Dispatchers.setMain(ioDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `adds an entity if the user is not null`() = runTest {
        val actualRes = repository.addGameToDatabase(firstGame, notNullUser)
        repository.addGameToDatabase(firstGame, notNullUser)
        val afterOperation = dao.getAllGamesForUser(notNullUser.userUid).firstOrNull()
        assert(actualRes is CustomResultModelDomain.Success)
        assertEquals(1, afterOperation?.size)
    }

    @Test
    fun `doesnt add an entity if the user is null`() = runTest {
        val addRes = repository.addGameToDatabase(firstGame, nullUser)
        assert(addRes is CustomResultModelDomain.Error)
    }

    @Test
    fun `deletes add an entity if the user is not null`() = runTest {
        repository.addGameToDatabase(firstGame, notNullUser)
        repository.addGameToDatabase(secondGame, notNullUser)
        val deleteRes = repository.deleteGameFromDatabase(firstGame, notNullUser)
        val afterOperation = dao.getAllGamesForUser(notNullUser.userUid).firstOrNull()
        assert(deleteRes is CustomResultModelDomain.Success)
        assertEquals(1, afterOperation?.size)
    }

    @Test
    fun `doesnt delete add an entity if the user is null`() = runTest {
        repository.addGameToDatabase(firstGame, notNullUser)
        val deleteRes = repository.deleteGameFromDatabase(firstGame, nullUser)
        assert(deleteRes is CustomResultModelDomain.Error)
    }

    @Test
    fun `returns exact number of entities if add one`() = runTest {
        repository.addGameToDatabase(firstGame, notNullUser)
        repository.addGameToDatabase(secondGame, notNullUser)
        val result = repository.getAllGamesForUser(notNullUser).firstOrNull()
        assertEquals(2, result?.size)
    }

    @Test
    fun `returns exact number of entities if delete one not existing`() = runTest {
        repository.addGameToDatabase(firstGame, notNullUser)
        repository.deleteGameFromDatabase(secondGame, nullUser)
        val result = repository.getAllGamesForUser(notNullUser).firstOrNull()
        assertEquals(1, result?.size)
    }

    @Test
    fun `returns exact number of entities if add one two times`() = runTest {
        repository.addGameToDatabase(firstGame, notNullUser)
        repository.addGameToDatabase(firstGame, notNullUser)
        val result = repository.getAllGamesForUser(notNullUser).firstOrNull()
        assertEquals(1, result?.size)
    }

}