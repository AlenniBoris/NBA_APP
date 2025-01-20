package com.alenniboris.nba_app.data.repository.network.api.nba

import com.alenniboris.nba_app.data.model.toModelDomain
import com.alenniboris.nba_app.data.source.remote.api.nba.INbaApiService
import com.alenniboris.nba_app.data.source.remote.api.nba.model.TeamResponseErrorsModelData
import com.alenniboris.nba_app.data.source.remote.api.nba.model.TeamResponseModel
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.TeamModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiTeamsNetworkRepository
import com.alenniboris.nba_app.domain.utils.GsonUtil.fromJson
import kotlinx.coroutines.withContext

class NbaApiTeamsNetworkRepositoryImpl(
    private val apiService: INbaApiService,
    private val dispatchers: IAppDispatchers
) : INbaApiTeamsNetworkRepository {

    private suspend fun getTeams(
        apiCall: suspend () -> TeamResponseModel
    ) = withContext(dispatchers.IO) {
        return@withContext NbaApiNetworkRepositoryFunctions.getDataFromApi(
            apiCall = apiCall,
            dispatcher = dispatchers.IO,
            transform = { responseList ->
                responseList?.mapNotNull { team ->
                    team?.toModelDomain()?.copy(isFollowed = false)
                }
            },
            errorsParser = { json ->
                json?.fromJson<TeamResponseErrorsModelData>()
            }
        )
    }

    override suspend fun getTeamsByCountry(
        country: CountryModelDomain?
    ): CustomResultModelDomain<List<TeamModelDomain>, NbaApiExceptionModelDomain> {
        if (country == null) {
            return CustomResultModelDomain.Error(
                NbaApiExceptionModelDomain.CountryIsNotSelectedForThisQuery
            )
        }

        return getTeams {
            apiService.getTeamsByCountry(
                countryId = country.id
            )
        }
    }

    override suspend fun getTeamsBySearchQuery(
        searchQuery: String
    ): CustomResultModelDomain<List<TeamModelDomain>, NbaApiExceptionModelDomain> {
        if (searchQuery.length < 3) {
            return CustomResultModelDomain.Error(
                NbaApiExceptionModelDomain.SearchQuerySizeToSmall
            )
        }

        return getTeams {
            apiService.getTeamsBySearchQuery(
                searchQuery = searchQuery
            )
        }
    }

    override suspend fun getTeamsBySeasonAndLeague(
        season: SeasonModelDomain?,
        league: LeagueModelDomain?
    ): CustomResultModelDomain<List<TeamModelDomain>, NbaApiExceptionModelDomain> {
        if (season == null || league == null) {
            return CustomResultModelDomain.Error(
                NbaApiExceptionModelDomain.LeagueAndSeasonFieldIsNeededToBeFilledBoth
            )
        }

        return getTeams {
            apiService.getTeamsBySeasonAndLeague(
                season = season.name,
                leagueId = league.id
            )
        }
    }

    override suspend fun getTeamsBySearchQueryAndSeasonAndLeague(
        searchQuery: String,
        season: SeasonModelDomain?,
        league: LeagueModelDomain?
    ): CustomResultModelDomain<List<TeamModelDomain>, NbaApiExceptionModelDomain> {
        if (searchQuery.length < 3) {
            return CustomResultModelDomain.Error(
                NbaApiExceptionModelDomain.SearchQuerySizeToSmall
            )
        }

        if (season == null || league == null) {
            return CustomResultModelDomain.Error(
                NbaApiExceptionModelDomain.LeagueAndSeasonFieldIsNeededToBeFilledBoth
            )
        }

        return getTeams {
            apiService.getTeamsBySearchQueryAndSeasonAndLeague(
                searchQuery = searchQuery,
                season = season.name,
                leagueId = league.id
            )
        }
    }

    override suspend fun getTeamsBySearchQueryAndSeasonAndLeagueAndCountry(
        searchQuery: String,
        season: SeasonModelDomain?,
        league: LeagueModelDomain?,
        country: CountryModelDomain?
    ): CustomResultModelDomain<List<TeamModelDomain>, NbaApiExceptionModelDomain> {
        if (searchQuery.length < 3) {
            return CustomResultModelDomain.Error(
                NbaApiExceptionModelDomain.SearchQuerySizeToSmall
            )
        }

        if (country == null) {
            return CustomResultModelDomain.Error(
                NbaApiExceptionModelDomain.CountryIsNotSelectedForThisQuery
            )
        }

        if (season == null || league == null) {
            return CustomResultModelDomain.Error(
                NbaApiExceptionModelDomain.LeagueAndSeasonFieldIsNeededToBeFilledBoth
            )
        }

        return getTeams {
            apiService.getTeamsBySearchQueryAndSeasonAndLeagueAndCountry(
                searchQuery = searchQuery,
                season = season.name,
                leagueId = league.id,
                country = country.name
            )
        }
    }

}