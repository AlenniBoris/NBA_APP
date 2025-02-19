package com.alenniboris.nba_app.domain.usecase.impl.leagues

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiLeaguesNetworkRepository
import com.alenniboris.nba_app.domain.usecase.leagues.IGetLeaguesByCountryUseCase
import kotlinx.coroutines.withContext

class GetLeaguesByCountryUseCaseImpl(
    private val nbaApiLeaguesNetworkRepository: INbaApiLeaguesNetworkRepository,
    private val dispatchers: IAppDispatchers
) : IGetLeaguesByCountryUseCase {

    override suspend fun invoke(country: CountryModelDomain):
            CustomResultModelDomain<List<LeagueModelDomain>, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext nbaApiLeaguesNetworkRepository.getLeaguesByCountry(
                country = country
            )
        }

}