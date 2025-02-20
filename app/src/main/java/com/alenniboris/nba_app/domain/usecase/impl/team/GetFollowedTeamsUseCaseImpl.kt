package com.alenniboris.nba_app.domain.usecase.impl.team

import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.entity.api.nba.TeamEntityModelDomain
import com.alenniboris.nba_app.domain.repository.authentication.IAuthenticationRepository
import com.alenniboris.nba_app.domain.repository.database.api.nba.INbaApiTeamsDatabaseRepository
import com.alenniboris.nba_app.domain.usecase.team.IGetFollowedTeamsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn

class GetFollowedTeamsUseCaseImpl(
    private val authenticationRepository: IAuthenticationRepository,
    private val nbaApiTeamsDatabaseRepository: INbaApiTeamsDatabaseRepository,
    private val dispatchers: IAppDispatchers
) : IGetFollowedTeamsUseCase {

    override val followedFlow: SharedFlow<List<TeamEntityModelDomain>> =
        authenticationRepository.user
            .flatMapLatest {
                it?.let { user ->
                    nbaApiTeamsDatabaseRepository.getAllTeamsForUser(user)
                } ?: emptyFlow()
            }
            .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
            .distinctUntilChanged()
            .shareIn(
                scope = CoroutineScope(SupervisorJob() + dispatchers.IO),
                started = SharingStarted.WhileSubscribed(20_000, 0),
                replay = 1
            )


}