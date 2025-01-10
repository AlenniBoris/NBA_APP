package com.alenniboris.nba_app.domain.repository

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.GameModelDomain
import com.alenniboris.nba_app.domain.model.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.TeamModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import java.util.Date

interface INbaApiRepository {

    suspend fun getGamesByDate(
        date: Date
    ): CustomResultModelDomain<List<GameModelDomain>, NbaApiExceptionModelDomain>

    suspend fun getGamesBySeasonAndLeague(
        season: SeasonModelDomain?,
        league: LeagueModelDomain?
    ): CustomResultModelDomain<List<GameModelDomain>, NbaApiExceptionModelDomain>

    suspend fun getGamesByDateAndSeasonAndLeague(
        date: Date,
        league: LeagueModelDomain?,
        season: SeasonModelDomain?
    ): CustomResultModelDomain<List<GameModelDomain>, NbaApiExceptionModelDomain>

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

    suspend fun getAllSeasons(): CustomResultModelDomain<List<SeasonModelDomain>, NbaApiExceptionModelDomain>

    suspend fun getAllCountries(): CustomResultModelDomain<List<CountryModelDomain>, NbaApiExceptionModelDomain>

    suspend fun getLeaguesByCountry(
        country: CountryModelDomain
    ): CustomResultModelDomain<List<LeagueModelDomain>, NbaApiExceptionModelDomain>

}