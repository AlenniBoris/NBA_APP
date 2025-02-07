package com.alenniboris.nba_app.data.repository.database.api.nba

import com.alenniboris.nba_app.data.source.local.dao.api.nba.INbaPlayersDao
import com.alenniboris.nba_app.data.source.local.model.api.nba.toEntityModel
import com.alenniboris.nba_app.data.source.local.model.api.nba.toModelDomain
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.UserModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.repository.database.api.nba.INbaApiPlayersDatabaseRepository
import junit.framework.TestCase
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
class NbaApiPlayersDatabaseRepositoryImplTest : KoinTest {

    private val testScheduler = TestCoroutineScheduler()
    private val ioDispatcher = StandardTestDispatcher(testScheduler)

    private val testModule = module {
        single<INbaPlayersDao> { Mockito.mock(INbaPlayersDao::class.java) }
        single<IAppDispatchers> {
            Mockito.mock(IAppDispatchers::class.java).apply {
                Mockito.`when`(this.IO).thenReturn(ioDispatcher)
            }
        }
        single<INbaApiPlayersDatabaseRepository> {
            NbaApiPlayersDatabaseRepositoryImpl(
                nbaApiPlayersDao = get<INbaPlayersDao>(),
                dispatchers = get<IAppDispatchers>()
            )
        }
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(testModule)
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
        assert(actualRes is CustomResultModelDomain.Success)
        Mockito.verify(dao).addPlayerToDatabase(firstPlayer.toEntityModel(notNullUser.userUid))
    }

    @Test
    fun `doesnt add an entity if the user is null`() = runTest {
        val addRes = repository.addPlayerToDatabase(firstPlayer, nullUser)
        assert(addRes is CustomResultModelDomain.Error)
    }

    @Test
    fun `deletes add an entity if the user is not null`() = runTest {
        repository.addPlayerToDatabase(firstPlayer, notNullUser)
        val deleteRes = repository.deletePlayerFromDatabase(firstPlayer, notNullUser)
        assert(deleteRes is CustomResultModelDomain.Success)
        Mockito.verify(dao).deletePlayerFromDatabase(firstPlayer.toEntityModel(notNullUser.userUid))
    }

    @Test
    fun `doesnt delete add an entity if the user is null`() = runTest {
        repository.addPlayerToDatabase(firstPlayer, notNullUser)
        val deleteRes = repository.deletePlayerFromDatabase(firstPlayer, nullUser)
        assert(deleteRes is CustomResultModelDomain.Error)
    }

    @Test
    fun `returns exact number of entities if add one`() = runTest {
        Mockito.`when`(dao.getAllPlayersForUser(notNullUser.userUid))
            .thenReturn(
                flowOf(
                    listOf(
                        firstPlayer.toEntityModel(notNullUser.userUid)
                    )
                )
            )

        repository.addPlayerToDatabase(firstPlayer, notNullUser)
        val result = repository.getAllPlayersForUser(notNullUser).firstOrNull()

        TestCase.assertEquals(
            result,
            listOf(firstPlayer.toEntityModel(notNullUser.userUid).toModelDomain())
        )
        Mockito.verify(dao).getAllPlayersForUser(notNullUser.userUid)
    }

    @Test
    fun `returns exact number of entities if delete one not existing`() = runTest {

        Mockito.`when`(dao.getAllPlayersForUser(notNullUser.userUid))
            .thenReturn(
                flowOf(
                    listOf(
                        firstPlayer.toEntityModel(notNullUser.userUid)
                    )
                )
            )

        repository.addPlayerToDatabase(firstPlayer, notNullUser)

        repository.deletePlayerFromDatabase(secondPlayer, nullUser)

        val result = repository.getAllPlayersForUser(notNullUser).firstOrNull()

        TestCase.assertEquals(
            result,
            listOf(firstPlayer.toEntityModel(notNullUser.userUid).toModelDomain())
        )
        Mockito.verify(dao).getAllPlayersForUser(notNullUser.userUid)
    }

    @Test
    fun `returns exact number of entities if add one two times`() = runTest {
        Mockito.`when`(dao.getAllPlayersForUser(notNullUser.userUid))
            .thenReturn(
                flowOf(
                    listOf(
                        firstPlayer.toEntityModel(notNullUser.userUid)
                    )
                )
            )

        repository.addPlayerToDatabase(firstPlayer, notNullUser)
        repository.addPlayerToDatabase(firstPlayer, notNullUser)

        val result = repository.getAllPlayersForUser(notNullUser).firstOrNull()

        TestCase.assertEquals(result?.size, 1)

        Mockito.verify(dao, Mockito.times(2))
            .addPlayerToDatabase(firstPlayer.toEntityModel(notNullUser.userUid))
        Mockito.verify(dao).getAllPlayersForUser(notNullUser.userUid)

    }
}