package com.alenniboris.nba_app.domain.repository.network.api.nba

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.TeamModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain

interface INbaApiTeamsNetworkRepository {

    suspend fun getTeamsByCountry(
        country: CountryModelDomain?
    ): CustomResultModelDomain<List<TeamModelDomain>, NbaApiExceptionModelDomain>

    suspend fun getTeamsBySearchQuery(
        searchQuery: String
    ): CustomResultModelDomain<List<TeamModelDomain>, NbaApiExceptionModelDomain>

    suspend fun getTeamsBySeasonAndLeague(
        season: SeasonModelDomain?,
        league: LeagueModelDomain?
    ): CustomResultModelDomain<List<TeamModelDomain>, NbaApiExceptionModelDomain>

    suspend fun getTeamsBySearchQueryAndSeasonAndLeague(
        searchQuery: String,
        season: SeasonModelDomain?,
        league: LeagueModelDomain?
    ): CustomResultModelDomain<List<TeamModelDomain>, NbaApiExceptionModelDomain>

    suspend fun getTeamsBySearchQueryAndSeasonAndLeagueAndCountry(
        searchQuery: String,
        season: SeasonModelDomain?,
        league: LeagueModelDomain?,
        country: CountryModelDomain?
    ): CustomResultModelDomain<List<TeamModelDomain>, NbaApiExceptionModelDomain>

}