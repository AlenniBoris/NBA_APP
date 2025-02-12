package com.alenniboris.nba_app.data.source.remote.api.nba

import android.app.Application
import com.alenniboris.nba_app.BuildConfig
import com.alenniboris.nba_app.data.source.remote.MyRetrofit
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.league.LeaguesResponseModel
import com.andretietz.retrofit.ResponseCache
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface INbaApiLeaguesService {

    @GET(NbaApiValues.LEAGUE_REQUEST)
    @ResponseCache(60, TimeUnit.MINUTES)
    @Headers(NbaApiValues.API_HEADER + BuildConfig.API_KEY)
    suspend fun getLeaguesByCountry(
        @Query(NbaApiValues.COUNTRY_ID_PARAMETER) countryId: Int
    ): LeaguesResponseModel

    companion object {
        fun get(
            apl: Application
        ) = MyRetrofit().create<INbaApiLeaguesService>(
            apl = apl,
            url = NbaApiValues.BASE_URL_NBA_API
        )
    }

}