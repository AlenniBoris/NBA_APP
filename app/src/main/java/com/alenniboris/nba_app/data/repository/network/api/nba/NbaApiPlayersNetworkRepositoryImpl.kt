package com.alenniboris.nba_app.data.repository.network.api.nba

import com.alenniboris.nba_app.data.model.api.nba.player.toModelDomain
import com.alenniboris.nba_app.data.source.remote.api.nba.INbaApiService
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.player.PlayerResponseErrorsModelData
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.player.PlayerResponseModel
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiPlayersNetworkRepository
import com.alenniboris.nba_app.domain.utils.GsonUtil.fromJson
import kotlinx.coroutines.withContext

class NbaApiPlayersNetworkRepositoryImpl(
    private val apiService: INbaApiService,
    private val dispatchers: IAppDispatchers
) : INbaApiPlayersNetworkRepository {

    private suspend fun getPlayers(
        apiCall: suspend () -> PlayerResponseModel
    ) = withContext(dispatchers.IO) {
        return@withContext NbaApiNetworkRepositoryFunctions.getDataFromApi(
            apiCall = apiCall,
            dispatcher = dispatchers.IO,
            transform = { responseList ->
                responseList?.mapNotNull { player ->
                    player?.toModelDomain()?.copy(isFollowed = false)
                }
            },
            errorsParser = { json ->
                json?.fromJson<PlayerResponseErrorsModelData>()
            }
        )
    }

    override suspend fun getPlayersBySearchQuery(
        searchQuery: String
    ): CustomResultModelDomain<List<PlayerModelDomain>, NbaApiExceptionModelDomain> {
        if (searchQuery.length < 3) {
            return CustomResultModelDomain.Error(
                NbaApiExceptionModelDomain.SearchQuerySizeToSmall
            )
        }

        return getPlayers {
            apiService.getPlayersBySearchQuery(
                searchQuery = searchQuery
            )
        }
    }

    override suspend fun getPlayersBySeasonAndTeam(
        season: SeasonModelDomain?,
        team: TeamModelDomain?
    ): CustomResultModelDomain<List<PlayerModelDomain>, NbaApiExceptionModelDomain> {
        if (season == null || team == null) {
            return CustomResultModelDomain.Error(
                NbaApiExceptionModelDomain.LeagueOrTeamIsNotChosen
            )
        }

        return getPlayers {
            apiService.getPlayersBySeasonAndTeam(
                season = season.name,
                teamId = team.id
            )
        }
    }

    override suspend fun getPlayersBySearchQueryAndSeasonAndTeam(
        searchQuery: String,
        season: SeasonModelDomain?,
        team: TeamModelDomain?
    ): CustomResultModelDomain<List<PlayerModelDomain>, NbaApiExceptionModelDomain> {
        if (searchQuery.length < 3) {
            return CustomResultModelDomain.Error(
                NbaApiExceptionModelDomain.SearchQuerySizeToSmall
            )
        }

        if (season == null || team == null) {
            return CustomResultModelDomain.Error(
                NbaApiExceptionModelDomain.LeagueOrTeamIsNotChosen
            )
        }

        return getPlayers {
            apiService.getPlayersBySearchQueryAndSeasonAndTeam(
                searchQuery = searchQuery,
                season = season.name,
                teamId = team.id
            )
        }
    }

}