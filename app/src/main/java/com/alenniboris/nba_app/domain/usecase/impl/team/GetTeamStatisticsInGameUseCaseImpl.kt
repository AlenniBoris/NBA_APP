package com.alenniboris.nba_app.domain.usecase.impl.team

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.TeamsInGameStatisticsModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiGamesNetworkRepository
import com.alenniboris.nba_app.domain.usecase.team.IGetTeamStatisticsInGameUseCase
import kotlinx.coroutines.withContext

class GetTeamStatisticsInGameUseCaseImpl(
    private val nbaApiGamesNetworkRepository: INbaApiGamesNetworkRepository,
    private val dispatchers: IAppDispatchers
) : IGetTeamStatisticsInGameUseCase {

    override suspend fun invoke(
        game: GameModelDomain
    ): CustomResultModelDomain<TeamsInGameStatisticsModelDomain, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext nbaApiGamesNetworkRepository.getTeamsStatisticsInGame(game = game)
        }

}