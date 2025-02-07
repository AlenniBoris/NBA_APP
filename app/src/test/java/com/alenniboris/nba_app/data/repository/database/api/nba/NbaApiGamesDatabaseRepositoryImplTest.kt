package com.alenniboris.nba_app.data.repository.database.api.nba

import com.alenniboris.nba_app.data.source.local.dao.api.nba.INbaGamesDao
import com.alenniboris.nba_app.data.source.local.model.api.nba.toEntityModel
import com.alenniboris.nba_app.data.source.local.model.api.nba.toModelDomain
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.UserModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.repository.database.api.nba.INbaApiGamesDatabaseRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class NbaApiGamesDatabaseRepositoryImplTest : KoinTest {

    private val testScheduler = TestCoroutineScheduler()
    private val ioDispatcher = StandardTestDispatcher(testScheduler)

    private val testModule = module {
        single<INbaGamesDao> { Mockito.mock(INbaGamesDao::class.java) }
        single<IAppDispatchers> {
            Mockito.mock(IAppDispatchers::class.java).apply {
                Mockito.`when`(this.IO).thenReturn(ioDispatcher)
            }
        }
        single<INbaApiGamesDatabaseRepository> {
            NbaApiGamesDatabaseRepositoryImpl(
                nbaApiGamesDao = get<INbaGamesDao>(),
                dispatchers = get<IAppDispatchers>()
            )
        }
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(testModule)
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
        assert(actualRes is CustomResultModelDomain.Success)
        Mockito.verify(dao).addGameToDatabase(firstGame.toEntityModel(notNullUser.userUid))
    }

    @Test
    fun `doesnt add an entity if the user is null`() = runTest {
        val addRes = repository.addGameToDatabase(firstGame, nullUser)
        assert(addRes is CustomResultModelDomain.Error)
    }

    @Test
    fun `deletes add an entity if the user is not null`() = runTest {
        repository.addGameToDatabase(firstGame, notNullUser)
        val deleteRes = repository.deleteGameFromDatabase(firstGame, notNullUser)
        assert(deleteRes is CustomResultModelDomain.Success)
        Mockito.verify(dao).deleteGameFromDatabase(firstGame.toEntityModel(notNullUser.userUid))
    }

    @Test
    fun `doesnt delete add an entity if the user is null`() = runTest {
        repository.addGameToDatabase(firstGame, notNullUser)
        val deleteRes = repository.deleteGameFromDatabase(firstGame, nullUser)
        assert(deleteRes is CustomResultModelDomain.Error)
    }

    @Test
    fun `returns exact number of entities if add one`() = runTest {
        Mockito.`when`(dao.getAllGamesForUser(notNullUser.userUid))
            .thenReturn(
                flowOf(
                    listOf(
                        firstGame.toEntityModel(notNullUser.userUid)
                    )
                )
            )

        repository.addGameToDatabase(firstGame, notNullUser)
        val result = repository.getAllGamesForUser(notNullUser).firstOrNull()

        assertEquals(
            result,
            listOf(firstGame.toEntityModel(notNullUser.userUid).toModelDomain())
        )
        Mockito.verify(dao).getAllGamesForUser(notNullUser.userUid)
    }

    @Test
    fun `returns exact number of entities if delete one not existing`() = runTest {

        Mockito.`when`(dao.getAllGamesForUser(notNullUser.userUid))
            .thenReturn(
                flowOf(
                    listOf(
                        firstGame.toEntityModel(notNullUser.userUid)
                    )
                )
            )

        repository.addGameToDatabase(firstGame, notNullUser)

        repository.deleteGameFromDatabase(secondGame, nullUser)

        val result = repository.getAllGamesForUser(notNullUser).firstOrNull()

        assertEquals(
            result,
            listOf(firstGame.toEntityModel(notNullUser.userUid).toModelDomain())
        )
        Mockito.verify(dao).getAllGamesForUser(notNullUser.userUid)
    }

    @Test
    fun `returns exact number of entities if add one two times`() = runTest {
        Mockito.`when`(dao.getAllGamesForUser(notNullUser.userUid))
            .thenReturn(
                flowOf(
                    listOf(
                        firstGame.toEntityModel(notNullUser.userUid)
                    )
                )
            )

        repository.addGameToDatabase(firstGame, notNullUser)
        repository.addGameToDatabase(firstGame, notNullUser)

        val result = repository.getAllGamesForUser(notNullUser).firstOrNull()

        assertEquals(result?.size, 1)

        Mockito.verify(dao, Mockito.times(2))
            .addGameToDatabase(firstGame.toEntityModel(notNullUser.userUid))
        Mockito.verify(dao).getAllGamesForUser(notNullUser.userUid)
    }

}