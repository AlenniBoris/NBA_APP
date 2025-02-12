package com.alenniboris.nba_app.di

import com.alenniboris.nba_app.data.repository.network.api.nba.NbaApiCountriesNetworkRepositoryImpl
import com.alenniboris.nba_app.data.repository.network.api.nba.NbaApiGamesNetworkRepositoryImpl
import com.alenniboris.nba_app.data.repository.network.api.nba.NbaApiLeaguesNetworkRepositoryImpl
import com.alenniboris.nba_app.data.repository.network.api.nba.NbaApiPlayersNetworkRepositoryImpl
import com.alenniboris.nba_app.data.repository.network.api.nba.NbaApiSeasonsNetworkRepositoryImpl
import com.alenniboris.nba_app.data.repository.network.api.nba.NbaApiTeamsNetworkRepositoryImpl
import com.alenniboris.nba_app.data.source.remote.api.nba.INbaApiCountriesService
import com.alenniboris.nba_app.data.source.remote.api.nba.INbaApiGameService
import com.alenniboris.nba_app.data.source.remote.api.nba.INbaApiLeaguesService
import com.alenniboris.nba_app.data.source.remote.api.nba.INbaApiPlayerService
import com.alenniboris.nba_app.data.source.remote.api.nba.INbaApiSeasonsService
import com.alenniboris.nba_app.data.source.remote.api.nba.INbaApiTeamService
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

    single<INbaApiGameService> {
        INbaApiGameService.get(
            apl = androidApplication()
        )
    }

    single<INbaApiGamesNetworkRepository> {
        NbaApiGamesNetworkRepositoryImpl(
            apiService = get<INbaApiGameService>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<INbaApiTeamService> {
        INbaApiTeamService.get(
            apl = androidApplication()
        )
    }

    single<INbaApiTeamsNetworkRepository> {
        NbaApiTeamsNetworkRepositoryImpl(
            apiService = get<INbaApiTeamService>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<INbaApiPlayerService> {
        INbaApiPlayerService.get(
            apl = androidApplication()
        )
    }

    single<INbaApiPlayersNetworkRepository> {
        NbaApiPlayersNetworkRepositoryImpl(
            apiService = get<INbaApiPlayerService>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<INbaApiCountriesService> {
        INbaApiCountriesService.get(
            apl = androidApplication()
        )
    }

    single<INbaApiCountriesNetworkRepository> {
        NbaApiCountriesNetworkRepositoryImpl(
            apiService = get<INbaApiCountriesService>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<INbaApiLeaguesService> {
        INbaApiLeaguesService.get(
            apl = androidApplication()
        )
    }

    single<INbaApiLeaguesNetworkRepository> {
        NbaApiLeaguesNetworkRepositoryImpl(
            apiService = get<INbaApiLeaguesService>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<INbaApiSeasonsService> {
        INbaApiSeasonsService.get(
            apl = androidApplication()
        )
    }

    single<INbaApiSeasonsNetworkRepository> {
        NbaApiSeasonsNetworkRepositoryImpl(
            apiService = get<INbaApiSeasonsService>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

}