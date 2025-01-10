package com.alenniboris.nba_app.domain.manager

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IStateModel
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.model.params.api.nba.INbaApiRequestParams

interface INbaApiManager {

    suspend fun makeRequestForListOfElements(
        requestParameters: INbaApiRequestParams
    ): CustomResultModelDomain<List<IStateModel>, NbaApiExceptionModelDomain>?

    suspend fun getAllSeasons(): CustomResultModelDomain<List<SeasonModelDomain>, NbaApiExceptionModelDomain>

    suspend fun getAllCountries(): CustomResultModelDomain<List<CountryModelDomain>, NbaApiExceptionModelDomain>

    suspend fun getLeaguesByCountry(
        country: CountryModelDomain
    ): CustomResultModelDomain<List<LeagueModelDomain>, NbaApiExceptionModelDomain>

}