package com.alenniboris.nba_app.domain.usecase.game

import com.alenniboris.nba_app.domain.model.entity.api.nba.GameEntityModelDomain
import kotlinx.coroutines.flow.SharedFlow

interface IGetFollowedGamesUseCase {

    val followedFlow: SharedFlow<List<GameEntityModelDomain>>

}