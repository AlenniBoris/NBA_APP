package com.alenniboris.nba_app.data.source.remote.api.nba

import android.app.Application
import com.alenniboris.nba_app.BuildConfig
import com.alenniboris.nba_app.data.source.remote.MyRetrofit
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.team.TeamResponseModel
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.team.TeamStatisticsResponseModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface INbaApiTeamService {

    @GET(NbaApiValues.TEAM_STATISTICS_REQUEST)
    @Headers(NbaApiValues.API_HEADER + BuildConfig.API_KEY)
    suspend fun getTeamStatisticsByTeamIdLeagueSeason(
        @Query(NbaApiValues.TEAM_PARAMETER) teamId: String,
        @Query(NbaApiValues.LEAGUE_PARAMETER) leagueId: String,
        @Query(NbaApiValues.SEASON_PARAMETER) season: String
    ): TeamStatisticsResponseModel

    @GET(NbaApiValues.TEAM_REQUEST)
    @Headers(NbaApiValues.API_HEADER + BuildConfig.API_KEY)
    suspend fun getDataForTeamById(
        @Query(NbaApiValues.ID_PARAMETER) teamId: Int
    ): TeamResponseModel

    @GET(NbaApiValues.TEAM_REQUEST)
    @Headers(NbaApiValues.API_HEADER + BuildConfig.API_KEY)
    suspend fun getTeamsByCountry(
        @Query(NbaApiValues.COUNTRY_ID_PARAMETER) countryId: Int
    ): TeamResponseModel

    @GET(NbaApiValues.TEAM_REQUEST)
    @Headers(NbaApiValues.API_HEADER + BuildConfig.API_KEY)
    suspend fun getTeamsBySearchQuery(
        @Query(NbaApiValues.SEARCH_PARAMETER) searchQuery: String
    ): TeamResponseModel

    @GET(NbaApiValues.TEAM_REQUEST)
    @Headers(NbaApiValues.API_HEADER + BuildConfig.API_KEY)
    suspend fun getTeamsBySeasonAndLeague(
        @Query(NbaApiValues.SEASON_PARAMETER) season: String,
        @Query(NbaApiValues.LEAGUE_PARAMETER) leagueId: Int
    ): TeamResponseModel

    @GET(NbaApiValues.TEAM_REQUEST)
    @Headers(NbaApiValues.API_HEADER + BuildConfig.API_KEY)
    suspend fun getTeamsBySearchQueryAndSeasonAndLeague(
        @Query(NbaApiValues.SEARCH_PARAMETER) searchQuery: String,
        @Query(NbaApiValues.SEASON_PARAMETER) season: String,
        @Query(NbaApiValues.LEAGUE_PARAMETER) leagueId: Int
    ): TeamResponseModel

    @GET(NbaApiValues.TEAM_REQUEST)
    @Headers(NbaApiValues.API_HEADER + BuildConfig.API_KEY)
    suspend fun getTeamsBySearchQueryAndSeasonAndLeagueAndCountry(
        @Query(NbaApiValues.SEARCH_PARAMETER) searchQuery: String,
        @Query(NbaApiValues.SEASON_PARAMETER) season: String,
        @Query(NbaApiValues.LEAGUE_PARAMETER) leagueId: Int,
        @Query(NbaApiValues.COUNTRY_PARAMETER) countryId: Int
    ): TeamResponseModel

    companion object {
        fun get(
            apl: Application
        ) = MyRetrofit().create<INbaApiTeamService>(
            apl = apl,
            url = NbaApiValues.BASE_URL_NBA_API
        )
    }

}