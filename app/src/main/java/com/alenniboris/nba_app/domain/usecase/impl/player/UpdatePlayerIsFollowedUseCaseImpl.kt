package com.alenniboris.nba_app.domain.usecase.impl.player

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.repository.authentication.IAuthenticationRepository
import com.alenniboris.nba_app.domain.repository.database.api.nba.INbaApiPlayersDatabaseRepository
import com.alenniboris.nba_app.domain.usecase.player.IUpdatePlayerIsFollowedUseCase
import kotlinx.coroutines.withContext

class UpdatePlayerIsFollowedUseCaseImpl(
    private val nbaApiPlayersDatabaseRepository: INbaApiPlayersDatabaseRepository,
    private val authenticationRepository: IAuthenticationRepository,
    private val dispatchers: IAppDispatchers
) : IUpdatePlayerIsFollowedUseCase {

    override suspend fun invoke(
        player: PlayerModelDomain
    ): CustomResultModelDomain<Unit, NbaApiExceptionModelDomain> = withContext(dispatchers.IO) {
        return@withContext if (player.isFollowed) {
            nbaApiPlayersDatabaseRepository.deletePlayerFromDatabase(
                player = player,
                user = authenticationRepository.user.value
            )
        } else {
            nbaApiPlayersDatabaseRepository.addPlayerToDatabase(
                player = player,
                user = authenticationRepository.user.value
            )
        }
    }

}