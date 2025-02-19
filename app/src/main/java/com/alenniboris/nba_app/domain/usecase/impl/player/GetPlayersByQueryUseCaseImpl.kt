package com.alenniboris.nba_app.domain.usecase.impl.player

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiPlayersNetworkRepository
import com.alenniboris.nba_app.domain.usecase.player.IGetFollowedPlayersUseCase
import com.alenniboris.nba_app.domain.usecase.player.IGetPlayersByQueryUseCase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class GetPlayersByQueryUseCaseImpl(
    private val getFollowedPlayersUseCase: IGetFollowedPlayersUseCase,
    private val nbaApiPlayersNetworkRepository: INbaApiPlayersNetworkRepository,
    private val dispatchers: IAppDispatchers
) : IGetPlayersByQueryUseCase {

    override suspend fun invoke(
        query: String
    ): CustomResultModelDomain<List<PlayerModelDomain>, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext when (
                val requestResult =
                    nbaApiPlayersNetworkRepository.getPlayersBySearchQuery(
                        searchQuery = query
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