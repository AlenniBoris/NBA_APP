package com.alenniboris.nba_app.data.repository.network.api.nba

import com.alenniboris.nba_app.data.model.api.nba.team.TeamModelData
import com.alenniboris.nba_app.data.model.api.nba.team.toModelDomain
import com.alenniboris.nba_app.data.source.remote.api.nba.INbaApiTeamService
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.team.TeamResponseModel
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.team.TeamStatisticsResponseModel
import com.alenniboris.nba_app.di.myModules
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiTeamsNetworkRepository
import com.alenniboris.nba_app.domain.utils.GsonUtil.fromJson
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
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class NbaApiTeamsNetworkRepositoryImplTest : KoinTest {

    private val testScheduler = TestCoroutineScheduler()
    private val ioDispatcher = StandardTestDispatcher(testScheduler)

    private val team = """
        {
            "id": 134,
            "name": "Brooklyn Nets",
            "logo": "https:\/\/media.api-sports.io\/basketball\/teams\/134.png",
            "nationnal": false,
            "country": {
                "id": 5,
                "name": "USA",
                "code": "US",
                "flag": "https:\/\/media.api-sports.io\/flags\/us.svg"
            }
        }
    """.trimIndent().fromJson<TeamModelData>()
    private val mockedTeamsResponse = """
        {
            "get": "teams",
            "parameters": {
                "search": "nets"
            },
            "errors": [],
            "results": 9,
            "response": [
                {
                    "id": 134,
                    "name": "Brooklyn Nets",
                    "logo": "https:\/\/media.api-sports.io\/basketball\/teams\/134.png",
                    "nationnal": false,
                    "country": {
                        "id": 5,
                        "name": "USA",
                        "code": "US",
                        "flag": "https:\/\/media.api-sports.io\/flags\/us.svg"
                    }
                },
                {
                    "id": 135,
                    "name": "Charlotte Hornets",
                    "logo": "https:\/\/media.api-sports.io\/basketball\/teams\/135.png",
                    "nationnal": false,
                    "country": {
                        "id": 5,
                        "name": "USA",
                        "code": "US",
                        "flag": "https:\/\/media.api-sports.io\/flags\/us.svg"
                    }
                },
                {
                    "id": 5185,
                    "name": "Donetsk",
                    "logo": "https:\/\/media.api-sports.io\/basketball\/teams\/5185.png",
                    "nationnal": false,
                    "country": {
                        "id": null,
                        "name": null,
                        "code": null,
                        "flag": null
                    }
                },
                {
                    "id": 6367,
                    "name": "Eng Tat Hornets",
                    "logo": "https:\/\/media.api-sports.io\/basketball\/teams\/6367.png",
                    "nationnal": false,
                    "country": {
                        "id": 78,
                        "name": "Singapore",
                        "code": "SG",
                        "flag": "https:\/\/media.api-sports.io\/flags\/sg.svg"
                    }
                },
                {
                    "id": 3535,
                    "name": "Hills Hornets",
                    "logo": "https:\/\/media.api-sports.io\/basketball\/teams\/3535.png",
                    "nationnal": false,
                    "country": {
                        "id": 1,
                        "name": "Australia",
                        "code": "AU",
                        "flag": "https:\/\/media.api-sports.io\/flags\/au.svg"
                    }
                },
                {
                    "id": 3545,
                    "name": "Hills Hornets W",
                    "logo": "https:\/\/media.api-sports.io\/basketball\/teams\/3545.png",
                    "nationnal": false,
                    "country": {
                        "id": 1,
                        "name": "Australia",
                        "code": "AU",
                        "flag": "https:\/\/media.api-sports.io\/flags\/au.svg"
                    }
                },
                {
                    "id": 262,
                    "name": "Long Island Nets",
                    "logo": "https:\/\/media.api-sports.io\/basketball\/teams\/262.png",
                    "nationnal": false,
                    "country": {
                        "id": 5,
                        "name": "USA",
                        "code": "US",
                        "flag": "https:\/\/media.api-sports.io\/flags\/us.svg"
                    }
                },
                {
                    "id": 6653,
                    "name": "Lyndon State Hornets",
                    "logo": "https:\/\/media.api-sports.io\/basketball\/teams\/6653.png",
                    "nationnal": false,
                    "country": {
                        "id": 5,
                        "name": "USA",
                        "code": "US",
                        "flag": "https:\/\/media.api-sports.io\/flags\/us.svg"
                    }
                },
                {
                    "id": 5927,
                    "name": "Sydney Cornets",
                    "logo": "https:\/\/media.api-sports.io\/basketball\/teams\/5927.png",
                    "nationnal": false,
                    "country": {
                        "id": 1,
                        "name": "Australia",
                        "code": "AU",
                        "flag": "https:\/\/media.api-sports.io\/flags\/au.svg"
                    }
                }
            ]
        }
    """.trimIndent().fromJson<TeamResponseModel>()
    private val mockedPlayersStatisticsResponse = """
        {
            "get": "statistics",
            "parameters": {
                "team": "134",
                "league": "12",
                "season": "2021-2022"
            },
            "errors": [],
            "results": 5,
            "response": {
                "country": {
                    "id": 5,
                    "name": "USA",
                    "code": "US",
                    "flag": "https:\/\/media.api-sports.io\/flags\/us.svg"
                },
                "league": {
                    "id": 12,
                    "name": "NBA",
                    "type": "League",
                    "season": "2021-2022",
                    "logo": "https:\/\/media.api-sports.io\/basketball\/leagues\/12.png"
                },
                "team": {
                    "id": 134,
                    "name": "Brooklyn Nets",
                    "logo": "https:\/\/media.api-sports.io\/basketball\/teams\/134.png"
                },
                "games": {
                    "played": {
                        "home": 46,
                        "away": 45,
                        "all": 91
                    },
                    "wins": {
                        "home": {
                            "total": 23,
                            "percentage": "0.500"
                        },
                        "away": {
                            "total": 25,
                            "percentage": "0.556"
                        },
                        "all": {
                            "total": 48,
                            "percentage": "0.527"
                        }
                    },
                    "draws": {
                        "home": {
                            "total": 0,
                            "percentage": "0.000"
                        },
                        "away": {
                            "total": 0,
                            "percentage": "0.000"
                        },
                        "all": {
                            "total": 0,
                            "percentage": "0.000"
                        }
                    },
                    "loses": {
                        "home": {
                            "total": 23,
                            "percentage": "0.500"
                        },
                        "away": {
                            "total": 20,
                            "percentage": "0.444"
                        },
                        "all": {
                            "total": 43,
                            "percentage": "0.473"
                        }
                    }
                },
                "points": {
                    "for": {
                        "total": {
                            "home": 5084,
                            "away": 5178,
                            "all": 10262
                        },
                        "average": {
                            "home": "110.5",
                            "away": "115.1",
                            "all": "112.8"
                        }
                    },
                    "against": {
                        "total": {
                            "home": 5160,
                            "away": 5024,
                            "all": 10184
                        },
                        "average": {
                            "home": "112.2",
                            "away": "111.6",
                            "all": "111.9"
                        }
                    }
                }
            }
        }
    """.trimIndent().fromJson<TeamStatisticsResponseModel>()
    private val season = SeasonModelDomain(name = "2021-2022")
    private val league = LeagueModelDomain(id = 12)
    private val country = CountryModelDomain(id = 12)

    private val testModule = module {
        single<INbaApiTeamService> {
            object : INbaApiTeamService {
                override suspend fun getTeamStatisticsByTeamIdLeagueSeason(
                    teamId: String,
                    leagueId: String,
                    season: String
                ): TeamStatisticsResponseModel {
                    return mockedPlayersStatisticsResponse
                }

                override suspend fun getDataForTeamById(teamId: Int): TeamResponseModel {
                    return mockedTeamsResponse
                }

                override suspend fun getTeamsByCountry(countryId: Int): TeamResponseModel {
                    return mockedTeamsResponse
                }

                override suspend fun getTeamsBySearchQuery(searchQuery: String): TeamResponseModel {
                    return mockedTeamsResponse
                }

                override suspend fun getTeamsBySeasonAndLeague(
                    season: String,
                    leagueId: Int
                ): TeamResponseModel {
                    return mockedTeamsResponse
                }

                override suspend fun getTeamsBySearchQueryAndSeasonAndLeague(
                    searchQuery: String,
                    season: String,
                    leagueId: Int
                ): TeamResponseModel {
                    return mockedTeamsResponse
                }

                override suspend fun getTeamsBySearchQueryAndSeasonAndLeagueAndCountry(
                    searchQuery: String,
                    season: String,
                    leagueId: Int,
                    countryId: Int
                ): TeamResponseModel {
                    return mockedTeamsResponse
                }
            }
        }
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(myModules + testModule)
    }

    private val repository: INbaApiTeamsNetworkRepository by inject()

    @Before
    fun setUp() {
        Dispatchers.setMain(ioDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `if response by id came correctly`() = runTest {
        val res = repository.getDataForTeamById(team.id!!.toInt())
        assert(res is CustomResultModelDomain.Success)
        assertEquals(res.result, team.toModelDomain())
    }

    @Test
    fun `if response for statistics by team,league,season correctly`() = runTest {
        val res = repository.getTeamStatisticsByTeamSeasonLeague(
            team = team.toModelDomain()!!,
            league = league,
            season = season
        )
        assert(res is CustomResultModelDomain.Success)
        assertEquals(res.result != null, true)
    }

    @Test
    fun `if response for statistics by team,league,season error because of nullability`() =
        runTest {
            val res = repository.getTeamStatisticsByTeamSeasonLeague(
                team = team.toModelDomain()!!,
                league = league,
                season = null
            )
            assert(res is CustomResultModelDomain.Error)
        }

    @Test
    fun `if response by query, country,league,season error because of nullability`() = runTest {
        val res = repository.getTeamsBySearchQueryAndSeasonAndLeagueAndCountry(
            searchQuery = "nets",
            country = country,
            league = league,
            season = null
        )
        assert(res is CustomResultModelDomain.Error)
    }

    @Test
    fun `if response by query, country,league,season error because of small query`() = runTest {
        val res = repository.getTeamsBySearchQueryAndSeasonAndLeagueAndCountry(
            searchQuery = "ne",
            country = country,
            league = league,
            season = season
        )
        assert(res is CustomResultModelDomain.Error)
    }

    @Test
    fun `if response by query, country,league,season came correctly`() = runTest {
        val res = repository.getTeamsBySearchQueryAndSeasonAndLeagueAndCountry(
            searchQuery = "nets",
            country = country,
            league = league,
            season = season
        )
        assert(res is CustomResultModelDomain.Success)
        assertEquals(res.result?.size, 8)
    }

    @Test
    fun `if response by query,league,season error because of small query`() = runTest {
        val res = repository.getTeamsBySearchQueryAndSeasonAndLeague(
            searchQuery = "ne",
            league = league,
            season = season
        )
        assert(res is CustomResultModelDomain.Error)
    }

    @Test
    fun `if response by query,league,season came correctly`() = runTest {
        val res = repository.getTeamsBySearchQueryAndSeasonAndLeague(
            searchQuery = "nets",
            league = league,
            season = season
        )
        assert(res is CustomResultModelDomain.Success)
        assertEquals(res.result?.size, 8)
    }

    @Test
    fun `if response by league,season error because of nullability`() = runTest {
        val res = repository.getTeamsBySeasonAndLeague(
            league = league,
            season = null
        )
        assert(res is CustomResultModelDomain.Error)
    }

    @Test
    fun `if response by league,season came correctly`() = runTest {
        val res = repository.getTeamsBySeasonAndLeague(
            league = league,
            season = season
        )
        assert(res is CustomResultModelDomain.Success)
        assertEquals(res.result?.size, 8)
    }

    @Test
    fun `if response by query,league,season error because of nullability`() = runTest {
        val res = repository.getTeamsBySearchQueryAndSeasonAndLeague(
            searchQuery = "nets",
            league = league,
            season = null
        )
        assert(res is CustomResultModelDomain.Error)
    }

    @Test
    fun `if response by quer error because of small query`() = runTest {
        val res = repository.getTeamsBySearchQuery(
            searchQuery = "ne"
        )
        assert(res is CustomResultModelDomain.Error)
    }

    @Test
    fun `if response by query came`() = runTest {
        val res = repository.getTeamsBySearchQuery(
            searchQuery = "nets"
        )
        assert(res is CustomResultModelDomain.Success)
        assertEquals(res.result?.size, 8)
    }

    @Test
    fun `if response by country error because of nullability`() = runTest {
        val res = repository.getTeamsByCountry(
            country = null
        )
        assert(res is CustomResultModelDomain.Error)
    }

    @Test
    fun `if response by country came`() = runTest {
        val res = repository.getTeamsByCountry(
            country = country
        )

        assert(res is CustomResultModelDomain.Success)
        assertEquals(res.result?.size, 8)
    }

}