package com.alenniboris.nba_app.domain.usecase.player

import com.alenniboris.nba_app.domain.model.entity.api.nba.PlayerEntityModelDomain
import kotlinx.coroutines.flow.SharedFlow

interface IGetFollowedPlayersUseCase {

    val followedFlow: SharedFlow<List<PlayerEntityModelDomain>>

}