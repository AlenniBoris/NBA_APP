package com.alenniboris.nba_app.domain.usecase.impl.game

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.api.nba.GameReloadingResult
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.GameStatisticsModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiGamesNetworkRepository
import com.alenniboris.nba_app.domain.usecase.game.IGetFollowedGamesUseCase
import com.alenniboris.nba_app.domain.usecase.game.IGetGameDataAndStatisticsByIdUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class GetGameDataAndStatisticsByIdUseCaseImpl(
    private val nbaApiGamesNetworkRepository: INbaApiGamesNetworkRepository,
    private val getFollowedGamesUseCase: IGetFollowedGamesUseCase,
    private val dispatchers: IAppDispatchers
) : IGetGameDataAndStatisticsByIdUseCase {

    override suspend fun invoke(
        gameId: Int
    ): CustomResultModelDomain<GameReloadingResult, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext when (
                val gameDataRes = nbaApiGamesNetworkRepository.getGameDataById(gameId)
            ) {
                is CustomResultModelDomain.Success -> {

                    val _teamsStatistics = async {
                        nbaApiGamesNetworkRepository.getTeamsStatisticsInGame(game = gameDataRes.result)
                    }

                    val _playersStatistics = async {
                        nbaApiGamesNetworkRepository.getGameStatisticsForPlayersInGame(game = gameDataRes.result)
                    }

                    val teamsStatistics = _teamsStatistics.await()
                    val playersStatistics = _playersStatistics.await()

                    if (
                        teamsStatistics is CustomResultModelDomain.Success &&
                        playersStatistics is CustomResultModelDomain.Success
                    ) {

                        val ids = getFollowedGamesUseCase.followedFlow.firstOrNull().orEmpty()
                            .map { it.gameId }
                        val game = gameDataRes.result

                        return@withContext CustomResultModelDomain.Success(
                            GameReloadingResult(
                                game = game.copy(isFollowed = ids.contains(game.id)),
                                statistics = GameStatisticsModelDomain(
                                    homeTeamStatistics = teamsStatistics.result.homeTeamStatistics,
                                    homePlayersStatistics = playersStatistics.result.homeTeamPlayersStatistics,
                                    visitorsTeamStatistics = teamsStatistics.result.visitorsTeamStatistics,
                                    visitorPlayersStatistics = playersStatistics.result.visitorsTeamPlayersStatistics,
                                )
                            )
                        )

                    }

                    return@withContext CustomResultModelDomain.Error(
                        NbaApiExceptionModelDomain.SomeUnknownExceptionOccurred
                    )
                }

                is CustomResultModelDomain.Error -> {
                    CustomResultModelDomain.Error(gameDataRes.exception)
                }
            }
        }

}