package com.alenniboris.nba_app.data.repository.network.api.nba

import com.alenniboris.nba_app.data.model.toModelDomain
import com.alenniboris.nba_app.data.source.remote.api.nba.INbaApiService
import com.alenniboris.nba_app.data.source.remote.api.nba.model.GameResponseModel
import com.alenniboris.nba_app.data.source.remote.api.nba.model.GamesResponseErrorsModelData
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.GameModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiGamesNetworkRepository
import com.alenniboris.nba_app.domain.utils.GsonUtil.fromJson
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NbaApiGamesNetworkRepositoryImpl(
    private val apiService: INbaApiService,
    private val dispatchers: IAppDispatchers
) : INbaApiGamesNetworkRepository {

    private suspend fun getGames(
        apiCall: suspend () -> GameResponseModel
    ) = withContext(dispatchers.IO) {
        return@withContext NbaApiNetworkRepositoryFunctions.getDataFromApi(
            apiCall = apiCall,
            dispatcher = dispatchers.IO,
            transform = { responseList ->
                responseList?.mapNotNull { game ->
                    game?.toModelDomain()?.copy(isFollowed = false)
                }
            },
            errorsParser = { json ->
                json?.fromJson<GamesResponseErrorsModelData>()
            }
        )
    }

    override suspend fun getGamesByDate(
        date: Date
    ): CustomResultModelDomain<List<GameModelDomain>, NbaApiExceptionModelDomain> =
        getGames {
            val dateString: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(date)

            apiService.getGamesByDate(
                date = dateString
            )
        }

    override suspend fun getGamesBySeasonAndLeague(
        season: SeasonModelDomain?,
        league: LeagueModelDomain?
    ): CustomResultModelDomain<List<GameModelDomain>, NbaApiExceptionModelDomain> {
        if (season == null || league == null) {
            return CustomResultModelDomain.Error(
                NbaApiExceptionModelDomain.LeagueAndSeasonFieldIsNeededToBeFilledBoth
            )
        }

        return getGames {
            apiService.getGamesBySeasonAndLeague(
                leagueId = league.id,
                season = season.name
            )
        }
    }

}