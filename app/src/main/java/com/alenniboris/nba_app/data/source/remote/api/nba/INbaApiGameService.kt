package com.alenniboris.nba_app.data.source.remote.api.nba

import android.app.Application
import com.alenniboris.nba_app.BuildConfig
import com.alenniboris.nba_app.data.source.remote.MyRetrofit
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.game.GameResponseModel
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.game.GameStatisticsForPlayersResponseModel
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.game.GameStatisticsForTeamsResponseModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface INbaApiGameService {

    @GET(NbaApiValues.GAME_REQUEST)
    @Headers(NbaApiValues.API_HEADER + BuildConfig.API_KEY)
    suspend fun getGamesByDate(
        @Query(NbaApiValues.DATE_PARAMETER) date: String
    ): GameResponseModel

    @GET(NbaApiValues.GAME_REQUEST)
    @Headers(NbaApiValues.API_HEADER + BuildConfig.API_KEY)
    suspend fun getGamesBySeasonAndLeague(
        @Query(NbaApiValues.LEAGUE_PARAMETER) leagueId: Int,
        @Query(NbaApiValues.SEASON_PARAMETER) season: String,
    ): GameResponseModel

    @GET(NbaApiValues.GAME_STATISTICS_TEAMS_REQUEST)
    @Headers(NbaApiValues.API_HEADER + BuildConfig.API_KEY)
    suspend fun getGameStatisticsForTeamsByGameId(
        @Query(NbaApiValues.ID_PARAMETER) gameId: Int
    ): GameStatisticsForTeamsResponseModel

    @GET(NbaApiValues.GAME_STATISTICS_PLAYERS_REQUEST)
    @Headers(NbaApiValues.API_HEADER + BuildConfig.API_KEY)
    suspend fun getGameStatisticsForPlayersByGameId(
        @Query(NbaApiValues.ID_PARAMETER) gameId: Int
    ): GameStatisticsForPlayersResponseModel

    @GET(NbaApiValues.GAME_REQUEST)
    @Headers(NbaApiValues.API_HEADER + BuildConfig.API_KEY)
    suspend fun getDataForGameById(
        @Query(NbaApiValues.ID_PARAMETER) gameId: Int
    ): GameResponseModel

    companion object {
        fun get(
            apl: Application
        ) = MyRetrofit().create<INbaApiGameService>(
            apl = apl,
            url = NbaApiValues.BASE_URL_NBA_API
        )
    }

}