package com.alenniboris.nba_app.domain.usecase.team

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain

interface IUpdateTeamIsFollowedUseCase {

    suspend fun invoke(
        team: TeamModelDomain
    ): CustomResultModelDomain<Unit, NbaApiExceptionModelDomain>

}