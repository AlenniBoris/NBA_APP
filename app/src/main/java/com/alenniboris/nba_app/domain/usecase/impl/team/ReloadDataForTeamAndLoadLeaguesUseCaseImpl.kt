package com.alenniboris.nba_app.domain.usecase.impl.team

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamReloadingResult
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiLeaguesNetworkRepository
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiTeamsNetworkRepository
import com.alenniboris.nba_app.domain.usecase.team.IGetFollowedTeamsUseCase
import com.alenniboris.nba_app.domain.usecase.team.IReloadDataForTeamAndLoadLeaguesUseCase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class ReloadDataForTeamAndLoadLeaguesUseCaseImpl(
    private val dispatchers: IAppDispatchers,
    private val nbaApiTeamsNetworkRepository: INbaApiTeamsNetworkRepository,
    private val nbaApiLeaguesNetworkRepository: INbaApiLeaguesNetworkRepository,
    private val getFollowedTeamsUseCaseImpl: IGetFollowedTeamsUseCase
) : IReloadDataForTeamAndLoadLeaguesUseCase {

    override suspend fun invoke(
        team: TeamModelDomain
    ): CustomResultModelDomain<TeamReloadingResult, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext when (
                val teamDataRes = nbaApiTeamsNetworkRepository.getDataForTeamById(id = team.id)
            ) {
                is CustomResultModelDomain.Success -> {
                    when (
                        val leaguesDataRes = nbaApiLeaguesNetworkRepository.getLeaguesByCountry(
                            country = teamDataRes.result.country
                        )
                    ) {
                        is CustomResultModelDomain.Success -> {
                            val followedIds =
                                getFollowedTeamsUseCaseImpl.followedFlow.firstOrNull().orEmpty()
                                    .map { it.teamId }
                            CustomResultModelDomain.Success(
                                TeamReloadingResult(
                                    teamData = teamDataRes.result.copy(
                                        isFollowed = followedIds.contains(
                                            teamDataRes.result.id
                                        )
                                    ),
                                    leaguesData = leaguesDataRes.result
                                )
                            )
                        }

                        is CustomResultModelDomain.Error -> CustomResultModelDomain.Error<TeamReloadingResult, NbaApiExceptionModelDomain>(
                            leaguesDataRes.exception
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    CustomResultModelDomain.Error<TeamReloadingResult, NbaApiExceptionModelDomain>(
                        teamDataRes.exception
                    )
                }
            }
        }

}