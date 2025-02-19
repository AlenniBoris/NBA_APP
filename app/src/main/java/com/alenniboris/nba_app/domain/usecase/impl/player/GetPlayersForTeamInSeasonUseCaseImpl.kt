package com.alenniboris.nba_app.domain.usecase.impl.player

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiPlayersNetworkRepository
import com.alenniboris.nba_app.domain.usecase.player.IGetPlayersForTeamInSeasonUseCase
import kotlinx.coroutines.withContext

class GetPlayersForTeamInSeasonUseCaseImpl(
    private val nbaApiPlayersNetworkRepository: INbaApiPlayersNetworkRepository,
    private val dispatchers: IAppDispatchers
) : IGetPlayersForTeamInSeasonUseCase {

    override suspend fun invoke(
        team: TeamModelDomain,
        season: SeasonModelDomain
    ): CustomResultModelDomain<List<PlayerModelDomain>, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext nbaApiPlayersNetworkRepository.getPlayersBySeasonAndTeam(
                team = team,
                season = season
            )
        }

}