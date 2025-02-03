package com.alenniboris.nba_app.domain.repository.network.api.nba

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.PlayerStatisticsModelDomain

interface INbaApiPlayersNetworkRepository {

    suspend fun getPlayersBySearchQuery(
        searchQuery: String
    ): CustomResultModelDomain<List<PlayerModelDomain>, NbaApiExceptionModelDomain>

    suspend fun getPlayersBySeasonAndTeam(
        season: SeasonModelDomain?,
        team: TeamModelDomain?
    ): CustomResultModelDomain<List<PlayerModelDomain>, NbaApiExceptionModelDomain>

    suspend fun getPlayersBySearchQueryAndSeasonAndTeam(
        searchQuery: String,
        season: SeasonModelDomain?,
        team: TeamModelDomain?
    ): CustomResultModelDomain<List<PlayerModelDomain>, NbaApiExceptionModelDomain>

    suspend fun requestForPlayersStatisticsInSeason(
        season: SeasonModelDomain,
        player: PlayerModelDomain
    ): CustomResultModelDomain<List<PlayerStatisticsModelDomain>, NbaApiExceptionModelDomain>

    suspend fun getPlayerDataById(
        id: Int
    ): CustomResultModelDomain<PlayerModelDomain, NbaApiExceptionModelDomain>

}