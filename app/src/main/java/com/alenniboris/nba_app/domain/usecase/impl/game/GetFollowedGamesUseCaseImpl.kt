package com.alenniboris.nba_app.domain.usecase.impl.game

import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.entity.api.nba.GameEntityModelDomain
import com.alenniboris.nba_app.domain.repository.authentication.IAuthenticationRepository
import com.alenniboris.nba_app.domain.repository.database.api.nba.INbaApiGamesDatabaseRepository
import com.alenniboris.nba_app.domain.usecase.game.IGetFollowedGamesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn

@OptIn(ExperimentalCoroutinesApi::class)
class GetFollowedGamesUseCaseImpl(
    private val authenticationRepository: IAuthenticationRepository,
    private val nbaApiGamesDatabaseRepository: INbaApiGamesDatabaseRepository,
    private val dispatchers: IAppDispatchers
) : IGetFollowedGamesUseCase {

    override val followedFlow: SharedFlow<List<GameEntityModelDomain>> =
        authenticationRepository.user
            .flatMapLatest {
                it?.let { user ->
                    nbaApiGamesDatabaseRepository.getAllGamesForUser(user)
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