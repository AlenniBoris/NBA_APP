package com.alenniboris.nba_app.domain.usecase.seasons

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain

interface IGetSeasonsUseCase {

    suspend fun invoke():
            CustomResultModelDomain<List<SeasonModelDomain>, NbaApiExceptionModelDomain>

}