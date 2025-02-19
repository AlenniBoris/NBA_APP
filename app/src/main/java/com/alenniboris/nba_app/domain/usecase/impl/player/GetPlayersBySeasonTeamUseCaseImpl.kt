package com.alenniboris.nba_app.domain.usecase.impl.player

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiPlayersNetworkRepository
import com.alenniboris.nba_app.domain.usecase.player.IGetFollowedPlayersUseCase
import com.alenniboris.nba_app.domain.usecase.player.IGetPlayersBySeasonTeamUseCase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class GetPlayersBySeasonTeamUseCaseImpl(
    private val getFollowedPlayersUseCase: IGetFollowedPlayersUseCase,
    private val nbaApiPlayersNetworkRepository: INbaApiPlayersNetworkRepository,
    private val dispatchers: IAppDispatchers
) : IGetPlayersBySeasonTeamUseCase {

    override suspend fun invoke(
        season: SeasonModelDomain?,
        team: TeamModelDomain?
    ): CustomResultModelDomain<List<PlayerModelDomain>, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext when (
                val requestResult =
                    nbaApiPlayersNetworkRepository.getPlayersBySeasonAndTeam(
                        season = season,
                        team = team
                    )
            ) {
                is CustomResultModelDomain.Success -> {
                    val followed = getFollowedPlayersUseCase.followedFlow.firstOrNull().orEmpty()
                        .map { it.playerId }
                    CustomResultModelDomain.Success(
                        requestResult.result.map {
                            it.copy(isFollowed = followed.contains(it.id))
                        }
                    )
                }

                is CustomResultModelDomain.Error -> {
                    CustomResultModelDomain.Error(requestResult.exception)
                }
            }
        }

}