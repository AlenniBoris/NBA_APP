package com.alenniboris.nba_app.domain.usecase.team

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.TeamsInGameStatisticsModelDomain

interface IGetTeamStatisticsInGameUseCase {

    suspend fun invoke(
        game: GameModelDomain
    ): CustomResultModelDomain<TeamsInGameStatisticsModelDomain, NbaApiExceptionModelDomain>

}