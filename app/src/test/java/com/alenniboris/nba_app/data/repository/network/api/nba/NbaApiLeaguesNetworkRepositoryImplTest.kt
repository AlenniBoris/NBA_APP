package com.alenniboris.nba_app.data.repository.network.api.nba

import com.alenniboris.nba_app.data.source.remote.api.nba.INbaApiService
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.league.LeaguesResponseModel
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiLeaguesNetworkRepository
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
class NbaApiLeaguesNetworkRepositoryImplTest : KoinTest {

    private val testScheduler = TestCoroutineScheduler()
    private val ioDispatcher = StandardTestDispatcher(testScheduler)

    private val testModule = module {
        single<INbaApiService> { Mockito.mock(INbaApiService::class.java) }
        single<IAppDispatchers> {
            Mockito.mock(IAppDispatchers::class.java).apply {
                Mockito.`when`(this.IO).thenReturn(ioDispatcher)
            }
        }
        single<INbaApiLeaguesNetworkRepository> {
            NbaApiLeaguesNetworkRepositoryImpl(
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
    private val repository: INbaApiLeaguesNetworkRepository by inject()

    private val country = CountryModelDomain(id = 51)
    private val mockedLeaguesResponse = """
        {
            "get": "leagues",
            "parameters": {
                "country_id": "51"
            },
            "errors": [],
            "results": 2,
            "response": [
                {
                    "id": 111,
                    "name": "Premier League",
                    "type": "League",
                    "logo": "https:\/\/media.api-sports.io\/basketball\/leagues\/111.png",
                    "country": {
                        "id": 51,
                        "name": "Belarus",
                        "code": "BY",
                        "flag": "https:\/\/media.api-sports.io\/flags\/by.svg"
                    },
                    "seasons": [
                        {
                            "season": "2018-2019",
                            "start": "2018-10-13",
                            "end": "2019-05-19",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2017-2018",
                            "start": "2017-10-13",
                            "end": "2018-05-29",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2016-2017",
                            "start": "2016-10-21",
                            "end": "2017-05-26",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2024-2025",
                            "start": "2024-09-21",
                            "end": "2025-04-15",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": true,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2020-2021",
                            "start": "2020-09-26",
                            "end": "2021-05-26",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2023-2024",
                            "start": "2023-09-23",
                            "end": "2024-05-24",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": true,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2021-2022",
                            "start": "2021-09-25",
                            "end": "2022-05-21",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2019-2020",
                            "start": "2019-09-21",
                            "end": "2020-09-06",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2015-2016",
                            "start": "2015-10-23",
                            "end": "2016-05-18",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2014-2015",
                            "start": "2014-10-31",
                            "end": "2015-05-21",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2013-2014",
                            "start": "2013-10-18",
                            "end": "2014-05-10",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2012-2013",
                            "start": "2012-10-12",
                            "end": "2013-05-18",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2022-2023",
                            "start": "2022-09-17",
                            "end": "2023-05-20",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        }
                    ]
                },
                {
                    "id": 112,
                    "name": "Premier League W",
                    "type": "League",
                    "logo": "https:\/\/media.api-sports.io\/basketball\/leagues\/112.png",
                    "country": {
                        "id": 51,
                        "name": "Belarus",
                        "code": "BY",
                        "flag": "https:\/\/media.api-sports.io\/flags\/by.svg"
                    },
                    "seasons": [
                        {
                            "season": "2021-2022",
                            "start": "2021-09-26",
                            "end": "2022-04-20",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2024-2025",
                            "start": "2024-09-21",
                            "end": "2025-04-13",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": true,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2023-2024",
                            "start": "2023-09-23",
                            "end": "2024-05-22",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": true,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2022-2023",
                            "start": "2022-09-17",
                            "end": "2023-05-03",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2020-2021",
                            "start": "2020-09-27",
                            "end": "2021-04-21",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2017-2018",
                            "start": "2017-10-07",
                            "end": "2018-05-19",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2018-2019",
                            "start": "2018-10-13",
                            "end": "2019-04-14",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2019-2020",
                            "start": "2019-09-29",
                            "end": "2020-03-15",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        }
                    ]
                }
            ]
        }

    """.trimIndent().fromJson<LeaguesResponseModel>()
    private val mockedLeaguesErrorResponse = """
        {
            "get": "leagues",
            "errors": [],
            "results": 2,
            "response": [
                {
                    "id": 111,
                    "name": "Premier League",
                    "type": "League",
                    "logo": "https:\/\/media.api-sports.io\/basketball\/leagues\/111.png",
                    "country": {
                        "id": 51,
                        "name": "Belarus",
                        "code": "BY",
                        "flag": "https:\/\/media.api-sports.io\/flags\/by.svg"
                    },
                    "seasons": [
                        {
                            "season": "2018-2019",
                            "start": "2018-10-13",
                            "end": "2019-05-19",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2017-2018",
                            "start": "2017-10-13",
                            "end": "2018-05-29",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2016-2017",
                            "start": "2016-10-21",
                            "end": "2017-05-26",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2024-2025",
                            "start": "2024-09-21",
                            "end": "2025-04-15",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": true,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2020-2021",
                            "start": "2020-09-26",
                            "end": "2021-05-26",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2023-2024",
                            "start": "2023-09-23",
                            "end": "2024-05-24",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": true,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2021-2022",
                            "start": "2021-09-25",
                            "end": "2022-05-21",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2019-2020",
                            "start": "2019-09-21",
                            "end": "2020-09-06",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2015-2016",
                            "start": "2015-10-23",
                            "end": "2016-05-18",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2014-2015",
                            "start": "2014-10-31",
                            "end": "2015-05-21",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2013-2014",
                            "start": "2013-10-18",
                            "end": "2014-05-10",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2012-2013",
                            "start": "2012-10-12",
                            "end": "2013-05-18",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2022-2023",
                            "start": "2022-09-17",
                            "end": "2023-05-20",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        }
                    ]
                },
                {
                    "id": 112,
                    "name": "Premier League W",
                    "type": "League",
                    "logo": "https:\/\/media.api-sports.io\/basketball\/leagues\/112.png",
                    "country": {
                        "id": 51,
                        "name": "Belarus",
                        "code": "BY",
                        "flag": "https:\/\/media.api-sports.io\/flags\/by.svg"
                    },
                    "seasons": [
                        {
                            "season": "2021-2022",
                            "start": "2021-09-26",
                            "end": "2022-04-20",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2024-2025",
                            "start": "2024-09-21",
                            "end": "2025-04-13",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": true,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2023-2024",
                            "start": "2023-09-23",
                            "end": "2024-05-22",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": true,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2022-2023",
                            "start": "2022-09-17",
                            "end": "2023-05-03",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2020-2021",
                            "start": "2020-09-27",
                            "end": "2021-04-21",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2017-2018",
                            "start": "2017-10-07",
                            "end": "2018-05-19",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2018-2019",
                            "start": "2018-10-13",
                            "end": "2019-04-14",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        },
                        {
                            "season": "2019-2020",
                            "start": "2019-09-29",
                            "end": "2020-03-15",
                            "coverage": {
                                "games": {
                                    "statistics": {
                                        "teams": false,
                                        "players": false
                                    }
                                },
                                "standings": false,
                                "players": false,
                                "odds": false
                            }
                        }
                    ]
                }
            ]
        }

    """.trimIndent().fromJson<LeaguesResponseModel>()

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
        Mockito.`when`(apiService.getLeaguesByCountry(country.id)).thenReturn(mockedLeaguesResponse)
        val res = repository.getLeaguesByCountry(country)
        assert(res is CustomResultModelDomain.Success)
        assertEquals(
            res.result?.size,
            2
        )
        Mockito.verify(apiService).getLeaguesByCountry(country.id)
    }

    @Test
    fun `function works and returns error when json without one paarmeter`() = runTest {
        Mockito.`when`(apiService.getLeaguesByCountry(country.id))
            .thenReturn(mockedLeaguesErrorResponse)
        val res = repository.getLeaguesByCountry(country)
        assert(res is CustomResultModelDomain.Error)
        Mockito.verify(apiService).getLeaguesByCountry(country.id)
    }
}