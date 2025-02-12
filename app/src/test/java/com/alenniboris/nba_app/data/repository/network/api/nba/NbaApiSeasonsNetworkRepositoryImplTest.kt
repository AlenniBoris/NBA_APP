package com.alenniboris.nba_app.data.repository.network.api.nba

import com.alenniboris.nba_app.data.source.remote.api.nba.INbaApiSeasonsService
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.season.SeasonsResponseModel
import com.alenniboris.nba_app.di.myModules
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiSeasonsNetworkRepository
import com.alenniboris.nba_app.domain.utils.GsonUtil.fromJson
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
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
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class NbaApiSeasonsNetworkRepositoryImplTest : KoinTest {

    private val ioDispatcher = StandardTestDispatcher()
    val mockedSeasonsResp = """
            {
                "get": "seasons",
                "parameters": [],
                "errors": [],
                "results": 38,
                "response": [2008, "2008-2009", 2009, "2009-2010", 2010, "2010-2011", 2011, "2011-2012", 2012, "2012-2013", 2013, "2013-2014", 2014, "2014-2015", 2015, "2015-2016", 2016, "2016-2017", 2017, "2017-2018", 2018, "2018-2019", 2019, "2019-2020", 2020, "2020-2021", 2021, "2021-2022", 2022, "2022-2023", "2022-2024", 2023, "2023-2024", "2023-2025", 2024, "2024-2025", 2025, "2025-2026"]
            }

        """.trimIndent().fromJson<SeasonsResponseModel>()

    private val testModule = module {
        single<INbaApiSeasonsService> {
            object : INbaApiSeasonsService {
                override suspend fun getAllSeasons(): SeasonsResponseModel {
                    return mockedSeasonsResp
                }
            }
        }
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(myModules + testModule)
    }

    private val repository: INbaApiSeasonsNetworkRepository by inject()

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
        val res = repository.getAllSeasons()
        assert(res is CustomResultModelDomain.Success)
        assertEquals(
            res.result?.size,
            38
        )
    }
}