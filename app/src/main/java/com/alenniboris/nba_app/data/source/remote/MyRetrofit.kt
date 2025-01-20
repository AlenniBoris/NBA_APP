package com.alenniboris.nba_app.data.source.remote

import android.app.Application
import com.alenniboris.nba_app.BuildConfig
import com.alenniboris.nba_app.data.source.remote.OkHttpCache.setOkhttpCache
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyRetrofit {

    inline fun <reified T> create(
        apl: Application,
        url: String,
        isSafe: Boolean = !BuildConfig.DEBUG,
        connectTimeout: Long = 30,
        writeTimeout: Long = 30,
        readTimeout: Long = 120
    ): T {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                MyOkHttpClient(
                    isSafe = isSafe,
                    connectTimeout = connectTimeout,
                    writeTimeout = writeTimeout,
                    readTimeout = readTimeout
                ).get()
            )
            .build()
            .setOkhttpCache(apl)
            .create(T::class.java)
    }

}