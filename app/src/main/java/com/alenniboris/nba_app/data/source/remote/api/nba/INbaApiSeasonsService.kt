package com.alenniboris.nba_app.data.source.remote.api.nba

import android.app.Application
import com.alenniboris.nba_app.BuildConfig
import com.alenniboris.nba_app.data.source.remote.MyRetrofit
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.season.SeasonsResponseModel
import com.andretietz.retrofit.ResponseCache
import retrofit2.http.GET
import retrofit2.http.Headers
import java.util.concurrent.TimeUnit

interface INbaApiSeasonsService {

    @GET(NbaApiValues.SEASON_REQUEST)
    @ResponseCache(60, TimeUnit.MINUTES)
    @Headers(NbaApiValues.API_HEADER + BuildConfig.API_KEY)
    suspend fun getAllSeasons(): SeasonsResponseModel

    companion object {
        fun get(
            apl: Application
        ) = MyRetrofit().create<INbaApiSeasonsService>(
            apl = apl,
            url = NbaApiValues.BASE_URL_NBA_API
        )
    }

}