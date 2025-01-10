package com.alenniboris.nba_app.domain.manager.impl

import com.alenniboris.nba_app.domain.manager.INbaApiManager
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.IStateModel
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.model.params.api.nba.GameRequestParamsModelDomain
import com.alenniboris.nba_app.domain.model.params.api.nba.INbaApiRequestParams
import com.alenniboris.nba_app.domain.model.params.api.nba.NbaApiGameRequestType
import com.alenniboris.nba_app.domain.model.params.api.nba.NbaApiPlayerRequestType
import com.alenniboris.nba_app.domain.model.params.api.nba.NbaApiTeamRequestType
import com.alenniboris.nba_app.domain.model.params.api.nba.PlayerRequestParamsModelDomain
import com.alenniboris.nba_app.domain.model.params.api.nba.TeamRequestParamsModelDomain
import com.alenniboris.nba_app.domain.repository.INbaApiRepository
import kotlinx.coroutines.withContext

class NbaApiManagerImpl(
    private val nbaApiRepository: INbaApiRepository,
    private val dispatchers: IAppDispatchers
) : INbaApiManager {

    override suspend fun makeRequestForListOfElements(
        requestParameters: INbaApiRequestParams
    ): CustomResultModelDomain<List<IStateModel>, NbaApiExceptionModelDomain>? =
        withContext(dispatchers.IO) {
            return@withContext when (requestParameters.requestType) {
                NbaApiGameRequestType.GAMES_DATE -> {

                    (requestParameters as? GameRequestParamsModelDomain)?.let {
                        nbaApiRepository.getGamesByDate(
                            date = requestParameters.requestedDate
                        )
                    }

                }

                NbaApiGameRequestType.GAMES_SEASON_LEAGUE -> {

                    (requestParameters as? GameRequestParamsModelDomain)?.let {
                        nbaApiRepository.getGamesBySeasonAndLeague(
                            season = requestParameters.requestedSeason,
                            league = requestParameters.requestedLeague
                        )
                    }

                }

                NbaApiGameRequestType.GAMES_DATE_SEASON_LEAGUE -> {

                    (requestParameters as? GameRequestParamsModelDomain)?.let {
                        nbaApiRepository.getGamesByDateAndSeasonAndLeague(
                            date = requestParameters.requestedDate,
                            season = requestParameters.requestedSeason,
                            league = requestParameters.requestedLeague
                        )
                    }

                }

                NbaApiTeamRequestType.TEAMS_COUNTRY -> {

                    (requestParameters as? TeamRequestParamsModelDomain)?.let {
                        nbaApiRepository.getTeamsByCountry(
                            country = requestParameters.requestedCountry
                        )
                    }

                }

                NbaApiTeamRequestType.TEAMS_SEARCH -> {

                    (requestParameters as? TeamRequestParamsModelDomain)?.let {
                        nbaApiRepository.getTeamsBySearchQuery(
                            searchQuery = requestParameters.requestedQuery
                        )
                    }

                }

                NbaApiTeamRequestType.TEAMS_SEASON_LEAGUE -> {
                    val parameters = requestParameters as TeamRequestParamsModelDomain
                    nbaApiRepository.getTeamsBySeasonAndLeague(
                        season = parameters.requestedSeason,
                        league = parameters.requestedLeague
                    )
                }

                NbaApiTeamRequestType.TEAMS_SEARCH_SEASON_LEAGUE -> {

                    (requestParameters as? TeamRequestParamsModelDomain)?.let {
                        nbaApiRepository.getTeamsBySearchQueryAndSeasonAndLeague(
                            searchQuery = requestParameters.requestedQuery,
                            season = requestParameters.requestedSeason,
                            league = requestParameters.requestedLeague
                        )
                    }

                }

                NbaApiTeamRequestType.TEAMS_SEARCH_SEASON_LEAGUE_COUNTRY -> {

                    (requestParameters as? TeamRequestParamsModelDomain)?.let {
                        nbaApiRepository.getTeamsBySearchQueryAndSeasonAndLeagueAndCountry(
                            searchQuery = requestParameters.requestedQuery,
                            season = requestParameters.requestedSeason,
                            league = requestParameters.requestedLeague,
                            country = requestParameters.requestedCountry
                        )
                    }

                }

                NbaApiPlayerRequestType.PLAYER_SEARCH -> {

                    (requestParameters as? PlayerRequestParamsModelDomain)?.let {
                        nbaApiRepository.getPlayersBySearchQuery(
                            searchQuery = requestParameters.requestedQuery,
                        )
                    }

                }

                NbaApiPlayerRequestType.PLAYER_SEASON_TEAM -> {

                    (requestParameters as? PlayerRequestParamsModelDomain)?.let {
                        nbaApiRepository.getPlayersBySeasonAndTeam(
                            season = requestParameters.requestedSeason,
                            team = requestParameters.requestedTeam
                        )
                    }

                }

                NbaApiPlayerRequestType.PLAYER_SEASON_TEAM_SEARCH -> {

                    (requestParameters as? PlayerRequestParamsModelDomain)?.let {
                        nbaApiRepository.getPlayersBySearchQueryAndSeasonAndTeam(
                            searchQuery = requestParameters.requestedQuery,
                            season = requestParameters.requestedSeason,
                            team = requestParameters.requestedTeam
                        )
                    }

                }

            }
        }

    override suspend fun getAllSeasons():
            CustomResultModelDomain<List<SeasonModelDomain>, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext nbaApiRepository.getAllSeasons()
        }

    override suspend fun getAllCountries():
            CustomResultModelDomain<List<CountryModelDomain>, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext nbaApiRepository.getAllCountries()
        }

    override suspend fun getLeaguesByCountry(country: CountryModelDomain):
            CustomResultModelDomain<List<LeagueModelDomain>, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext nbaApiRepository.getLeaguesByCountry(
                country = country
            )
        }

}