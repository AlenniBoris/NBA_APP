package com.alenniboris.nba_app.domain.repository.database.api.nba

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.UserModelDomain
import com.alenniboris.nba_app.domain.model.entity.PlayerEntityModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import kotlinx.coroutines.flow.Flow

interface INbaApiPlayersDatabaseRepository {

    suspend fun addPlayerToDatabase(
        player: PlayerModelDomain,
        user: UserModelDomain?
    ): CustomResultModelDomain<Unit, NbaApiExceptionModelDomain>

    suspend fun deletePlayerFromDatabase(
        player: PlayerModelDomain,
        user: UserModelDomain?
    ): CustomResultModelDomain<Unit, NbaApiExceptionModelDomain>

    fun getAllPlayersForUser(user: UserModelDomain): Flow<List<PlayerEntityModelDomain>>

}