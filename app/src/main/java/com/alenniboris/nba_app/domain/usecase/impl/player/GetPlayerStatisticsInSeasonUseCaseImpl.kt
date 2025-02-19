package com.alenniboris.nba_app.domain.usecase.impl.player

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.PlayerStatisticsModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiPlayersNetworkRepository
import com.alenniboris.nba_app.domain.usecase.player.IGetPlayerStatisticsInSeasonUseCase
import kotlinx.coroutines.withContext

class GetPlayerStatisticsInSeasonUseCaseImpl(
    private val nbaApiPlayersNetworkRepository: INbaApiPlayersNetworkRepository,
    private val dispatchers: IAppDispatchers
) : IGetPlayerStatisticsInSeasonUseCase {

    override suspend fun invoke(
        season: SeasonModelDomain,
        player: PlayerModelDomain
    ): CustomResultModelDomain<List<PlayerStatisticsModelDomain>, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext nbaApiPlayersNetworkRepository.requestForPlayersStatisticsInSeason(
                season = season,
                player = player
            )
        }

}