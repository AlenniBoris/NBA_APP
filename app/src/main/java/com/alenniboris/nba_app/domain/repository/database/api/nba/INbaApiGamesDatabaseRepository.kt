package com.alenniboris.nba_app.domain.repository.database.api.nba

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.UserModelDomain
import com.alenniboris.nba_app.domain.model.entity.api.nba.GameEntityModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import kotlinx.coroutines.flow.Flow

interface INbaApiGamesDatabaseRepository {

    suspend fun addGameToDatabase(
        game: GameModelDomain,
        user: UserModelDomain?
    ): CustomResultModelDomain<Unit, NbaApiExceptionModelDomain>

    suspend fun deleteGameFromDatabase(
        game: GameModelDomain,
        user: UserModelDomain?
    ): CustomResultModelDomain<Unit, NbaApiExceptionModelDomain>

    fun getAllGamesForUser(user: UserModelDomain): Flow<List<GameEntityModelDomain>>

}