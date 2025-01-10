package com.alenniboris.nba_app.data.repository

import android.util.Log
import com.alenniboris.nba_app.data.model.toModelDomain
import com.alenniboris.nba_app.data.source.api.INbaApiService
import com.alenniboris.nba_app.data.source.api.model.ApiResponse
import com.alenniboris.nba_app.data.source.api.model.CountriesResponseErrorsModelData
import com.alenniboris.nba_app.data.source.api.model.GameResponseModel
import com.alenniboris.nba_app.data.source.api.model.GamesResponseErrorsModelData
import com.alenniboris.nba_app.data.source.api.model.LeaguesResponseErrorsModelData
import com.alenniboris.nba_app.data.source.api.model.PlayerResponseErrorsModelData
import com.alenniboris.nba_app.data.source.api.model.PlayerResponseModel
import com.alenniboris.nba_app.data.source.api.model.TeamResponseErrorsModelData
import com.alenniboris.nba_app.data.source.api.model.TeamResponseModel
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.GameModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.TeamModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.repository.INbaApiRepository
import com.alenniboris.nba_app.domain.utils.GsonUtil.fromJson
import com.alenniboris.nba_app.domain.utils.GsonUtil.toJson
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NbaApiRepositoryImpl(
    private val apiService: INbaApiService,
    private val dispatchers: IAppDispatchers
) : INbaApiRepository {

    private suspend fun <T, E, V> getDataFromApi(
        apiCall: suspend () -> ApiResponse<T>,
        transform: (List<T?>?) -> List<E>?,
        errorsParser: (String?) -> V?
    ): CustomResultModelDomain<List<E>, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            runCatching {
                val response = apiCall()

                if (response.isSomePropertyNotReceived) {
                    return@runCatching CustomResultModelDomain.Error(
                        NbaApiExceptionModelDomain.SomeUnknownExceptionOccurred
                    )
                }

                runCatching {
                    response.responseErrors?.toJson()?.let {
                        errorsParser(it)
                    }
                }.getOrNull()?.let {
                    return@runCatching CustomResultModelDomain.Error(
                        NbaApiExceptionModelDomain.SomeUnknownExceptionOccurred
                    )
                }

                val resultList = transform(response.responseList)
                    ?: return@runCatching CustomResultModelDomain.Error(
                        NbaApiExceptionModelDomain.SomeUnknownExceptionOccurred
                    )

                CustomResultModelDomain.Success<List<E>, NbaApiExceptionModelDomain>(resultList)

            }.getOrElse { exception ->
                Log.e("NbaApiRepositoryImpl", exception.stackTraceToString())
                CustomResultModelDomain.Error(NbaApiExceptionModelDomain.SomeUnknownExceptionOccurred)
            }
        }

    private suspend fun getGames(
        apiCall: suspend () -> GameResponseModel
    ) = withContext(dispatchers.IO) {
        return@withContext getDataFromApi(
            apiCall = apiCall,
            transform = { responseList ->
                responseList?.mapNotNull { it?.toModelDomain() }
            },
            errorsParser = { json ->
                json?.fromJson<GamesResponseErrorsModelData>()
            }
        )
    }

    private suspend fun getTeams(
        apiCall: suspend () -> TeamResponseModel
    ) = withContext(dispatchers.IO){
        return@withContext getDataFromApi(
            apiCall = apiCall,
            transform = { responseList ->
                responseList?.mapNotNull { it?.toModelDomain() }
            },
            errorsParser = { json ->
                json?.fromJson<TeamResponseErrorsModelData>()
            }
        )
    }

    private suspend fun getPlayers(
        apiCall: suspend () -> PlayerResponseModel
    ) = withContext(dispatchers.IO){
        return@withContext getDataFromApi(
            apiCall = apiCall,
            transform = { responseList ->
                responseList?.mapNotNull { it?.toModelDomain() }
            },
            errorsParser = { json ->
                json?.fromJson<PlayerResponseErrorsModelData>()
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

    override suspend fun getGamesByDateAndSeasonAndLeague(
        date: Date,
        league: LeagueModelDomain?,
        season: SeasonModelDomain?
    ): CustomResultModelDomain<List<GameModelDomain>, NbaApiExceptionModelDomain> {
        if (season == null || league == null) {
            return CustomResultModelDomain.Error(
                NbaApiExceptionModelDomain.LeagueAndSeasonFieldIsNeededToBeFilledBoth
            )
        }

        val dateString: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            .format(date)

        return getGames {
            apiService.getGamesByDateAndSeasonAndLeague(
                date = dateString,
                leagueId = league.id,
                season = season.name
            )
        }
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

        return getTeams{
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

        return getTeams{
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

        return getTeams{
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

        return getTeams{
            apiService.getTeamsBySearchQueryAndSeasonAndLeagueAndCountry(
                searchQuery = searchQuery,
                season = season.name,
                leagueId = league.id,
                country = country.name
            )
        }
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

    override suspend fun getAllSeasons(): CustomResultModelDomain<List<SeasonModelDomain>, NbaApiExceptionModelDomain> =
        getDataFromApi(
            apiCall = {
                apiService.getAllSeasons()
            },
            transform = { responseList ->
                responseList?.mapIndexedNotNull { index, value ->
                    SeasonModelDomain(
                        id = index,
                        name = value!!
                    )
                }
            },
            errorsParser = { json ->
                json?.fromJson<GamesResponseErrorsModelData>()
            }
        )

    override suspend fun getAllCountries(): CustomResultModelDomain<List<CountryModelDomain>, NbaApiExceptionModelDomain> =
        getDataFromApi(
            apiCall = {
                apiService.getAllCountries()
            },
            transform = { responseList ->
                responseList?.mapNotNull { it?.toModelDomain() }
            },
            errorsParser = { json ->
                json?.fromJson<CountriesResponseErrorsModelData>()
            }
        )


    override suspend fun getLeaguesByCountry(country: CountryModelDomain): CustomResultModelDomain<List<LeagueModelDomain>, NbaApiExceptionModelDomain> =
        getDataFromApi(
            apiCall = {
                apiService.getLeaguesByCountry(
                    countryId = country.id
                )
            },
            transform = { responseList ->
                responseList?.mapNotNull { it?.toModelDomain() }
            },
            errorsParser = { json ->
                json?.fromJson<LeaguesResponseErrorsModelData>()
            }
        )

}