package com.alenniboris.nba_app.domain.usecase.player

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain

interface IGetPlayersByQueryUseCase {

    suspend fun invoke(
        query: String
    ): CustomResultModelDomain<List<PlayerModelDomain>, NbaApiExceptionModelDomain>

}