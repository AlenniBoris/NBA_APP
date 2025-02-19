package com.alenniboris.nba_app.domain.usecase.impl.game

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.repository.authentication.IAuthenticationRepository
import com.alenniboris.nba_app.domain.repository.database.api.nba.INbaApiGamesDatabaseRepository
import com.alenniboris.nba_app.domain.usecase.game.IUpdateGameIsFollowedUseCase
import kotlinx.coroutines.withContext

class UpdateGameIsFollowedUseCaseImpl(
    private val nbaApiGamesDatabaseRepository: INbaApiGamesDatabaseRepository,
    private val authenticationRepository: IAuthenticationRepository,
    private val dispatchers: IAppDispatchers
) : IUpdateGameIsFollowedUseCase {

    override suspend fun invoke(
        game: GameModelDomain
    ): CustomResultModelDomain<Unit, NbaApiExceptionModelDomain> = withContext(dispatchers.IO) {
        return@withContext if (game.isFollowed) {
            nbaApiGamesDatabaseRepository.deleteGameFromDatabase(
                game = game,
                user = authenticationRepository.user.value
            )
        } else {
            nbaApiGamesDatabaseRepository.addGameToDatabase(
                game = game,
                user = authenticationRepository.user.value
            )
        }
    }

}