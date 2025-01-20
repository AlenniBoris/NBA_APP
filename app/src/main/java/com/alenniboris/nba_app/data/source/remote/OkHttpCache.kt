package com.alenniboris.nba_app.data.source.remote

import android.app.Application
import com.alenniboris.nba_app.BuildConfig
import com.andretietz.retrofit.responseCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import okhttp3.Cache
import retrofit2.Retrofit
import java.io.File

object OkHttpCache {
    private var cache: Cache? = null
    private var isInit = false

    private fun getCache(apl: Application): Cache? {
        if (!isInit) {
            isInit = true
            runCatching {
                val currentVersion = BuildConfig.VERSION_CODE
                fun getFile(code: Int) = File("${apl.filesDir}/okhttp_cache/${code}")

                val cacheFile = getFile(currentVersion).apply {
                    mkdirs()
                }
                cache = Cache(cacheFile, 1024 * 1024 * 10)

                (MainScope() + Dispatchers.IO).launch {
                    (1 until currentVersion).forEach {
                        runCatching {
                            getFile(it).deleteRecursively()
                        }
                    }
                }
            }
        }
        return cache
    }

    fun Retrofit.setOkhttpCache(apl: Application): Retrofit {
        val mCache = getCache(apl)
        return if (mCache != null) {
            responseCache(mCache)
        } else {
            this
        }
    }

}