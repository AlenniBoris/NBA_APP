package com.alenniboris.nba_app.domain.repository.database.api.nba

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.TeamModelDomain
import com.alenniboris.nba_app.domain.model.UserModelDomain
import com.alenniboris.nba_app.domain.model.entity.TeamEntityModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import kotlinx.coroutines.flow.Flow

interface INbaApiTeamsDatabaseRepository {

    suspend fun addTeamToDatabase(
        team: TeamModelDomain,
        user: UserModelDomain?
    ): CustomResultModelDomain<Unit, NbaApiExceptionModelDomain>

    suspend fun deleteTeamFromDatabase(
        team: TeamModelDomain,
        user: UserModelDomain?
    ): CustomResultModelDomain<Unit, NbaApiExceptionModelDomain>

    fun getAllTeamsForUser(user: UserModelDomain): Flow<List<TeamEntityModelDomain>>

}