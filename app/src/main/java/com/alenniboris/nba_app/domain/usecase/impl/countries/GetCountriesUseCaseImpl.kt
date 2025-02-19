package com.alenniboris.nba_app.domain.usecase.impl.countries

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiCountriesNetworkRepository
import com.alenniboris.nba_app.domain.usecase.countries.IGetCountriesUseCase
import kotlinx.coroutines.withContext

class GetCountriesUseCaseImpl(
    private val dispatchers: IAppDispatchers,
    private val nbaApiCountriesNetworkRepository: INbaApiCountriesNetworkRepository
) : IGetCountriesUseCase {

    override suspend fun invoke():
            CustomResultModelDomain<List<CountryModelDomain>, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext nbaApiCountriesNetworkRepository.getAllCountries()
        }

}