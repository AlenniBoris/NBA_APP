package com.alenniboris.nba_app.domain.usecase.team

import com.alenniboris.nba_app.domain.model.entity.api.nba.TeamEntityModelDomain
import kotlinx.coroutines.flow.SharedFlow

interface IGetFollowedTeamsUseCase {

    val followedFlow: SharedFlow<List<TeamEntityModelDomain>>

}