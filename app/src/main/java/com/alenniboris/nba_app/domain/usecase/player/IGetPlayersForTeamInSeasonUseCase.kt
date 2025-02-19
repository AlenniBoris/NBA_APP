package com.alenniboris.nba_app.domain.usecase.player

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain

interface IGetPlayersForTeamInSeasonUseCase {

    suspend fun invoke(
        team: TeamModelDomain,
        season: SeasonModelDomain
    ): CustomResultModelDomain<List<PlayerModelDomain>, NbaApiExceptionModelDomain>

}