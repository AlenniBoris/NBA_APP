package com.alenniboris.nba_app.domain.usecase.impl.team

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.repository.authentication.IAuthenticationRepository
import com.alenniboris.nba_app.domain.repository.database.api.nba.INbaApiTeamsDatabaseRepository
import com.alenniboris.nba_app.domain.usecase.team.IUpdateTeamIsFollowedUseCase
import kotlinx.coroutines.withContext

class UpdateTeamIsFollowedUseCaseImpl(
    private val nbaApiTeamsDatabaseRepository: INbaApiTeamsDatabaseRepository,
    private val authenticationRepository: IAuthenticationRepository,
    private val dispatchers: IAppDispatchers
) : IUpdateTeamIsFollowedUseCase {

    override suspend fun invoke(
        team: TeamModelDomain
    ): CustomResultModelDomain<Unit, NbaApiExceptionModelDomain> = withContext(dispatchers.IO) {
        return@withContext if (team.isFollowed) {
            nbaApiTeamsDatabaseRepository.deleteTeamFromDatabase(
                team = team,
                user = authenticationRepository.user.value
            )
        } else {
            nbaApiTeamsDatabaseRepository.addTeamToDatabase(
                team = team,
                user = authenticationRepository.user.value
            )
        }
    }

}