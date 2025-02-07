package com.alenniboris.nba_app.data.repository.network.api.nba

import com.alenniboris.nba_app.data.model.api.nba.player.PlayerModelData
import com.alenniboris.nba_app.data.model.api.nba.player.toModelDomain
import com.alenniboris.nba_app.data.source.remote.api.nba.INbaApiService
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.game.GameStatisticsForPlayersResponseModel
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.player.PlayerResponseModel
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiPlayersNetworkRepository
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
class NbaApiPlayersNetworkRepositoryImplTest : KoinTest {

    private val testScheduler = TestCoroutineScheduler()
    private val ioDispatcher = StandardTestDispatcher(testScheduler)

    private val testModule = module {
        single<INbaApiService> { Mockito.mock(INbaApiService::class.java) }
        single<IAppDispatchers> {
            Mockito.mock(IAppDispatchers::class.java).apply {
                Mockito.`when`(this.IO).thenReturn(ioDispatcher)
            }
        }
        single<INbaApiPlayersNetworkRepository> {
            NbaApiPlayersNetworkRepositoryImpl(
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
    private val repository: INbaApiPlayersNetworkRepository by inject()

    private val mockedPlayer = """
        {
            "id": 6265,
            "name": "Kuznetsov Sergiy",
            "number": "30",
            "country": "Ukraine",
            "position": "Guard",
            "age": 26
        }
    """.trimIndent().fromJson<PlayerModelData>()

    private val mockedPlayerResponse = """
        {
            "get": "players",
            "parameters": {
                "id": "6265"
            },
            "errors": [],
            "results": 1,
            "response": [
                {
                    "id": 6265,
                    "name": "Kuznetsov Sergiy",
                    "number": "30",
                    "country": "Ukraine",
                    "position": "Guard",
                    "age": 26
                }
            ]
        }
    """.trimIndent().fromJson<PlayerResponseModel>()

    private val mockedPlayerErrorResponse = """
        {
            "parameters": {
                "id": "6265"
            },
            "errors": [],
            "results": 1,
            "response": [
                {
                    "id": 6265,
                    "name": "Kuznetsov Sergiy",
                    "number": "30",
                    "country": "Ukraine",
                    "position": "Guard",
                    "age": 26
                }
            ]
        }
    """.trimIndent().fromJson<PlayerResponseModel>()

    private val mockedPlayerStatisticsResponse = """
        {
            "get": "games",
            "parameters": {
                "season": "2021-2022",
                "player": "6265"
            },
            "errors": [],
            "results": 22,
            "response": [
                {
                    "game": {
                        "id": 152667
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "1:29",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 2,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 142713
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "0:00",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 143679
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "0:00",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 143687
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "0:00",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 144851
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "4:02",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 1,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 145330
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "0:00",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 145339
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "0:36",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 146074
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "1:40",
                    "field_goals": {
                        "total": 0,
                        "attempts": 1,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 146082
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "1:01",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 151600
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "1:33",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 151611
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "0:48",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 151616
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "0:00",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 151622
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "0:00",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 151629
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "1:28",
                    "field_goals": {
                        "total": 0,
                        "attempts": 1,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 150812
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "0:00",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 153392
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "0:47",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 2,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 153398
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "10:44",
                    "field_goals": {
                        "total": 2,
                        "attempts": 3,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 1,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 2
                    },
                    "assists": 2,
                    "points": 4
                },
                {
                    "game": {
                        "id": 153402
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "0:34",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 153407
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "1:55",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 154289
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "4:01",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 154305
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "2:15",
                    "field_goals": {
                        "total": 0,
                        "attempts": 1,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 2,
                        "attempts": 3,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 2
                    },
                    "assists": 0,
                    "points": 2
                },
                {
                    "game": {
                        "id": 154293
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "20:09",
                    "field_goals": {
                        "total": 1,
                        "attempts": 1,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 1,
                        "attempts": 2,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 4
                    },
                    "assists": 1,
                    "points": 5
                }
            ]
        }
    """.trimIndent().fromJson<GameStatisticsForPlayersResponseModel>()

    private val mockedPlayerStatisticsErrorResponse = """
        {
            "parameters": {
                "season": "2021-2022",
                "player": "6265"
            },
            "errors": [],
            "results": 22,
            "response": [
                {
                    "game": {
                        "id": 152667
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "1:29",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 2,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 142713
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "0:00",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 143679
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "0:00",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 143687
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "0:00",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 144851
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "4:02",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 1,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 145330
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "0:00",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 145339
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "0:36",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 146074
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "1:40",
                    "field_goals": {
                        "total": 0,
                        "attempts": 1,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 146082
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "1:01",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 151600
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "1:33",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 151611
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "0:48",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 151616
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "0:00",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 151622
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "0:00",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 151629
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "1:28",
                    "field_goals": {
                        "total": 0,
                        "attempts": 1,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 150812
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "0:00",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 153392
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "0:47",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 2,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 153398
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "10:44",
                    "field_goals": {
                        "total": 2,
                        "attempts": 3,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 1,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 2
                    },
                    "assists": 2,
                    "points": 4
                },
                {
                    "game": {
                        "id": 153402
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "0:34",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 153407
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "1:55",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 154289
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "4:01",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 0
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 154305
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "2:15",
                    "field_goals": {
                        "total": 0,
                        "attempts": 1,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 2,
                        "attempts": 3,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 2
                    },
                    "assists": 0,
                    "points": 2
                },
                {
                    "game": {
                        "id": 154293
                    },
                    "team": {
                        "id": 1312
                    },
                    "player": {
                        "id": 6265,
                        "name": "Kuznetsov Sergiy"
                    },
                    "type": "bench",
                    "minutes": "20:09",
                    "field_goals": {
                        "total": 1,
                        "attempts": 1,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 1,
                        "attempts": 2,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 4
                    },
                    "assists": 1,
                    "points": 5
                }
            ]
        }
    """.trimIndent().fromJson<GameStatisticsForPlayersResponseModel>()

    private val season = SeasonModelDomain(name = "2021-2022")
    private val team = TeamModelDomain(id = 22)

    @Before
    fun setUp() {
        Dispatchers.setMain(ioDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `if response by id came with some error`() = runTest {
        Mockito.`when`(apiService.getDataForPlayerById(mockedPlayer.id?.toInt()!!))
            .thenReturn(mockedPlayerErrorResponse)

        val result = repository.getPlayerDataById(mockedPlayer.id?.toInt()!!)

        assert(result is CustomResultModelDomain.Error)
    }

    @Test
    fun `if response by id came perfectly`() = runTest {
        Mockito.`when`(apiService.getDataForPlayerById(mockedPlayer.id?.toInt()!!))
            .thenReturn(mockedPlayerResponse)

        val result = repository.getPlayerDataById(mockedPlayer.id?.toInt()!!)

        assert(result is CustomResultModelDomain.Success)
        assertEquals(result.result, mockedPlayer.toModelDomain())
    }

    @Test
    fun `if response of stats of team players in season came with error`() = runTest {
        Mockito.`when`(
            apiService.getStatisticsForPlayerInSeason(
                season = season.name,
                playerId = mockedPlayer.id?.toInt()!!
            )
        ).thenReturn(mockedPlayerStatisticsErrorResponse)

        val result = repository.requestForPlayersStatisticsInSeason(
            season = season, player = mockedPlayer.toModelDomain()!!
        )

        assert(result is CustomResultModelDomain.Error)
    }

    @Test
    fun `if response of stats of team players in season came perfectly`() = runTest {
        Mockito.`when`(
            apiService.getStatisticsForPlayerInSeason(
                season = season.name,
                playerId = mockedPlayer.id?.toInt()!!
            )
        ).thenReturn(mockedPlayerStatisticsResponse)

        val result = repository.requestForPlayersStatisticsInSeason(
            season = season, player = mockedPlayer.toModelDomain()!!
        )

        assert(result is CustomResultModelDomain.Success)
        assertEquals(result.result?.size, 22)
    }

    @Test
    fun `request for players with query, season, team came with error because of small query length`() =
        runTest {
            val result = repository.getPlayersBySearchQueryAndSeasonAndTeam(
                searchQuery = "ne",
                season = season,
                team = team
            )

            assert(result is CustomResultModelDomain.Error)
        }

    @Test
    fun `request for players with query, season, team came with error because of nullable season`() =
        runTest {
            val result = repository.getPlayersBySearchQueryAndSeasonAndTeam(
                searchQuery = "nets",
                season = null,
                team = team
            )

            assert(result is CustomResultModelDomain.Error)
        }

    @Test
    fun `request for players with query, season, team came with error because of nullable team`() =
        runTest {
            val result = repository.getPlayersBySearchQueryAndSeasonAndTeam(
                searchQuery = "nets",
                season = season,
                team = null
            )

            assert(result is CustomResultModelDomain.Error)
        }

    @Test
    fun `request for players with query, season, team came perfectly`() = runTest {
        Mockito.`when`(
            apiService.getPlayersBySearchQueryAndSeasonAndTeam(
                searchQuery = "nets",
                season = season.name,
                teamId = team.id
            )
        ).thenReturn(mockedPlayerResponse)

        val result = repository.getPlayersBySearchQueryAndSeasonAndTeam(
            searchQuery = "nets",
            season = season,
            team = team
        )

        assertEquals(result.result, mockedPlayerResponse.responseList?.map { it?.toModelDomain() })
    }

    @Test
    fun `request for players with season, team came with error because of nullable season`() =
        runTest {
            val result = repository.getPlayersBySeasonAndTeam(
                season = null,
                team = team
            )

            assert(result is CustomResultModelDomain.Error)
        }

    @Test
    fun `request for players with season, team came with error because of nullable team`() =
        runTest {
            val result = repository.getPlayersBySeasonAndTeam(
                season = season,
                team = null
            )

            assert(result is CustomResultModelDomain.Error)
        }

    @Test
    fun `request for players with season, team came perfectly`() = runTest {
        Mockito.`when`(
            apiService.getPlayersBySeasonAndTeam(
                season = season.name,
                teamId = team.id
            )
        ).thenReturn(mockedPlayerResponse)

        val result = repository.getPlayersBySeasonAndTeam(
            season = season,
            team = team
        )

        assertEquals(result.result, mockedPlayerResponse.responseList?.map { it?.toModelDomain() })
    }

    @Test
    fun `request for players with query came with error because of small query length`() =
        runTest {
            val result = repository.getPlayersBySearchQuery(searchQuery = "ne")

            assert(result is CustomResultModelDomain.Error)
        }

    @Test
    fun `request for players with query came perfectly`() =
        runTest {
            Mockito.`when`(
                apiService.getPlayersBySearchQuery(searchQuery = "nets")
            ).thenReturn(mockedPlayerResponse)

            val result = repository.getPlayersBySearchQuery(searchQuery = "nets")

            assertEquals(
                result.result,
                mockedPlayerResponse.responseList?.map { it?.toModelDomain() })
        }

}