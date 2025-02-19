package com.alenniboris.nba_app.domain.usecase.game

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.GameReloadingResult
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain

interface IGetGameDataAndStatisticsByIdUseCase {

    suspend fun invoke(
        gameId: Int
    ): CustomResultModelDomain<GameReloadingResult, NbaApiExceptionModelDomain>

}