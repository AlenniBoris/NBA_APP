package com.alenniboris.nba_app.data.repository.network.api.nba

import com.alenniboris.nba_app.data.model.api.nba.country.toModelDomain
import com.alenniboris.nba_app.data.source.remote.api.nba.INbaApiCountriesService
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.country.CountriesResponseModel
import com.alenniboris.nba_app.di.myModules
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiCountriesNetworkRepository
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
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

@OptIn(ExperimentalCoroutinesApi::class)
class NbaApiCountriesNetworkRepositoryImplTest : KoinTest {

    private val ioDispatcher = StandardTestDispatcher()
    val mockedCountriesResponse = """
            {
                "get": "countries",
                "parameters": [],
                "errors": [],
                "results": 79,
                "response": [
                    {
                        "id": 1,
                        "name": "Australia",
                        "code": "AU",
                        "flag": "https:\/\/media.api-sports.io\/flags\/au.svg"
                    },
                    {
                        "id": 2,
                        "name": "Asia",
                        "code": null,
                        "flag": null
                    },
                    {
                        "id": 3,
                        "name": "Austria",
                        "code": "AT",
                        "flag": "https:\/\/media.api-sports.io\/flags\/at.svg"
                    },
                    {
                        "id": 4,
                        "name": "France",
                        "code": "FR",
                        "flag": "https:\/\/media.api-sports.io\/flags\/fr.svg"
                    },
                    {
                        "id": 5,
                        "name": "USA",
                        "code": "US",
                        "flag": "https:\/\/media.api-sports.io\/flags\/us.svg"
                    },
                    {
                        "id": 6,
                        "name": "Argentina",
                        "code": "AR",
                        "flag": "https:\/\/media.api-sports.io\/flags\/ar.svg"
                    },
                    {
                        "id": 7,
                        "name": "Belgium",
                        "code": "BE",
                        "flag": "https:\/\/media.api-sports.io\/flags\/be.svg"
                    },
                    {
                        "id": 8,
                        "name": "Brazil",
                        "code": "BR",
                        "flag": "https:\/\/media.api-sports.io\/flags\/br.svg"
                    },
                    {
                        "id": 9,
                        "name": "Canada",
                        "code": "CA",
                        "flag": "https:\/\/media.api-sports.io\/flags\/ca.svg"
                    },
                    {
                        "id": 10,
                        "name": "Croatia",
                        "code": "HR",
                        "flag": "https:\/\/media.api-sports.io\/flags\/hr.svg"
                    },
                    {
                        "id": 11,
                        "name": "China",
                        "code": "CN",
                        "flag": "https:\/\/media.api-sports.io\/flags\/cn.svg"
                    },
                    {
                        "id": 12,
                        "name": "Czech Republic",
                        "code": "CZ",
                        "flag": "https:\/\/media.api-sports.io\/flags\/cz.svg"
                    },
                    {
                        "id": 13,
                        "name": "Denmark",
                        "code": "DK",
                        "flag": "https:\/\/media.api-sports.io\/flags\/dk.svg"
                    },
                    {
                        "id": 14,
                        "name": "Estonia",
                        "code": "EE",
                        "flag": "https:\/\/media.api-sports.io\/flags\/ee.svg"
                    },
                    {
                        "id": 15,
                        "name": "Finland",
                        "code": "FI",
                        "flag": "https:\/\/media.api-sports.io\/flags\/fi.svg"
                    },
                    {
                        "id": 16,
                        "name": "Georgia",
                        "code": "GE",
                        "flag": "https:\/\/media.api-sports.io\/flags\/ge.svg"
                    },
                    {
                        "id": 17,
                        "name": "Germany",
                        "code": "DE",
                        "flag": "https:\/\/media.api-sports.io\/flags\/de.svg"
                    },
                    {
                        "id": 18,
                        "name": "Greece",
                        "code": "GR",
                        "flag": "https:\/\/media.api-sports.io\/flags\/gr.svg"
                    },
                    {
                        "id": 19,
                        "name": "Hungary",
                        "code": "HU",
                        "flag": "https:\/\/media.api-sports.io\/flags\/hu.svg"
                    },
                    {
                        "id": 20,
                        "name": "Iceland",
                        "code": "IS",
                        "flag": "https:\/\/media.api-sports.io\/flags\/is.svg"
                    },
                    {
                        "id": 21,
                        "name": "Ireland",
                        "code": "IE",
                        "flag": "https:\/\/media.api-sports.io\/flags\/ie.svg"
                    },
                    {
                        "id": 22,
                        "name": "Israel",
                        "code": "IL",
                        "flag": "https:\/\/media.api-sports.io\/flags\/il.svg"
                    },
                    {
                        "id": 23,
                        "name": "Italy",
                        "code": "IT",
                        "flag": "https:\/\/media.api-sports.io\/flags\/it.svg"
                    },
                    {
                        "id": 24,
                        "name": "Japan",
                        "code": "JP",
                        "flag": "https:\/\/media.api-sports.io\/flags\/jp.svg"
                    },
                    {
                        "id": 25,
                        "name": "Kazakhstan",
                        "code": "KZ",
                        "flag": "https:\/\/media.api-sports.io\/flags\/kz.svg"
                    },
                    {
                        "id": 26,
                        "name": "Kosovo",
                        "code": "XK",
                        "flag": "https:\/\/media.api-sports.io\/flags\/xk.svg"
                    },
                    {
                        "id": 27,
                        "name": "Lithuania",
                        "code": "LT",
                        "flag": "https:\/\/media.api-sports.io\/flags\/lt.svg"
                    },
                    {
                        "id": 28,
                        "name": "Mexico",
                        "code": "MX",
                        "flag": "https:\/\/media.api-sports.io\/flags\/mx.svg"
                    },
                    {
                        "id": 29,
                        "name": "Montenegro",
                        "code": "ME",
                        "flag": "https:\/\/media.api-sports.io\/flags\/me.svg"
                    },
                    {
                        "id": 30,
                        "name": "Netherlands",
                        "code": "NL",
                        "flag": "https:\/\/media.api-sports.io\/flags\/nl.svg"
                    },
                    {
                        "id": 31,
                        "name": "New Zealand",
                        "code": "NZ",
                        "flag": "https:\/\/media.api-sports.io\/flags\/nz.svg"
                    },
                    {
                        "id": 32,
                        "name": "Macedonia",
                        "code": "MK",
                        "flag": "https:\/\/media.api-sports.io\/flags\/mk.svg"
                    },
                    {
                        "id": 33,
                        "name": "Norway",
                        "code": "NO",
                        "flag": "https:\/\/media.api-sports.io\/flags\/no.svg"
                    },
                    {
                        "id": 34,
                        "name": "Poland",
                        "code": "PL",
                        "flag": "https:\/\/media.api-sports.io\/flags\/pl.svg"
                    },
                    {
                        "id": 35,
                        "name": "Portugal",
                        "code": "PT",
                        "flag": "https:\/\/media.api-sports.io\/flags\/pt.svg"
                    },
                    {
                        "id": 36,
                        "name": "Puerto Rico",
                        "code": "PR",
                        "flag": "https:\/\/media.api-sports.io\/flags\/pr.svg"
                    },
                    {
                        "id": 37,
                        "name": "Qatar",
                        "code": "QA",
                        "flag": "https:\/\/media.api-sports.io\/flags\/qa.svg"
                    },
                    {
                        "id": 38,
                        "name": "Romania",
                        "code": "RO",
                        "flag": "https:\/\/media.api-sports.io\/flags\/ro.svg"
                    },
                    {
                        "id": 39,
                        "name": "Russia",
                        "code": "RU",
                        "flag": "https:\/\/media.api-sports.io\/flags\/ru.svg"
                    },
                    {
                        "id": 40,
                        "name": "Saudi Arabia",
                        "code": "SA",
                        "flag": "https:\/\/media.api-sports.io\/flags\/sa.svg"
                    },
                    {
                        "id": 41,
                        "name": "Serbia",
                        "code": "RS",
                        "flag": "https:\/\/media.api-sports.io\/flags\/rs.svg"
                    },
                    {
                        "id": 42,
                        "name": "Slovakia",
                        "code": "SK",
                        "flag": "https:\/\/media.api-sports.io\/flags\/sk.svg"
                    },
                    {
                        "id": 43,
                        "name": "Slovenia",
                        "code": "SI",
                        "flag": "https:\/\/media.api-sports.io\/flags\/si.svg"
                    },
                    {
                        "id": 44,
                        "name": "South Korea",
                        "code": "KR",
                        "flag": "https:\/\/media.api-sports.io\/flags\/kr.svg"
                    },
                    {
                        "id": 45,
                        "name": "Spain",
                        "code": "ES",
                        "flag": "https:\/\/media.api-sports.io\/flags\/es.svg"
                    },
                    {
                        "id": 46,
                        "name": "Sweden",
                        "code": "SE",
                        "flag": "https:\/\/media.api-sports.io\/flags\/se.svg"
                    },
                    {
                        "id": 47,
                        "name": "Switzerland",
                        "code": "CH",
                        "flag": "https:\/\/media.api-sports.io\/flags\/ch.svg"
                    },
                    {
                        "id": 48,
                        "name": "Turkey",
                        "code": "TR",
                        "flag": "https:\/\/media.api-sports.io\/flags\/tr.svg"
                    },
                    {
                        "id": 49,
                        "name": "Ukraine",
                        "code": "UA",
                        "flag": "https:\/\/media.api-sports.io\/flags\/ua.svg"
                    },
                    {
                        "id": 50,
                        "name": "United Kingdom",
                        "code": "GB",
                        "flag": "https:\/\/media.api-sports.io\/flags\/gb.svg"
                    },
                    {
                        "id": 51,
                        "name": "Belarus",
                        "code": "BY",
                        "flag": "https:\/\/media.api-sports.io\/flags\/by.svg"
                    },
                    {
                        "id": 52,
                        "name": "Bulgaria",
                        "code": "BG",
                        "flag": "https:\/\/media.api-sports.io\/flags\/bg.svg"
                    },
                    {
                        "id": 53,
                        "name": "Chile",
                        "code": "CL",
                        "flag": "https:\/\/media.api-sports.io\/flags\/cl.svg"
                    },
                    {
                        "id": 54,
                        "name": "Cyprus",
                        "code": "CY",
                        "flag": "https:\/\/media.api-sports.io\/flags\/cy.svg"
                    },
                    {
                        "id": 55,
                        "name": "Europe",
                        "code": " ",
                        "flag": "https:\/\/media.api-sports.io\/flags\/ .svg"
                    },
                    {
                        "id": 56,
                        "name": "Bahrain",
                        "code": "BH",
                        "flag": "https:\/\/media.api-sports.io\/flags\/bh.svg"
                    },
                    {
                        "id": 57,
                        "name": "Bosnia-and-Herzegovina",
                        "code": "BA",
                        "flag": "https:\/\/media.api-sports.io\/flags\/ba.svg"
                    },
                    {
                        "id": 58,
                        "name": "Indonesia",
                        "code": "ID",
                        "flag": "https:\/\/media.api-sports.io\/flags\/id.svg"
                    },
                    {
                        "id": 59,
                        "name": "Latvia",
                        "code": "LV",
                        "flag": "https:\/\/media.api-sports.io\/flags\/lv.svg"
                    },
                    {
                        "id": 60,
                        "name": "Philippines",
                        "code": "PH",
                        "flag": "https:\/\/media.api-sports.io\/flags\/ph.svg"
                    },
                    {
                        "id": 61,
                        "name": "Venezuela",
                        "code": "VE",
                        "flag": "https:\/\/media.api-sports.io\/flags\/ve.svg"
                    },
                    {
                        "id": 62,
                        "name": "World",
                        "code": " ",
                        "flag": "https:\/\/media.api-sports.io\/flags\/ .svg"
                    },
                    {
                        "id": 63,
                        "name": "Uruguay",
                        "code": "UY",
                        "flag": "https:\/\/media.api-sports.io\/flags\/uy.svg"
                    },
                    {
                        "id": 64,
                        "name": "Colombia",
                        "code": "CO",
                        "flag": "https:\/\/media.api-sports.io\/flags\/co.svg"
                    },
                    {
                        "id": 65,
                        "name": "Iran",
                        "code": "IR",
                        "flag": "https:\/\/media.api-sports.io\/flags\/ir.svg"
                    },
                    {
                        "id": 66,
                        "name": "Luxembourg",
                        "code": "LU",
                        "flag": "https:\/\/media.api-sports.io\/flags\/lu.svg"
                    },
                    {
                        "id": 67,
                        "name": "Paraguay",
                        "code": "PY",
                        "flag": "https:\/\/media.api-sports.io\/flags\/py.svg"
                    },
                    {
                        "id": 68,
                        "name": "Taiwan",
                        "code": "TW",
                        "flag": "https:\/\/media.api-sports.io\/flags\/tw.svg"
                    },
                    {
                        "id": 69,
                        "name": "Thailand",
                        "code": "TH",
                        "flag": "https:\/\/media.api-sports.io\/flags\/th.svg"
                    },
                    {
                        "id": 70,
                        "name": "Vietnam",
                        "code": "VN",
                        "flag": "https:\/\/media.api-sports.io\/flags\/vn.svg"
                    },
                    {
                        "id": 71,
                        "name": "Africa",
                        "code": null,
                        "flag": null
                    },
                    {
                        "id": 72,
                        "name": "Bolivia",
                        "code": "BO",
                        "flag": "https:\/\/media.api-sports.io\/flags\/bo.svg"
                    },
                    {
                        "id": 73,
                        "name": "Dominican-Republic",
                        "code": "DO",
                        "flag": "https:\/\/media.api-sports.io\/flags\/do.svg"
                    },
                    {
                        "id": 74,
                        "name": "Tajikistan",
                        "code": "TJ",
                        "flag": "https:\/\/media.api-sports.io\/flags\/tj.svg"
                    },
                    {
                        "id": 75,
                        "name": "Turkmenistan",
                        "code": "TM",
                        "flag": "https:\/\/media.api-sports.io\/flags\/tm.svg"
                    },
                    {
                        "id": 76,
                        "name": "Albania",
                        "code": "AL",
                        "flag": "https:\/\/media.api-sports.io\/flags\/al.svg"
                    },
                    {
                        "id": 77,
                        "name": "Lebanon",
                        "code": "LB",
                        "flag": "https:\/\/media.api-sports.io\/flags\/lb.svg"
                    },
                    {
                        "id": 78,
                        "name": "Singapore",
                        "code": "SG",
                        "flag": "https:\/\/media.api-sports.io\/flags\/sg.svg"
                    },
                    {
                        "id": 79,
                        "name": "South-America",
                        "code": null,
                        "flag": null
                    }
                ]
            }
        """.trimIndent().fromJson<CountriesResponseModel>()

    private val testModule = module {
        single<INbaApiCountriesService> {
            object : INbaApiCountriesService {
                override suspend fun getAllCountries(): CountriesResponseModel {
                    return mockedCountriesResponse
                }
            }
        }
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(myModules + testModule)
    }

    private val repository: INbaApiCountriesNetworkRepository by inject()

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
        val res = repository.getAllCountries()
        assert(res is CustomResultModelDomain.Success)
        assertEquals(
            res.result,
            mockedCountriesResponse.responseList?.mapNotNull { it?.toModelDomain() }
        )
    }

}