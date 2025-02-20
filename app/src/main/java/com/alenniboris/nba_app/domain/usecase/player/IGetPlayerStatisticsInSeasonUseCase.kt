package com.alenniboris.nba_app.domain.usecase.player

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.PlayerStatisticsModelDomain

interface IGetPlayerStatisticsInSeasonUseCase {

    suspend fun invoke(
        season: SeasonModelDomain,
        player: PlayerModelDomain
    ): CustomResultModelDomain<List<PlayerStatisticsModelDomain>, NbaApiExceptionModelDomain>

}