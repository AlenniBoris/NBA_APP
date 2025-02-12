package com.alenniboris.nba_app.data.source.remote.api.nba

import android.app.Application
import com.alenniboris.nba_app.BuildConfig
import com.alenniboris.nba_app.data.source.remote.MyRetrofit
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.game.GameStatisticsForPlayersResponseModel
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.player.PlayerResponseModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface INbaApiPlayerService {

    @GET(NbaApiValues.PLAYER_REQUEST)
    @Headers(NbaApiValues.API_HEADER + BuildConfig.API_KEY)
    suspend fun getPlayersBySearchQuery(
        @Query(NbaApiValues.SEARCH_PARAMETER) searchQuery: String
    ): PlayerResponseModel

    @GET(NbaApiValues.PLAYER_REQUEST)
    @Headers(NbaApiValues.API_HEADER + BuildConfig.API_KEY)
    suspend fun getPlayersBySeasonAndTeam(
        @Query(NbaApiValues.SEASON_PARAMETER) season: String,
        @Query(NbaApiValues.TEAM_PARAMETER) teamId: Int
    ): PlayerResponseModel

    @GET(NbaApiValues.PLAYER_REQUEST)
    @Headers(NbaApiValues.API_HEADER + BuildConfig.API_KEY)
    suspend fun getPlayersBySearchQueryAndSeasonAndTeam(
        @Query(NbaApiValues.SEARCH_PARAMETER) searchQuery: String,
        @Query(NbaApiValues.SEASON_PARAMETER) season: String,
        @Query(NbaApiValues.TEAM_PARAMETER) teamId: Int
    ): PlayerResponseModel

    @GET(NbaApiValues.PLAYER_REQUEST)
    @Headers(NbaApiValues.API_HEADER + BuildConfig.API_KEY)
    suspend fun getDataForPlayerById(
        @Query(NbaApiValues.ID_PARAMETER) playerId: Int
    ): PlayerResponseModel

    @GET(NbaApiValues.GAME_STATISTICS_PLAYERS_REQUEST)
    @Headers(NbaApiValues.API_HEADER + BuildConfig.API_KEY)
    suspend fun getStatisticsForPlayerInSeason(
        @Query(NbaApiValues.SEASON_PARAMETER) season: String,
        @Query(NbaApiValues.PLAYER_PARAMETER) playerId: Int
    ): GameStatisticsForPlayersResponseModel

    companion object {
        fun get(
            apl: Application
        ) = MyRetrofit().create<INbaApiPlayerService>(
            apl = apl,
            url = NbaApiValues.BASE_URL_NBA_API
        )
    }

}