package com.alenniboris.nba_app.domain.repository.network.api.nba

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.GameModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import java.util.Date

interface INbaApiGamesNetworkRepository {

    suspend fun getGamesByDate(
        date: Date
    ): CustomResultModelDomain<List<GameModelDomain>, NbaApiExceptionModelDomain>

    suspend fun getGamesBySeasonAndLeague(
        season: SeasonModelDomain?,
        league: LeagueModelDomain?
    ): CustomResultModelDomain<List<GameModelDomain>, NbaApiExceptionModelDomain>

}