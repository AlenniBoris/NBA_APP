package com.alenniboris.nba_app.data.repository.network.api.nba

import com.alenniboris.nba_app.data.model.api.nba.country.toModelDomain
import com.alenniboris.nba_app.data.source.remote.api.nba.INbaApiCountriesService
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.country.CountriesResponseErrorsModelData
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiCountriesNetworkRepository
import com.alenniboris.nba_app.domain.utils.GsonUtil.fromJson

class NbaApiCountriesNetworkRepositoryImpl(
    private val apiService: INbaApiCountriesService,
    private val dispatchers: IAppDispatchers,
) : INbaApiCountriesNetworkRepository {

    override suspend fun getAllCountries()
            : CustomResultModelDomain<List<CountryModelDomain>, NbaApiExceptionModelDomain> =
        NbaApiNetworkRepositoryFunctions.getElementsFromApi(
            apiCall = {
                apiService.getAllCountries()
            },
            dispatcher = dispatchers.IO,
            transform = { responseList ->
                responseList?.mapNotNull { it?.toModelDomain() }
            },
            errorsParser = { json ->
                json?.fromJson<CountriesResponseErrorsModelData>()
            }
        )

}