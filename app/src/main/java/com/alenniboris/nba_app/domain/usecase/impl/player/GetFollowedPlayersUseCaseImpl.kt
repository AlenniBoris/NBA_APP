package com.alenniboris.nba_app.domain.usecase.impl.player

import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.entity.api.nba.PlayerEntityModelDomain
import com.alenniboris.nba_app.domain.repository.authentication.IAuthenticationRepository
import com.alenniboris.nba_app.domain.repository.database.api.nba.INbaApiPlayersDatabaseRepository
import com.alenniboris.nba_app.domain.usecase.player.IGetFollowedPlayersUseCase
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

class GetFollowedPlayersUseCaseImpl(
    private val authenticationRepository: IAuthenticationRepository,
    private val nbaApiPlayersDatabaseRepository: INbaApiPlayersDatabaseRepository,
    private val dispatchers: IAppDispatchers
) : IGetFollowedPlayersUseCase {

    override val followedFlow: SharedFlow<List<PlayerEntityModelDomain>> =
        authenticationRepository.user
            .flatMapLatest {
                it?.let { user ->
                    nbaApiPlayersDatabaseRepository.getAllPlayersForUser(user)
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