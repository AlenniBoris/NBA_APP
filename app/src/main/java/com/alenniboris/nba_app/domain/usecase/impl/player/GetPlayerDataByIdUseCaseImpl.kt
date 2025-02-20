package com.alenniboris.nba_app.domain.usecase.impl.player

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiPlayersNetworkRepository
import com.alenniboris.nba_app.domain.usecase.player.IGetFollowedPlayersUseCase
import com.alenniboris.nba_app.domain.usecase.player.IGetPlayerDataByIdUseCase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class GetPlayerDataByIdUseCaseImpl(
    private val nbaApiPlayersNetworkRepository: INbaApiPlayersNetworkRepository,
    private val getFollowedPlayersUseCase: IGetFollowedPlayersUseCase,
    private val dispatchers: IAppDispatchers
) : IGetPlayerDataByIdUseCase {

    override suspend fun invoke(
        id: Int
    ): CustomResultModelDomain<PlayerModelDomain, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            val followedIds =
                getFollowedPlayersUseCase.followedFlow.firstOrNull().orEmpty().map { it.playerId }
            return@withContext when (val res =
                nbaApiPlayersNetworkRepository.getPlayerDataById(id = id)) {
                is CustomResultModelDomain.Success -> {
                    CustomResultModelDomain.Success(
                        res.result.copy(
                            isFollowed = followedIds.contains(
                                res.result.id
                            )
                        )
                    )
                }

                is CustomResultModelDomain.Error -> {
                    CustomResultModelDomain.Error(res.exception)
                }
            }
        }

}