package com.alenniboris.nba_app.data.source.remote.api.nba

import android.app.Application
import com.alenniboris.nba_app.BuildConfig
import com.alenniboris.nba_app.data.source.remote.MyRetrofit
import com.alenniboris.nba_app.data.source.remote.api.nba.model.CountriesResponseModel
import com.alenniboris.nba_app.data.source.remote.api.nba.model.GameResponseModel
import com.alenniboris.nba_app.data.source.remote.api.nba.model.LeaguesResponseModel
import com.alenniboris.nba_app.data.source.remote.api.nba.model.PlayerResponseModel
import com.alenniboris.nba_app.data.source.remote.api.nba.model.SeasonsResponseModel
import com.alenniboris.nba_app.data.source.remote.api.nba.model.TeamResponseModel
import com.andretietz.retrofit.ResponseCache
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface INbaApiService {

    @GET(NbaApiValues.GAME_REQUEST)
    @Headers("x-rapidapi-key:${BuildConfig.API_KEY}")
    suspend fun getGamesByDate(
        @Query(NbaApiValues.DATE_PARAMETER) date: String
    ): GameResponseModel

    @GET(NbaApiValues.GAME_REQUEST)
    @Headers("x-rapidapi-key:${BuildConfig.API_KEY}")
    suspend fun getGamesBySeasonAndLeague(
        @Query(NbaApiValues.LEAGUE_PARAMETER) leagueId: Int,
        @Query(NbaApiValues.SEASON_PARAMETER) season: String,
    ): GameResponseModel

    @GET(NbaApiValues.TEAM_REQUEST)
    @Headers("x-rapidapi-key:${BuildConfig.API_KEY}")
    suspend fun getTeamsByCountry(
        @Query(NbaApiValues.COUNTRY_ID_PARAMETER) countryId: Int
    ): TeamResponseModel

    @GET(NbaApiValues.TEAM_REQUEST)
    @Headers("x-rapidapi-key:${BuildConfig.API_KEY}")
    suspend fun getTeamsBySearchQuery(
        @Query(NbaApiValues.SEARCH_PARAMETER) searchQuery: String
    ): TeamResponseModel

    @GET(NbaApiValues.TEAM_REQUEST)
    @Headers("x-rapidapi-key:${BuildConfig.API_KEY}")
    suspend fun getTeamsBySeasonAndLeague(
        @Query(NbaApiValues.SEASON_PARAMETER) season: String,
        @Query(NbaApiValues.LEAGUE_PARAMETER) leagueId: Int
    ): TeamResponseModel

    @GET(NbaApiValues.TEAM_REQUEST)
    @Headers("x-rapidapi-key:${BuildConfig.API_KEY}")
    suspend fun getTeamsBySearchQueryAndSeasonAndLeague(
        @Query(NbaApiValues.SEARCH_PARAMETER) searchQuery: String,
        @Query(NbaApiValues.SEASON_PARAMETER) season: String,
        @Query(NbaApiValues.LEAGUE_PARAMETER) leagueId: Int
    ): TeamResponseModel

    @GET(NbaApiValues.TEAM_REQUEST)
    @Headers("x-rapidapi-key:${BuildConfig.API_KEY}")
    suspend fun getTeamsBySearchQueryAndSeasonAndLeagueAndCountry(
        @Query(NbaApiValues.SEARCH_PARAMETER) searchQuery: String,
        @Query(NbaApiValues.SEASON_PARAMETER) season: String,
        @Query(NbaApiValues.LEAGUE_PARAMETER) leagueId: Int,
        @Query(NbaApiValues.COUNTRY_PARAMETER) country: String
    ): TeamResponseModel

    @GET(NbaApiValues.PLAYER_REQUEST)
    @Headers("x-rapidapi-key:${BuildConfig.API_KEY}")
    suspend fun getPlayersBySearchQuery(
        @Query(NbaApiValues.SEARCH_PARAMETER) searchQuery: String
    ): PlayerResponseModel

    @GET(NbaApiValues.PLAYER_REQUEST)
    @Headers("x-rapidapi-key:${BuildConfig.API_KEY}")
    suspend fun getPlayersBySeasonAndTeam(
        @Query(NbaApiValues.SEASON_PARAMETER) season: String,
        @Query(NbaApiValues.TEAM_PARAMETER) teamId: Int
    ): PlayerResponseModel

    @GET(NbaApiValues.PLAYER_REQUEST)
    @Headers("x-rapidapi-key:${BuildConfig.API_KEY}")
    suspend fun getPlayersBySearchQueryAndSeasonAndTeam(
        @Query(NbaApiValues.SEARCH_PARAMETER) searchQuery: String,
        @Query(NbaApiValues.SEASON_PARAMETER) season: String,
        @Query(NbaApiValues.TEAM_PARAMETER) teamId: Int
    ): PlayerResponseModel

    @GET(NbaApiValues.SEASON_REQUEST)
    @ResponseCache(60, TimeUnit.MINUTES)
    @Headers("x-rapidapi-key:${BuildConfig.API_KEY}")
    suspend fun getAllSeasons(): SeasonsResponseModel

    @GET(NbaApiValues.COUNTRY_REQUEST)
    @ResponseCache(60, TimeUnit.MINUTES)
    @Headers("x-rapidapi-key:${BuildConfig.API_KEY}")
    suspend fun getAllCountries(): CountriesResponseModel

    @GET(NbaApiValues.LEAGUE_REQUEST)
    @ResponseCache(60, TimeUnit.MINUTES)
    @Headers("x-rapidapi-key:${BuildConfig.API_KEY}")
    suspend fun getLeaguesByCountry(
        @Query(NbaApiValues.COUNTRY_ID_PARAMETER) countryId: Int
    ): LeaguesResponseModel

    companion object {
        fun get(
            apl: Application
        ) = MyRetrofit().create<INbaApiService>(
            apl = apl,
            url = NbaApiValues.BASE_URL_NBA_API
        )
    }

}