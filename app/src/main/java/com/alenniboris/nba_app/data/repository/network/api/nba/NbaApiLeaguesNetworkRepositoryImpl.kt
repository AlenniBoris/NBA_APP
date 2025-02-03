package com.alenniboris.nba_app.data.repository.network.api.nba

import com.alenniboris.nba_app.data.model.api.nba.league.toModelDomain
import com.alenniboris.nba_app.data.source.remote.api.nba.INbaApiService
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.league.LeaguesResponseErrorsModelData
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiLeaguesNetworkRepository
import com.alenniboris.nba_app.domain.utils.GsonUtil.fromJson
import kotlinx.coroutines.withContext

class NbaApiLeaguesNetworkRepositoryImpl(
    private val apiService: INbaApiService,
    private val dispatchers: IAppDispatchers
) : INbaApiLeaguesNetworkRepository {

    override suspend fun getLeaguesByCountry(
        country: CountryModelDomain?
    ): CustomResultModelDomain<List<LeagueModelDomain>, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            if (country == null) {
                return@withContext CustomResultModelDomain.Error<List<LeagueModelDomain>, NbaApiExceptionModelDomain>(
                    NbaApiExceptionModelDomain.SomeUnknownExceptionOccurred
                )
            }
            return@withContext NbaApiNetworkRepositoryFunctions.getElementsFromApi(
                apiCall = {
                    apiService.getLeaguesByCountry(
                        countryId = country.id
                    )
                },
                dispatcher = dispatchers.IO,
                transform = { responseList ->
                    responseList?.mapNotNull { it?.toModelDomain() }
                },
                errorsParser = { json ->
                    json?.fromJson<LeaguesResponseErrorsModelData>()
                }
            )
        }


}