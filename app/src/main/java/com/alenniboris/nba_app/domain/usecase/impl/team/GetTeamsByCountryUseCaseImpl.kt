package com.alenniboris.nba_app.domain.usecase.impl.team

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiTeamsNetworkRepository
import com.alenniboris.nba_app.domain.usecase.team.IGetFollowedTeamsUseCase
import com.alenniboris.nba_app.domain.usecase.team.IGetTeamsByCountryUseCase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class GetTeamsByCountryUseCaseImpl(
    private val getFollowedTeamsUseCase: IGetFollowedTeamsUseCase,
    private val nbaApiTeamsNetworkRepository: INbaApiTeamsNetworkRepository,
    private val dispatchers: IAppDispatchers
) : IGetTeamsByCountryUseCase {

    override suspend fun invoke(
        country: CountryModelDomain?
    ): CustomResultModelDomain<List<TeamModelDomain>, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext when (
                val requestResult =
                    nbaApiTeamsNetworkRepository.getTeamsByCountry(
                        country = country
                    )
            ) {
                is CustomResultModelDomain.Success -> {
                    val followed = getFollowedTeamsUseCase.followedFlow.firstOrNull().orEmpty()
                        .map { it.teamId }
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