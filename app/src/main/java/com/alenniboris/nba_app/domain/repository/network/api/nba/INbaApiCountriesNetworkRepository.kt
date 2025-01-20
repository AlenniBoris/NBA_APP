package com.alenniboris.nba_app.domain.repository.network.api.nba

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain

interface INbaApiCountriesNetworkRepository {

    suspend fun getAllCountries()
            : CustomResultModelDomain<List<CountryModelDomain>, NbaApiExceptionModelDomain>

}