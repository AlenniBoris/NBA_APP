package com.alenniboris.nba_app.domain.usecase.impl.seasons

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiSeasonsNetworkRepository
import com.alenniboris.nba_app.domain.usecase.seasons.IGetSeasonsUseCase
import kotlinx.coroutines.withContext

class GetSeasonsUseCaseImpl(
    private val nbaApiSeasonsNetworkRepository: INbaApiSeasonsNetworkRepository,
    private val dispatchers: IAppDispatchers
) : IGetSeasonsUseCase {

    override suspend fun invoke():
            CustomResultModelDomain<List<SeasonModelDomain>, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext nbaApiSeasonsNetworkRepository.getAllSeasons()
        }

}