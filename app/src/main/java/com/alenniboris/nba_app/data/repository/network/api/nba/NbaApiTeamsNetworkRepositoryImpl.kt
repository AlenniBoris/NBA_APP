package com.alenniboris.nba_app.data.repository.network.api.nba

import com.alenniboris.nba_app.data.mappers.toNbaApiExceptionModelDomain
import com.alenniboris.nba_app.data.model.api.nba.team.toModelDomain
import com.alenniboris.nba_app.data.source.remote.api.nba.INbaApiTeamService
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.team.TeamResponseErrorsModelData
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.team.TeamResponseModel
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.team.TeamStatisticsResponseErrorsModelData
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.TeamStatisticsModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiTeamsNetworkRepository
import com.alenniboris.nba_app.domain.utils.GsonUtil.fromJson
import com.alenniboris.nba_app.domain.utils.GsonUtil.toJson
import com.alenniboris.nba_app.domain.utils.LogPrinter
import kotlinx.coroutines.withContext

class NbaApiTeamsNetworkRepositoryImpl(
    private val apiService: INbaApiTeamService,
    private val dispatchers: IAppDispatchers
) : INbaApiTeamsNetworkRepository {

    private suspend fun getTeams(
        apiCall: suspend () -> TeamResponseModel
    ) = withContext(dispatchers.IO) {
        return@withContext NbaApiNetworkRepositoryFunctions.getElementsFromApi(
            apiCall = apiCall,
            dispatcher = dispatchers.IO,
            transform = { responseList ->
                responseList?.mapNotNull { team ->
                    team?.toModelDomain()?.copy(isFollowed = false)
                }?.filter { it.country != null }
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
                countryId = country.id
            )
        }
    }

    override suspend fun getTeamStatisticsByTeamSeasonLeague(
        team: TeamModelDomain,
        league: LeagueModelDomain?,
        season: SeasonModelDomain?
    ): CustomResultModelDomain<TeamStatisticsModelDomain, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {

            if (season == null || league == null) {
                return@withContext CustomResultModelDomain.Error(
                    NbaApiExceptionModelDomain.LeagueAndSeasonFieldIsNeededToBeFilledBoth
                )
            }

            return@withContext runCatching {
                val response = apiService.getTeamStatisticsByTeamIdLeagueSeason(
                    teamId = team.id.toString(),
                    leagueId = league.id.toString(),
                    season = season.name
                )

                if (response.isSomePropertyNotReceived) {
                    return@runCatching CustomResultModelDomain.Error(
                        NbaApiExceptionModelDomain.SomeUnknownExceptionOccurred
                    )
                }

                runCatching {
                    response.responseErrors?.toJson()
                        ?.fromJson<TeamStatisticsResponseErrorsModelData>()
                }.getOrNull()?.let {
                    return@runCatching CustomResultModelDomain.Error(
                        NbaApiExceptionModelDomain.TryAnotherSeason
                    )
                }

                val resultList = response.response?.toModelDomain()
                    ?: return@runCatching CustomResultModelDomain.Error(
                        NbaApiExceptionModelDomain.SomeUnknownExceptionOccurred
                    )

                CustomResultModelDomain.Success<TeamStatisticsModelDomain, NbaApiExceptionModelDomain>(
                    resultList
                )

            }.getOrElse { exception ->
                LogPrinter.printLog("NbaApiRepositoryImpl", exception.stackTraceToString())
                CustomResultModelDomain.Error(exception.toNbaApiExceptionModelDomain())
            }
        }

    override suspend fun getDataForTeamById(
        id: Int
    ): CustomResultModelDomain<TeamModelDomain, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext NbaApiNetworkRepositoryFunctions.getElementFromApi(
                apiCall = { apiService.getDataForTeamById(teamId = id) },
                dispatcher = dispatchers.IO,
                transform = { list ->
                    list?.firstNotNullOfOrNull { team ->
                        team?.toModelDomain()
                    }
                },
                errorsParser = { json ->
                    json?.fromJson<TeamResponseErrorsModelData>()
                }
            )
        }

}