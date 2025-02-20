package com.alenniboris.nba_app.domain.usecase.impl.game

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiGamesNetworkRepository
import com.alenniboris.nba_app.domain.usecase.game.IGetFollowedGamesUseCase
import com.alenniboris.nba_app.domain.usecase.game.IGetGamesBySeasonAndLeagueUseCase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class GetGamesBySeasonAndLeagueUseCaseImpl(
    private val getFollowedGamesUseCase: IGetFollowedGamesUseCase,
    private val nbaApiGamesNetworkRepository: INbaApiGamesNetworkRepository,
    private val dispatchers: IAppDispatchers
) : IGetGamesBySeasonAndLeagueUseCase {

    override suspend fun invoke(
        season: SeasonModelDomain?,
        league: LeagueModelDomain?
    ): CustomResultModelDomain<List<GameModelDomain>, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext when (
                val requestResult =
                    nbaApiGamesNetworkRepository.getGamesBySeasonAndLeague(
                        season = season,
                        league = league
                    )
            ) {
                is CustomResultModelDomain.Success -> {
                    val followed = getFollowedGamesUseCase.followedFlow.firstOrNull().orEmpty()
                        .map { it.gameId }
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