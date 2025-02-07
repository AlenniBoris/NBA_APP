package com.alenniboris.nba_app.data.repository.database.api.nba

import com.alenniboris.nba_app.data.source.local.dao.api.nba.INbaTeamsDao
import com.alenniboris.nba_app.data.source.local.model.api.nba.toEntityModel
import com.alenniboris.nba_app.data.source.local.model.api.nba.toModelDomain
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.UserModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.repository.database.api.nba.INbaApiTeamsDatabaseRepository
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
class NbaApiTeamsDatabaseRepositoryImplTest : KoinTest {

    private val testScheduler = TestCoroutineScheduler()
    private val ioDispatcher = StandardTestDispatcher(testScheduler)

    private val testModule = module {
        single<INbaTeamsDao> { Mockito.mock(INbaTeamsDao::class.java) }
        single<IAppDispatchers> {
            Mockito.mock(IAppDispatchers::class.java).apply {
                Mockito.`when`(this.IO).thenReturn(ioDispatcher)
            }
        }
        single<INbaApiTeamsDatabaseRepository> {
            NbaApiTeamsDatabaseRepositoryImpl(
                nbaApiTeamsDao = get<INbaTeamsDao>(),
                dispatchers = get<IAppDispatchers>()
            )
        }
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(testModule)
    }

    private val dao: INbaTeamsDao by inject()
    private val repository: INbaApiTeamsDatabaseRepository by inject()

    private val notNullUser =
        UserModelDomain(userUid = "firstUser", userEmail = "firstUser@gmail.com")
    private val nullUser: UserModelDomain? = null
    private val firstTeam = TeamModelDomain(id = 1)
    private val secondTeam = TeamModelDomain(id = 2)

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
        val actualRes = repository.addTeamToDatabase(firstTeam, notNullUser)
        assert(actualRes is CustomResultModelDomain.Success)
        Mockito.verify(dao).addTeamToDatabase(firstTeam.toEntityModel(notNullUser.userUid))
    }

    @Test
    fun `doesnt add an entity if the user is null`() = runTest {
        val addRes = repository.addTeamToDatabase(firstTeam, nullUser)
        assert(addRes is CustomResultModelDomain.Error)
    }

    @Test
    fun `deletes add an entity if the user is not null`() = runTest {
        repository.addTeamToDatabase(firstTeam, notNullUser)
        val deleteRes = repository.deleteTeamFromDatabase(firstTeam, notNullUser)
        assert(deleteRes is CustomResultModelDomain.Success)
        Mockito.verify(dao).deleteTeamFromDatabase(firstTeam.toEntityModel(notNullUser.userUid))
    }

    @Test
    fun `doesnt delete add an entity if the user is null`() = runTest {
        repository.addTeamToDatabase(firstTeam, notNullUser)
        val deleteRes = repository.deleteTeamFromDatabase(firstTeam, nullUser)
        assert(deleteRes is CustomResultModelDomain.Error)
    }

    @Test
    fun `returns exact number of entities if add one`() = runTest {
        Mockito.`when`(dao.getAllTeamsForUser(notNullUser.userUid))
            .thenReturn(
                flowOf(
                    listOf(
                        firstTeam.toEntityModel(notNullUser.userUid)
                    )
                )
            )

        repository.addTeamToDatabase(firstTeam, notNullUser)
        val result = repository.getAllTeamsForUser(notNullUser).firstOrNull()

        TestCase.assertEquals(
            result,
            listOf(firstTeam.toEntityModel(notNullUser.userUid).toModelDomain())
        )
        Mockito.verify(dao).getAllTeamsForUser(notNullUser.userUid)
    }

    @Test
    fun `returns exact number of entities if delete one not existing`() = runTest {

        Mockito.`when`(dao.getAllTeamsForUser(notNullUser.userUid))
            .thenReturn(
                flowOf(
                    listOf(
                        firstTeam.toEntityModel(notNullUser.userUid)
                    )
                )
            )

        repository.addTeamToDatabase(firstTeam, notNullUser)

        repository.deleteTeamFromDatabase(secondTeam, nullUser)

        val result = repository.getAllTeamsForUser(notNullUser).firstOrNull()

        TestCase.assertEquals(
            result,
            listOf(firstTeam.toEntityModel(notNullUser.userUid).toModelDomain())
        )
        Mockito.verify(dao).getAllTeamsForUser(notNullUser.userUid)
    }

    @Test
    fun `returns exact number of entities if add one two times`() = runTest {
        Mockito.`when`(dao.getAllTeamsForUser(notNullUser.userUid))
            .thenReturn(
                flowOf(
                    listOf(
                        firstTeam.toEntityModel(notNullUser.userUid)
                    )
                )
            )

        repository.addTeamToDatabase(firstTeam, notNullUser)
        repository.addTeamToDatabase(firstTeam, notNullUser)

        val result = repository.getAllTeamsForUser(notNullUser).firstOrNull()

        TestCase.assertEquals(result?.size, 1)

        Mockito.verify(dao, Mockito.times(2))
            .addTeamToDatabase(firstTeam.toEntityModel(notNullUser.userUid))
        Mockito.verify(dao).getAllTeamsForUser(notNullUser.userUid)

    }
}