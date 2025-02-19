package com.alenniboris.nba_app.domain.usecase.game

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain

interface IUpdateGameIsFollowedUseCase {

    suspend fun invoke(
        game: GameModelDomain
    ): CustomResultModelDomain<Unit, NbaApiExceptionModelDomain>

}