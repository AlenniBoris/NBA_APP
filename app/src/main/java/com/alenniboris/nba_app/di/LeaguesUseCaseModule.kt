package com.alenniboris.nba_app.di

import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiLeaguesNetworkRepository
import com.alenniboris.nba_app.domain.usecase.impl.leagues.GetLeaguesByCountryUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.leagues.IGetLeaguesByCountryUseCase
import org.koin.dsl.module

val LeaguesUseCaseModule = module {

    factory<IGetLeaguesByCountryUseCase> {
        GetLeaguesByCountryUseCaseImpl(
            nbaApiLeaguesNetworkRepository = get<INbaApiLeaguesNetworkRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

}