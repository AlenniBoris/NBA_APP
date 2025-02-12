package com.alenniboris.nba_app.data.repository.network.api.nba

import com.alenniboris.nba_app.data.model.api.nba.game.GameModelData
import com.alenniboris.nba_app.data.model.api.nba.game.toModelDomain
import com.alenniboris.nba_app.data.source.remote.api.nba.INbaApiGameService
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.game.GameResponseModel
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.game.GameStatisticsForPlayersResponseModel
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.game.GameStatisticsForTeamsResponseModel
import com.alenniboris.nba_app.di.myModules
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiGamesNetworkRepository
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
import java.util.Date


@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class NbaApiGamesNetworkRepositoryImplTest : KoinTest {

    private val ioDispatcher = StandardTestDispatcher()

    private val game = """
        {
            "id": 414628,
            "date": "2025-02-11T00:00:00+00:00",
            "time": "00:00",
            "timestamp": 1739232000,
            "timezone": "UTC",
            "stage": null,
            "week": null,
            "venue": "Rocket Mortgage FieldHouse",
            "status": {
                "long": "Game Finished",
                "short": "FT",
                "timer": null
            },
            "league": {
                "id": 12,
                "name": "NBA",
                "type": "League",
                "season": "2024-2025",
                "logo": "https:\/\/media.api-sports.io\/basketball\/leagues\/12.png"
            },
            "country": {
                "id": 5,
                "name": "USA",
                "code": "US",
                "flag": "https:\/\/media.api-sports.io\/flags\/us.svg"
            },
            "teams": {
                "home": {
                    "id": 137,
                    "name": "Cleveland Cavaliers",
                    "logo": "https:\/\/media.api-sports.io\/basketball\/teams\/137.png"
                },
                "away": {
                    "id": 149,
                    "name": "Minnesota Timberwolves",
                    "logo": "https:\/\/media.api-sports.io\/basketball\/teams\/149.png"
                }
            },
            "scores": {
                "home": {
                    "quarter_1": 30,
                    "quarter_2": 36,
                    "quarter_3": 38,
                    "quarter_4": 24,
                    "over_time": null,
                    "total": 128
                },
                "away": {
                    "quarter_1": 12,
                    "quarter_2": 32,
                    "quarter_3": 34,
                    "quarter_4": 29,
                    "over_time": null,
                    "total": 107
                }
            }
        }
    """.trimIndent().fromJson<GameModelData>()
    private val mockedGameResponse = """
        {
            "get": "games",
            "parameters": {
                "id": "414628"
            },
            "errors": [],
            "results": 25,
            "response": [
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 137
                    },
                    "player": {
                        "id": 507,
                        "name": "Hunter De&apos;Andre"
                    },
                    "type": "starters",
                    "minutes": "22:57",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 3,
                        "attempts": 4,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 3,
                        "attempts": 4,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 1
                    },
                    "assists": 0,
                    "points": 12
                }
            ]
        }
    """.trimIndent().fromJson<GameResponseModel>()
    private val mockedPlayersStatisticsResponse = """
        {
            "get": "games",
            "parameters": {
                "id": "414628"
            },
            "errors": [],
            "results": 25,
            "response": [
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 137
                    },
                    "player": {
                        "id": 507,
                        "name": "Hunter De&apos;Andre"
                    },
                    "type": "starters",
                    "minutes": "22:57",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 3,
                        "attempts": 4,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 3,
                        "attempts": 4,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 1
                    },
                    "assists": 0,
                    "points": 12
                },
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 137
                    },
                    "player": {
                        "id": 584,
                        "name": "Mobley Evan"
                    },
                    "type": "starters",
                    "minutes": "31:36",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 1,
                        "attempts": 4,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 3,
                        "attempts": 5,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 10
                    },
                    "assists": 6,
                    "points": 28
                },
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 137
                    },
                    "player": {
                        "id": 582,
                        "name": "Allen Jarrett"
                    },
                    "type": "starters",
                    "minutes": "26:50",
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
                        "total": 2,
                        "attempts": 2,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 13
                    },
                    "assists": 3,
                    "points": 14
                },
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 137
                    },
                    "player": {
                        "id": 586,
                        "name": "Garland Darius"
                    },
                    "type": "starters",
                    "minutes": "26:50",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 4,
                        "attempts": 6,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 1,
                        "attempts": 1,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 1
                    },
                    "assists": 3,
                    "points": 17
                },
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 137
                    },
                    "player": {
                        "id": 589,
                        "name": "Mitchell Donovan"
                    },
                    "type": "starters",
                    "minutes": "32:21",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 2,
                        "attempts": 5,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 1,
                        "attempts": 3,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 3
                    },
                    "assists": 8,
                    "points": 23
                },
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 137
                    },
                    "player": {
                        "id": 594,
                        "name": "Bates Emoni"
                    },
                    "type": "bench",
                    "minutes": "4:45",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 1,
                        "attempts": 4,
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
                    "points": 3
                },
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 137
                    },
                    "player": {
                        "id": 587,
                        "name": "Jerome Ty"
                    },
                    "type": "bench",
                    "minutes": "24:12",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 2,
                        "attempts": 6,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 5,
                        "attempts": 5,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 2
                    },
                    "assists": 4,
                    "points": 15
                },
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 137
                    },
                    "player": {
                        "id": 588,
                        "name": "Merrill Sam"
                    },
                    "type": "bench",
                    "minutes": "28:47",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 3,
                        "attempts": 8,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 6
                    },
                    "assists": 0,
                    "points": 9
                },
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 137
                    },
                    "player": {
                        "id": 590,
                        "name": "Porter Craig"
                    },
                    "type": "bench",
                    "minutes": "16:33",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 4,
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
                    "assists": 1,
                    "points": 2
                },
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 137
                    },
                    "player": {
                        "id": 600,
                        "name": "Thompson Tristan"
                    },
                    "type": "bench",
                    "minutes": "4:45",
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
                        "total": 5
                    },
                    "assists": 1,
                    "points": 0
                },
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 137
                    },
                    "player": {
                        "id": 51419,
                        "name": "J. Tyson"
                    },
                    "type": "bench",
                    "minutes": "13:59",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 1,
                        "attempts": 1,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 5
                    },
                    "assists": 1,
                    "points": 5
                },
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 137
                    },
                    "player": {
                        "id": 15,
                        "name": "Travers Luke"
                    },
                    "type": "bench",
                    "minutes": "6:25",
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
                        "total": 1
                    },
                    "assists": 0,
                    "points": 0
                },
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 149
                    },
                    "player": {
                        "id": 826,
                        "name": "McDaniels Jaden"
                    },
                    "type": "starters",
                    "minutes": "32:04",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 2,
                        "attempts": 4,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 3
                    },
                    "assists": 1,
                    "points": 10
                },
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 149
                    },
                    "player": {
                        "id": 816,
                        "name": "Reid Naz"
                    },
                    "type": "starters",
                    "minutes": "35:16",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 1,
                        "attempts": 6,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 1,
                        "attempts": 2,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 6
                    },
                    "assists": 4,
                    "points": 10
                },
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 149
                    },
                    "player": {
                        "id": 815,
                        "name": "Gobert Rudy"
                    },
                    "type": "starters",
                    "minutes": "26:47",
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
                        "total": 4,
                        "attempts": 5,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 8
                    },
                    "assists": 1,
                    "points": 12
                },
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 149
                    },
                    "player": {
                        "id": 818,
                        "name": "Alexander-Walker Nickeil"
                    },
                    "type": "starters",
                    "minutes": "26:17",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 4,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 2,
                        "attempts": 2,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 6
                    },
                    "assists": 2,
                    "points": 6
                },
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 149
                    },
                    "player": {
                        "id": 820,
                        "name": "Edwards Anthony"
                    },
                    "type": "starters",
                    "minutes": "36:30",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 8,
                        "attempts": 15,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 10,
                        "attempts": 13,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 6
                    },
                    "assists": 1,
                    "points": 44
                },
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 149
                    },
                    "player": {
                        "id": 51427,
                        "name": "R. Dillingham"
                    },
                    "type": "bench",
                    "minutes": "22:41",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 1,
                        "attempts": 3,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 2,
                        "attempts": 2,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 2
                    },
                    "assists": 3,
                    "points": 13
                },
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 149
                    },
                    "player": {
                        "id": 51422,
                        "name": "J. Clark"
                    },
                    "type": "bench",
                    "minutes": "14:30",
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
                    "points": 4
                },
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 149
                    },
                    "player": {
                        "id": 814,
                        "name": "Garza Luka"
                    },
                    "type": "bench",
                    "minutes": "3:16",
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
                        "id": 414628
                    },
                    "team": {
                        "id": 149
                    },
                    "player": {
                        "id": 903,
                        "name": "Ingles Joe"
                    },
                    "type": "bench",
                    "minutes": "15:04",
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
                        "total": 1
                    },
                    "assists": 2,
                    "points": 0
                },
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 149
                    },
                    "player": {
                        "id": 827,
                        "name": "Miller Leonard"
                    },
                    "type": "bench",
                    "minutes": "4:45",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 2,
                        "percentage": null
                    },
                    "freethrows_goals": {
                        "total": 2,
                        "attempts": 2,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 1
                    },
                    "assists": 0,
                    "points": 4
                },
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 149
                    },
                    "player": {
                        "id": 51433,
                        "name": "T. Newton"
                    },
                    "type": "bench",
                    "minutes": "4:45",
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
                        "total": 3
                    },
                    "assists": 1,
                    "points": 0
                },
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 149
                    },
                    "player": {
                        "id": 828,
                        "name": "Minott Josh"
                    },
                    "type": "bench",
                    "minutes": "4:45",
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
                        "total": 2,
                        "attempts": 2,
                        "percentage": null
                    },
                    "rebounds": {
                        "total": 3
                    },
                    "assists": 1,
                    "points": 2
                },
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 149
                    },
                    "player": {
                        "id": 51426,
                        "name": "J. T. Shannon"
                    },
                    "type": "bench",
                    "minutes": "13:20",
                    "field_goals": {
                        "total": 0,
                        "attempts": 0,
                        "percentage": null
                    },
                    "threepoint_goals": {
                        "total": 0,
                        "attempts": 2,
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
                    "assists": 1,
                    "points": 2
                }
            ]
        }
    """.trimIndent().fromJson<GameStatisticsForPlayersResponseModel>()
    private val mockedTeamStatisticsResponse = """
        {
            "get": "games",
            "parameters": {
                "id": "414628"
            },
            "errors": [],
            "results": 2,
            "response": [
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 137
                    },
                    "field_goals": {
                        "total": 48,
                        "attempts": 94,
                        "percentage": 51
                    },
                    "threepoint_goals": {
                        "total": 17,
                        "attempts": 43,
                        "percentage": 39
                    },
                    "freethrows_goals": {
                        "total": 15,
                        "attempts": 20,
                        "percentage": 75
                    },
                    "rebounds": {
                        "total": 49,
                        "offence": 14,
                        "defense": 35
                    },
                    "assists": 27,
                    "steals": 10,
                    "blocks": 5,
                    "turnovers": 16,
                    "personal_fouls": 24
                },
                {
                    "game": {
                        "id": 414628
                    },
                    "team": {
                        "id": 149
                    },
                    "field_goals": {
                        "total": 36,
                        "attempts": 90,
                        "percentage": 40
                    },
                    "threepoint_goals": {
                        "total": 12,
                        "attempts": 39,
                        "percentage": 30
                    },
                    "freethrows_goals": {
                        "total": 23,
                        "attempts": 30,
                        "percentage": 76
                    },
                    "rebounds": {
                        "total": 41,
                        "offence": 15,
                        "defense": 26
                    },
                    "assists": 17,
                    "steals": 10,
                    "blocks": 6,
                    "turnovers": 16,
                    "personal_fouls": 19
                }
            ]
        }
    """.trimIndent().fromJson<GameStatisticsForTeamsResponseModel>()
    private val season = SeasonModelDomain(name = "2021-2022")
    private val nullSeason: SeasonModelDomain? = null
    private val league = LeagueModelDomain(id = 12)

    private val testModule = module {
        single<INbaApiGameService> {
            object : INbaApiGameService {
                override suspend fun getGamesByDate(date: String): GameResponseModel {
                    return mockedGameResponse
                }

                override suspend fun getGamesBySeasonAndLeague(
                    leagueId: Int,
                    season: String
                ): GameResponseModel {
                    return mockedGameResponse
                }

                override suspend fun getGameStatisticsForTeamsByGameId(gameId: Int): GameStatisticsForTeamsResponseModel {
                    return mockedTeamStatisticsResponse
                }

                override suspend fun getGameStatisticsForPlayersByGameId(gameId: Int): GameStatisticsForPlayersResponseModel {
                    return mockedPlayersStatisticsResponse
                }

                override suspend fun getDataForGameById(gameId: Int): GameResponseModel {
                    return mockedGameResponse
                }
            }
        }
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(myModules + testModule)
    }

    private val repository: INbaApiGamesNetworkRepository by inject()

    @Before
    fun setUp() {
        Dispatchers.setMain(ioDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `if response for request came with some error`() = runTest {
        val res = repository.getGameDataById(game.id!!.toInt())
        assert(res is CustomResultModelDomain.Error)
        assertEquals(res.result, null)
    }

    @Test
    fun `getting games by id returns correctly`() = runTest {
        val res = repository.getGameDataById(414628)
        assertEquals(res.result, game.toModelDomain())
    }

    @Test
    fun `statistics for players in game returns correctly`() = runTest {
        val res = repository.getGameStatisticsForPlayersInGame(GameModelDomain(414628))
        assert(res is CustomResultModelDomain.Success)
    }

    @Test
    fun `statistics for teams in game returns correctly`() = runTest {
        val res = repository.getTeamsStatisticsInGame(GameModelDomain(414628))
        assert(res is CustomResultModelDomain.Success)
    }

    @Test
    fun `getting games by date returns correctly`() = runTest {
        val res = repository.getGamesByDate(Date(1737244800000))
        assert(res is CustomResultModelDomain.Success)
    }

    @Test
    fun `error goes when getting by season and league when something is nullable`() = runTest {
        val res = repository.getGamesBySeasonAndLeague(nullSeason, league)
        assert(res is CustomResultModelDomain.Error)
    }

    @Test
    fun `getting games by season and league returns correctly`() = runTest {
        val res = repository.getGamesBySeasonAndLeague(season, league)
        assert(res is CustomResultModelDomain.Success)
        assertEquals(
            mockedGameResponse.responseList?.mapNotNull { it?.toModelDomain() },
            res.result
        )
    }

}