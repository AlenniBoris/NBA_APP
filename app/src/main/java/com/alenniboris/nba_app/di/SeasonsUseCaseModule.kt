package com.alenniboris.nba_app.di

import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiSeasonsNetworkRepository
import com.alenniboris.nba_app.domain.usecase.impl.seasons.GetSeasonsUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.seasons.IGetSeasonsUseCase
import org.koin.dsl.module

val SeasonsUseCaseModule = module {

    single<IGetSeasonsUseCase> {
        GetSeasonsUseCaseImpl(
            nbaApiSeasonsNetworkRepository = get<INbaApiSeasonsNetworkRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

}