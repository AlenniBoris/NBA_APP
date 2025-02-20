package com.alenniboris.nba_app.domain.usecase.game

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import java.util.Date

interface IGetGamesByDateUseCase {

    suspend fun invoke(date: Date): CustomResultModelDomain<List<GameModelDomain>, NbaApiExceptionModelDomain>

}