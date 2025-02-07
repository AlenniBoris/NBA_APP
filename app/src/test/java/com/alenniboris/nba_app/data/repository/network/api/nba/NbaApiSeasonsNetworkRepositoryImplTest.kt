package com.alenniboris.nba_app.data.repository.network.api.nba

import com.alenniboris.nba_app.data.source.remote.api.nba.INbaApiService
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.season.SeasonsResponseModel
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiSeasonsNetworkRepository
import com.alenniboris.nba_app.domain.utils.GsonUtil.fromJson
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class NbaApiSeasonsNetworkRepositoryImplTest : KoinTest {

    private val testScheduler = TestCoroutineScheduler()
    private val ioDispatcher = StandardTestDispatcher(testScheduler)

    private val testModule = module {
        single<INbaApiService> { Mockito.mock(INbaApiService::class.java) }
        single<IAppDispatchers> {
            Mockito.mock(IAppDispatchers::class.java).apply {
                Mockito.`when`(this.IO).thenReturn(ioDispatcher)
            }
        }
        single<INbaApiSeasonsNetworkRepository> {
            NbaApiSeasonsNetworkRepositoryImpl(
                apiService = get<INbaApiService>(),
                dispatchers = get<IAppDispatchers>()
            )
        }
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(testModule)
    }

    private val apiService: INbaApiService by inject()
    private val repository: INbaApiSeasonsNetworkRepository by inject()

    val mockedSeasonsResp = """
            {
                "get": "seasons",
                "parameters": [],
                "errors": [],
                "results": 38,
                "response": [2008, "2008-2009", 2009, "2009-2010", 2010, "2010-2011", 2011, "2011-2012", 2012, "2012-2013", 2013, "2013-2014", 2014, "2014-2015", 2015, "2015-2016", 2016, "2016-2017", 2017, "2017-2018", 2018, "2018-2019", 2019, "2019-2020", 2020, "2020-2021", 2021, "2021-2022", 2022, "2022-2023", "2022-2024", 2023, "2023-2024", "2023-2025", 2024, "2024-2025", 2025, "2025-2026"]
            }

        """.trimIndent().fromJson<SeasonsResponseModel>()
    val mockedSeasonsErrorResp = """
            {
                "parameters": [],
                "errors": [],
                "results": 38,
                "response": [2008, "2008-2009", 2009, "2009-2010", 2010, "2010-2011", 2011, "2011-2012", 2012, "2012-2013", 2013, "2013-2014", 2014, "2014-2015", 2015, "2015-2016", 2016, "2016-2017", 2017, "2017-2018", 2018, "2018-2019", 2019, "2019-2020", 2020, "2020-2021", 2021, "2021-2022", 2022, "2022-2023", "2022-2024", 2023, "2023-2024", "2023-2025", 2024, "2024-2025", 2025, "2025-2026"]
            }

        """.trimIndent().fromJson<SeasonsResponseModel>()

    @Before
    fun setUp() {
        Dispatchers.setMain(ioDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `function works and returns functions`() = runTest {
        Mockito.`when`(apiService.getAllSeasons()).thenReturn(mockedSeasonsResp)
        val res = repository.getAllSeasons()
        assert(res is CustomResultModelDomain.Success)
        assertEquals(
            res.result?.size,
            38
        )
        Mockito.verify(apiService).getAllSeasons()
    }

    @Test
    fun `function works and returns error when json without one paarmeter`() = runTest {
        Mockito.`when`(apiService.getAllSeasons()).thenReturn(mockedSeasonsErrorResp)
        val res = repository.getAllSeasons()
        assert(res is CustomResultModelDomain.Error)
        Mockito.verify(apiService).getAllSeasons()
    }
}