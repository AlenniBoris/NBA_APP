package com.alenniboris.nba_app.di

import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiCountriesNetworkRepository
import com.alenniboris.nba_app.domain.usecase.countries.IGetCountriesUseCase
import com.alenniboris.nba_app.domain.usecase.impl.countries.GetCountriesUseCaseImpl
import org.koin.dsl.module

val CountriesUseCaseModule = module {

    factory<IGetCountriesUseCase> {
        GetCountriesUseCaseImpl(
            dispatchers = get<IAppDispatchers>(),
            nbaApiCountriesNetworkRepository = get<INbaApiCountriesNetworkRepository>()
        )
    }

}