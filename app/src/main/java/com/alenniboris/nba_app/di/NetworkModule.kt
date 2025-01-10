package com.alenniboris.nba_app.di

import com.alenniboris.nba_app.data.repository.NbaApiRepositoryImpl
import com.alenniboris.nba_app.data.source.api.INbaApiService
import com.alenniboris.nba_app.domain.manager.INbaApiManager
import com.alenniboris.nba_app.domain.manager.impl.NbaApiManagerImpl
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.repository.INbaApiRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val NetworkModule = module {

    single<INbaApiService> {
        INbaApiService.get(
            apl = androidApplication()
        )
    }

    single<INbaApiRepository> {
        NbaApiRepositoryImpl(
            apiService = get<INbaApiService>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<INbaApiManager> {
        NbaApiManagerImpl(
            nbaApiRepository = get<INbaApiRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

}