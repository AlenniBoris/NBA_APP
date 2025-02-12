package com.alenniboris.nba_app.data.repository.database.api.nba

import com.alenniboris.nba_app.data.source.local.dao.api.nba.INbaPlayersDao
import com.alenniboris.nba_app.data.source.local.model.api.nba.PlayerEntityModelData
import com.alenniboris.nba_app.di.myModules
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.UserModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.repository.database.api.nba.INbaApiPlayersDatabaseRepository
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
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class NbaApiPlayersDatabaseRepositoryImplTest : KoinTest {

    private val ioDispatcher = StandardTestDispatcher()

    private val databaseFlow = MutableStateFlow<List<PlayerEntityModelData>>(emptyList())
    private val testModule = module {
        single<INbaPlayersDao> {
            object : INbaPlayersDao {
                override suspend fun addPlayerToDatabase(player: PlayerEntityModelData) {
                    if (!databaseFlow.value.contains(player)) {
                        databaseFlow.update { it + player }
                    }
                }

                override suspend fun deletePlayerFromDatabase(player: PlayerEntityModelData) {
                    if (databaseFlow.value.contains(player)) {
                        databaseFlow.update { it - player }
                    }
                }

                override fun getAllPlayersForUser(userId: String): Flow<List<PlayerEntityModelData>> {
                    return databaseFlow
                }
            }
        }
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(myModules + testModule)
    }

    private val dao: INbaPlayersDao by inject()
    private val repository: INbaApiPlayersDatabaseRepository by inject()

    private val notNullUser =
        UserModelDomain(userUid = "firstUser", userEmail = "firstUser@gmail.com")
    private val nullUser: UserModelDomain? = null
    private val firstPlayer = PlayerModelDomain(id = 1)
    private val secondPlayer = PlayerModelDomain(id = 2)

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
        val actualRes = repository.addPlayerToDatabase(firstPlayer, notNullUser)
        val afterOperation = dao.getAllPlayersForUser(notNullUser.userUid).firstOrNull()?.size
        assert(actualRes is CustomResultModelDomain.Success)
        assertEquals(1, afterOperation)
    }

    @Test
    fun `doesnt add an entity if the user is null`() = runTest {
        val addRes = repository.addPlayerToDatabase(firstPlayer, nullUser)
        assert(addRes is CustomResultModelDomain.Error)
    }

    @Test
    fun `deletes an entity if the user is not null`() = runTest {
        repository.addPlayerToDatabase(firstPlayer, notNullUser)
        val deleteRes = repository.deletePlayerFromDatabase(firstPlayer, notNullUser)
        val afterOperation = dao.getAllPlayersForUser(notNullUser.userUid).firstOrNull()?.size
        assert(deleteRes is CustomResultModelDomain.Success)
        assertEquals(0, afterOperation)
    }

    @Test
    fun `doesnt delete add an entity if the user is null`() = runTest {
        repository.addPlayerToDatabase(firstPlayer, notNullUser)
        val deleteRes = repository.deletePlayerFromDatabase(firstPlayer, nullUser)
        assert(deleteRes is CustomResultModelDomain.Error)
    }

    @Test
    fun `returns exact number of entities if add one`() = runTest {
        repository.addPlayerToDatabase(firstPlayer, notNullUser)
        repository.addPlayerToDatabase(firstPlayer, notNullUser)
        val afterOperation = dao.getAllPlayersForUser(notNullUser.userUid).firstOrNull()?.size
        assertEquals(1, afterOperation)
    }

    @Test
    fun `returns exact number of entities if delete one not existing`() = runTest {
        repository.addPlayerToDatabase(firstPlayer, notNullUser)
        repository.deletePlayerFromDatabase(secondPlayer, nullUser)
        val afterOperation = dao.getAllPlayersForUser(notNullUser.userUid).firstOrNull()?.size
        assertEquals(1, afterOperation)
    }
}