package com.alenniboris.nba_app.di

import com.alenniboris.nba_app.data.repository.network.api.nba.NbaApiCountriesNetworkRepositoryImpl
import com.alenniboris.nba_app.data.repository.network.api.nba.NbaApiGamesNetworkRepositoryImpl
import com.alenniboris.nba_app.data.repository.network.api.nba.NbaApiLeaguesNetworkRepositoryImpl
import com.alenniboris.nba_app.data.repository.network.api.nba.NbaApiPlayersNetworkRepositoryImpl
import com.alenniboris.nba_app.data.repository.network.api.nba.NbaApiSeasonsNetworkRepositoryImpl
import com.alenniboris.nba_app.data.repository.network.api.nba.NbaApiTeamsNetworkRepositoryImpl
import com.alenniboris.nba_app.data.source.remote.api.nba.INbaApiService
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiCountriesNetworkRepository
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiGamesNetworkRepository
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiLeaguesNetworkRepository
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiPlayersNetworkRepository
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiSeasonsNetworkRepository
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiTeamsNetworkRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val NetworkModule = module {

    single<INbaApiService> {
        INbaApiService.get(
            apl = androidApplication()
        )
    }

    single<INbaApiGamesNetworkRepository> {
        NbaApiGamesNetworkRepositoryImpl(
            apiService = get<INbaApiService>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<INbaApiTeamsNetworkRepository> {
        NbaApiTeamsNetworkRepositoryImpl(
            apiService = get<INbaApiService>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<INbaApiPlayersNetworkRepository> {
        NbaApiPlayersNetworkRepositoryImpl(
            apiService = get<INbaApiService>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<INbaApiCountriesNetworkRepository> {
        NbaApiCountriesNetworkRepositoryImpl(
            apiService = get<INbaApiService>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<INbaApiLeaguesNetworkRepository> {
        NbaApiLeaguesNetworkRepositoryImpl(
            apiService = get<INbaApiService>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<INbaApiSeasonsNetworkRepository> {
        NbaApiSeasonsNetworkRepositoryImpl(
            apiService = get<INbaApiService>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

}