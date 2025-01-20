package com.alenniboris.nba_app.domain.manager.impl

import com.alenniboris.nba_app.domain.manager.INbaApiManager
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.GameModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.IStateModel
import com.alenniboris.nba_app.domain.model.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.TeamModelDomain
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
import com.alenniboris.nba_app.domain.repository.authentication.IAuthenticationRepository
import com.alenniboris.nba_app.domain.repository.database.api.nba.INbaApiGamesDatabaseRepository
import com.alenniboris.nba_app.domain.repository.database.api.nba.INbaApiPlayersDatabaseRepository
import com.alenniboris.nba_app.domain.repository.database.api.nba.INbaApiTeamsDatabaseRepository
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiCountriesNetworkRepository
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiGamesNetworkRepository
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiLeaguesNetworkRepository
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiPlayersNetworkRepository
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiSeasonsNetworkRepository
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiTeamsNetworkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.withContext

@OptIn(ExperimentalCoroutinesApi::class)
class NbaApiManagerImpl(
    private val nbaApiGamesNetworkRepository: INbaApiGamesNetworkRepository,
    private val nbaApiTeamsNetworkRepository: INbaApiTeamsNetworkRepository,
    private val nbaApiPlayersNetworkRepository: INbaApiPlayersNetworkRepository,
    private val nbaApiSeasonsNetworkRepository: INbaApiSeasonsNetworkRepository,
    private val nbaApiCountriesNetworkRepository: INbaApiCountriesNetworkRepository,
    private val nbaApiLeaguesNetworkRepository: INbaApiLeaguesNetworkRepository,
    private val nbaApiGamesDatabaseRepository: INbaApiGamesDatabaseRepository,
    private val nbaApiTeamsDatabaseRepository: INbaApiTeamsDatabaseRepository,
    private val nbaApiPlayersDatabaseRepository: INbaApiPlayersDatabaseRepository,
    private val authRepository: IAuthenticationRepository,
    private val dispatchers: IAppDispatchers
) : INbaApiManager {

    private val userFlow = authRepository.user

    override val followedGames = userFlow
        .flatMapLatest {
            it?.let { user ->
                nbaApiGamesDatabaseRepository.getAllGamesForUser(user)
            } ?: emptyFlow()
        }
        .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
        .distinctUntilChanged()
        .shareIn(
            scope = CoroutineScope(SupervisorJob() + dispatchers.IO),
            started = SharingStarted.WhileSubscribed(20_000, 0),
            replay = 1
        )

    override val followedTeams = userFlow
        .flatMapLatest {
            it?.let { user ->
                nbaApiTeamsDatabaseRepository.getAllTeamsForUser(user)
            } ?: emptyFlow()
        }
        .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
        .distinctUntilChanged()
        .shareIn(
            scope = CoroutineScope(SupervisorJob() + dispatchers.IO),
            started = SharingStarted.WhileSubscribed(20_000, 0),
            replay = 1
        )

    override val followedPlayers = userFlow
        .flatMapLatest {
            it?.let { user ->
                nbaApiPlayersDatabaseRepository.getAllPlayersForUser(user)
            } ?: emptyFlow()
        }
        .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
        .distinctUntilChanged()
        .shareIn(
            scope = CoroutineScope(SupervisorJob() + dispatchers.IO),
            started = SharingStarted.WhileSubscribed(20_000, 0),
            replay = 1
        )


    private suspend fun mapComingResult(
        res: CustomResultModelDomain<List<IStateModel>, NbaApiExceptionModelDomain>
    ): CustomResultModelDomain<List<IStateModel>, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext when (res) {
                is CustomResultModelDomain.Success -> {
                    val firstEl = res.result.firstOrNull()

                    firstEl?.let {
                        val followed =
                            when (firstEl) {
                                is GameModelDomain -> followedGames.firstOrNull().orEmpty()
                                    .map { it.gameId }

                                is PlayerModelDomain -> followedPlayers.firstOrNull().orEmpty()
                                    .map { it.playerId }

                                is TeamModelDomain -> followedTeams.firstOrNull().orEmpty()
                                    .map { it.teamId }
                            }

                        return@withContext CustomResultModelDomain.Success(
                            res.result.map {
                                if (followed.contains(it.id)) {
                                    when (it) {
                                        is GameModelDomain -> it.copy(isFollowed = true)

                                        is PlayerModelDomain -> it.copy(isFollowed = true)
                                        is TeamModelDomain -> it.copy(isFollowed = true)
                                    }
                                } else it
                            }
                        )
                    }

                    return@withContext res
                }

                is CustomResultModelDomain.Error -> res
            }
        }

    override suspend fun makeRequestForListOfElements(
        requestParameters: INbaApiRequestParams
    ): CustomResultModelDomain<List<IStateModel>, NbaApiExceptionModelDomain>? =
        withContext(dispatchers.IO) {
            return@withContext when (requestParameters.requestType) {
                NbaApiGameRequestType.GAMES_DATE -> {

                    (requestParameters as? GameRequestParamsModelDomain)?.let {
                        val res = nbaApiGamesNetworkRepository.getGamesByDate(
                            date = requestParameters.requestedDate
                        )
                        mapComingResult(res)
                    }

                }

                NbaApiGameRequestType.GAMES_SEASON_LEAGUE -> {

                    (requestParameters as? GameRequestParamsModelDomain)?.let {
                        val res = nbaApiGamesNetworkRepository.getGamesBySeasonAndLeague(
                            season = requestParameters.requestedSeason,
                            league = requestParameters.requestedLeague
                        )
                        mapComingResult(res)
                    }

                }

                NbaApiTeamRequestType.TEAMS_COUNTRY -> {

                    (requestParameters as? TeamRequestParamsModelDomain)?.let {
                        val res = nbaApiTeamsNetworkRepository.getTeamsByCountry(
                            country = requestParameters.requestedCountry
                        )
                        mapComingResult(res)
                    }

                }

                NbaApiTeamRequestType.TEAMS_SEARCH -> {

                    (requestParameters as? TeamRequestParamsModelDomain)?.let {
                        val res = nbaApiTeamsNetworkRepository.getTeamsBySearchQuery(
                            searchQuery = requestParameters.requestedQuery
                        )
                        mapComingResult(res)
                    }

                }

                NbaApiTeamRequestType.TEAMS_SEASON_LEAGUE -> {
                    val parameters = requestParameters as TeamRequestParamsModelDomain
                    val res = nbaApiTeamsNetworkRepository.getTeamsBySeasonAndLeague(
                        season = parameters.requestedSeason,
                        league = parameters.requestedLeague
                    )
                    mapComingResult(res)
                }

                NbaApiTeamRequestType.TEAMS_SEARCH_SEASON_LEAGUE -> {

                    (requestParameters as? TeamRequestParamsModelDomain)?.let {
                        val res =
                            nbaApiTeamsNetworkRepository.getTeamsBySearchQueryAndSeasonAndLeague(
                                searchQuery = requestParameters.requestedQuery,
                                season = requestParameters.requestedSeason,
                                league = requestParameters.requestedLeague
                            )
                        mapComingResult(res)
                    }

                }

                NbaApiTeamRequestType.TEAMS_SEARCH_SEASON_LEAGUE_COUNTRY -> {

                    (requestParameters as? TeamRequestParamsModelDomain)?.let {
                        val res =
                            nbaApiTeamsNetworkRepository.getTeamsBySearchQueryAndSeasonAndLeagueAndCountry(
                                searchQuery = requestParameters.requestedQuery,
                                season = requestParameters.requestedSeason,
                                league = requestParameters.requestedLeague,
                                country = requestParameters.requestedCountry
                            )
                        mapComingResult(res)
                    }

                }

                NbaApiPlayerRequestType.PLAYER_SEARCH -> {

                    (requestParameters as? PlayerRequestParamsModelDomain)?.let {
                        val res = nbaApiPlayersNetworkRepository.getPlayersBySearchQuery(
                            searchQuery = requestParameters.requestedQuery,
                        )
                        mapComingResult(res)
                    }

                }

                NbaApiPlayerRequestType.PLAYER_SEASON_TEAM -> {

                    (requestParameters as? PlayerRequestParamsModelDomain)?.let {
                        val res = nbaApiPlayersNetworkRepository.getPlayersBySeasonAndTeam(
                            season = requestParameters.requestedSeason,
                            team = requestParameters.requestedTeam
                        )
                        mapComingResult(res)
                    }

                }

                NbaApiPlayerRequestType.PLAYER_SEASON_TEAM_SEARCH -> {

                    (requestParameters as? PlayerRequestParamsModelDomain)?.let {
                        val res =
                            nbaApiPlayersNetworkRepository.getPlayersBySearchQueryAndSeasonAndTeam(
                                searchQuery = requestParameters.requestedQuery,
                                season = requestParameters.requestedSeason,
                                team = requestParameters.requestedTeam
                            )
                        mapComingResult(res)
                    }

                }

            }
        }

    override suspend fun getAllSeasons():
            CustomResultModelDomain<List<SeasonModelDomain>, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext nbaApiSeasonsNetworkRepository.getAllSeasons()
        }

    override suspend fun getAllCountries():
            CustomResultModelDomain<List<CountryModelDomain>, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext nbaApiCountriesNetworkRepository.getAllCountries()
        }

    override suspend fun getLeaguesByCountry(country: CountryModelDomain):
            CustomResultModelDomain<List<LeagueModelDomain>, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext nbaApiLeaguesNetworkRepository.getLeaguesByCountry(
                country = country
            )
        }

    override suspend fun proceedElementIsFollowingUpdate(
        element: IStateModel
    ): CustomResultModelDomain<Unit, NbaApiExceptionModelDomain> = withContext(dispatchers.IO) {
        return@withContext when (element) {
            is GameModelDomain -> {
                if (element.isFollowed) {
                    nbaApiGamesDatabaseRepository.deleteGameFromDatabase(
                        game = element,
                        user = userFlow.value
                    )
                } else {
                    nbaApiGamesDatabaseRepository.addGameToDatabase(
                        game = element,
                        user = userFlow.value
                    )
                }
            }

            is PlayerModelDomain -> {
                if (element.isFollowed) nbaApiPlayersDatabaseRepository.deletePlayerFromDatabase(
                    player = element,
                    user = userFlow.value
                )
                else nbaApiPlayersDatabaseRepository.addPlayerToDatabase(
                    player = element,
                    user = userFlow.value
                )
            }

            is TeamModelDomain -> {
                if (element.isFollowed) nbaApiTeamsDatabaseRepository.deleteTeamFromDatabase(
                    team = element,
                    user = userFlow.value
                )
                else nbaApiTeamsDatabaseRepository.addTeamToDatabase(
                    team = element,
                    user = userFlow.value
                )
            }
        }

    }

}