package com.alenniboris.nba_app.domain.usecase.countries

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain

interface IGetCountriesUseCase {

    suspend fun invoke():
            CustomResultModelDomain<List<CountryModelDomain>, NbaApiExceptionModelDomain>

}