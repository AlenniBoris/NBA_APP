package com.alenniboris.nba_app.data.source.remote.api.nba

import android.app.Application
import com.alenniboris.nba_app.BuildConfig
import com.alenniboris.nba_app.data.source.remote.MyRetrofit
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.country.CountriesResponseModel
import com.andretietz.retrofit.ResponseCache
import retrofit2.http.GET
import retrofit2.http.Headers
import java.util.concurrent.TimeUnit

interface INbaApiCountriesService {

    @GET(NbaApiValues.COUNTRY_REQUEST)
    @ResponseCache(60, TimeUnit.MINUTES)
    @Headers(NbaApiValues.API_HEADER + BuildConfig.API_KEY)
    suspend fun getAllCountries(): CountriesResponseModel

    companion object {
        fun get(
            apl: Application
        ) = MyRetrofit().create<INbaApiCountriesService>(
            apl = apl,
            url = NbaApiValues.BASE_URL_NBA_API
        )
    }

}