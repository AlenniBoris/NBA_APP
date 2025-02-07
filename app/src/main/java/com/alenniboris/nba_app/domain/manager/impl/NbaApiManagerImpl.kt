package com.alenniboris.nba_app.domain.manager.impl

import com.alenniboris.nba_app.domain.manager.INbaApiManager
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.GameReloadingResult
import com.alenniboris.nba_app.domain.model.api.nba.IStateModel
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamReloadingResult
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.model.params.api.nba.GameRequestParamsModelDomain
import com.alenniboris.nba_app.domain.model.params.api.nba.INbaApiElementsRequestParams
import com.alenniboris.nba_app.domain.model.params.api.nba.NbaApiGameTypeElementsRequest
import com.alenniboris.nba_app.domain.model.params.api.nba.NbaApiPlayerTypeElementsRequest
import com.alenniboris.nba_app.domain.model.params.api.nba.NbaApiTeamTypeElementsRequest
import com.alenniboris.nba_app.domain.model.params.api.nba.PlayerRequestParamsModelDomain
import com.alenniboris.nba_app.domain.model.params.api.nba.TeamRequestParamsModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.GameStatisticsModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.PlayerStatisticsModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.TeamStatisticsModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.TeamsInGameStatisticsModelDomain
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
import kotlinx.coroutines.async
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

    override suspend fun reloadDataForTeamAndLoadLeagues(
        team: TeamModelDomain
    ): CustomResultModelDomain<TeamReloadingResult, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext when (
                val teamDataRes = nbaApiTeamsNetworkRepository.getDataForTeamById(id = team.id)
            ) {
                is CustomResultModelDomain.Success -> {
                    when (
                        val leaguesDataRes = nbaApiLeaguesNetworkRepository.getLeaguesByCountry(
                            country = teamDataRes.result.country
                        )
                    ) {
                        is CustomResultModelDomain.Success -> {

                            val followedIds =
                                followedTeams.firstOrNull().orEmpty().map { it.teamId }

                            CustomResultModelDomain.Success(
                                TeamReloadingResult(
                                    teamData = teamDataRes.result.copy(
                                        isFollowed = followedIds.contains(
                                            teamDataRes.result.id
                                        )
                                    ),
                                    leaguesData = leaguesDataRes.result
                                )
                            )
                        }

                        is CustomResultModelDomain.Error -> CustomResultModelDomain.Error<TeamReloadingResult, NbaApiExceptionModelDomain>(
                            leaguesDataRes.exception
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    CustomResultModelDomain.Error<TeamReloadingResult, NbaApiExceptionModelDomain>(
                        teamDataRes.exception
                    )
                }
            }
        }

    override suspend fun makeRequestForListOfElements(
        elementsRequestParameters: INbaApiElementsRequestParams
    ): CustomResultModelDomain<List<IStateModel>, NbaApiExceptionModelDomain>? =
        withContext(dispatchers.IO) {
            return@withContext when (elementsRequestParameters.elementsRequestType) {
                NbaApiGameTypeElementsRequest.GAMES_DATE -> {

                    (elementsRequestParameters as? GameRequestParamsModelDomain)?.let {
                        val res = nbaApiGamesNetworkRepository.getGamesByDate(
                            date = elementsRequestParameters.requestedDate
                        )
                        mapComingResult(res)
                    }

                }

                NbaApiGameTypeElementsRequest.GAMES_SEASON_LEAGUE -> {

                    (elementsRequestParameters as? GameRequestParamsModelDomain)?.let {
                        val res = nbaApiGamesNetworkRepository.getGamesBySeasonAndLeague(
                            season = elementsRequestParameters.requestedSeason,
                            league = elementsRequestParameters.requestedLeague
                        )
                        mapComingResult(res)
                    }

                }

                NbaApiTeamTypeElementsRequest.TEAMS_COUNTRY -> {

                    (elementsRequestParameters as? TeamRequestParamsModelDomain)?.let {
                        val res = nbaApiTeamsNetworkRepository.getTeamsByCountry(
                            country = elementsRequestParameters.requestedCountry
                        )
                        mapComingResult(res)
                    }

                }

                NbaApiTeamTypeElementsRequest.TEAMS_SEARCH -> {

                    (elementsRequestParameters as? TeamRequestParamsModelDomain)?.let {
                        val res = nbaApiTeamsNetworkRepository.getTeamsBySearchQuery(
                            searchQuery = elementsRequestParameters.requestedQuery
                        )
                        mapComingResult(res)
                    }

                }

                NbaApiTeamTypeElementsRequest.TEAMS_SEASON_LEAGUE -> {
                    val parameters = elementsRequestParameters as TeamRequestParamsModelDomain
                    val res = nbaApiTeamsNetworkRepository.getTeamsBySeasonAndLeague(
                        season = parameters.requestedSeason,
                        league = parameters.requestedLeague
                    )
                    mapComingResult(res)
                }

                NbaApiTeamTypeElementsRequest.TEAMS_SEARCH_SEASON_LEAGUE -> {

                    (elementsRequestParameters as? TeamRequestParamsModelDomain)?.let {
                        val res =
                            nbaApiTeamsNetworkRepository.getTeamsBySearchQueryAndSeasonAndLeague(
                                searchQuery = elementsRequestParameters.requestedQuery,
                                season = elementsRequestParameters.requestedSeason,
                                league = elementsRequestParameters.requestedLeague
                            )
                        mapComingResult(res)
                    }

                }

                NbaApiTeamTypeElementsRequest.TEAMS_SEARCH_SEASON_LEAGUE_COUNTRY -> {

                    (elementsRequestParameters as? TeamRequestParamsModelDomain)?.let {
                        val res =
                            nbaApiTeamsNetworkRepository.getTeamsBySearchQueryAndSeasonAndLeagueAndCountry(
                                searchQuery = elementsRequestParameters.requestedQuery,
                                season = elementsRequestParameters.requestedSeason,
                                league = elementsRequestParameters.requestedLeague,
                                country = elementsRequestParameters.requestedCountry
                            )
                        mapComingResult(res)
                    }

                }

                NbaApiPlayerTypeElementsRequest.PLAYER_SEARCH -> {

                    (elementsRequestParameters as? PlayerRequestParamsModelDomain)?.let {
                        val res = nbaApiPlayersNetworkRepository.getPlayersBySearchQuery(
                            searchQuery = elementsRequestParameters.requestedQuery,
                        )
                        mapComingResult(res)
                    }

                }

                NbaApiPlayerTypeElementsRequest.PLAYER_SEASON_TEAM -> {

                    (elementsRequestParameters as? PlayerRequestParamsModelDomain)?.let {
                        val res = nbaApiPlayersNetworkRepository.getPlayersBySeasonAndTeam(
                            season = elementsRequestParameters.requestedSeason,
                            team = elementsRequestParameters.requestedTeam
                        )
                        mapComingResult(res)
                    }

                }

                NbaApiPlayerTypeElementsRequest.PLAYER_SEASON_TEAM_SEARCH -> {

                    (elementsRequestParameters as? PlayerRequestParamsModelDomain)?.let {
                        val res =
                            nbaApiPlayersNetworkRepository.getPlayersBySearchQueryAndSeasonAndTeam(
                                searchQuery = elementsRequestParameters.requestedQuery,
                                season = elementsRequestParameters.requestedSeason,
                                team = elementsRequestParameters.requestedTeam
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

    override suspend fun requestForTeamsStatisticsInGame(
        game: GameModelDomain
    ): CustomResultModelDomain<TeamsInGameStatisticsModelDomain, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext nbaApiGamesNetworkRepository.getTeamsStatisticsInGame(game = game)
        }

    override suspend fun requestForTeamStatistics(
        team: TeamModelDomain,
        season: SeasonModelDomain?,
        league: LeagueModelDomain?
    ): CustomResultModelDomain<TeamStatisticsModelDomain, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext nbaApiTeamsNetworkRepository.getTeamStatisticsByTeamSeasonLeague(
                team = team,
                season = season,
                league = league
            )
        }

    override suspend fun requestForPlayersStatisticsInSeason(
        season: SeasonModelDomain,
        player: PlayerModelDomain
    ): CustomResultModelDomain<List<PlayerStatisticsModelDomain>, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext nbaApiPlayersNetworkRepository.requestForPlayersStatisticsInSeason(
                season = season,
                player = player
            )
        }

    override suspend fun getPlayerDataById(
        id: Int
    ): CustomResultModelDomain<PlayerModelDomain, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            val followedIds = followedPlayers.firstOrNull().orEmpty().map { it.playerId }
            return@withContext when (val res =
                nbaApiPlayersNetworkRepository.getPlayerDataById(id = id)) {
                is CustomResultModelDomain.Success -> {
                    CustomResultModelDomain.Success(
                        res.result.copy(
                            isFollowed = followedIds.contains(
                                res.result.id
                            )
                        )
                    )
                }

                is CustomResultModelDomain.Error -> {
                    CustomResultModelDomain.Error(res.exception)
                }
            }
        }

    override suspend fun getGameDataAndStatistics(
        gameId: Int
    ): CustomResultModelDomain<GameReloadingResult, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext when (
                val gameDataRes = nbaApiGamesNetworkRepository.getGameDataById(gameId)
            ) {
                is CustomResultModelDomain.Success -> {

                    val _teamsStatistics = async {
                        nbaApiGamesNetworkRepository.getTeamsStatisticsInGame(game = gameDataRes.result)
                    }

                    val _playersStatistics = async {
                        nbaApiGamesNetworkRepository.getGameStatisticsForPlayersInGame(game = gameDataRes.result)
                    }

                    val teamsStatistics = _teamsStatistics.await()
                    val playersStatistics = _playersStatistics.await()

                    if (
                        teamsStatistics is CustomResultModelDomain.Success &&
                        playersStatistics is CustomResultModelDomain.Success
                    ) {
                        return@withContext CustomResultModelDomain.Success(
                            GameReloadingResult(
                                game = gameDataRes.result,
                                statistics = GameStatisticsModelDomain(
                                    homeTeamStatistics = teamsStatistics.result.homeTeamStatistics,
                                    homePlayersStatistics = playersStatistics.result.homeTeamPlayersStatistics,
                                    visitorsTeamStatistics = teamsStatistics.result.visitorsTeamStatistics,
                                    visitorPlayersStatistics = playersStatistics.result.visitorsTeamPlayersStatistics,
                                )
                            )
                        )
                    }

                    return@withContext CustomResultModelDomain.Error(
                        NbaApiExceptionModelDomain.SomeUnknownExceptionOccurred
                    )
                }

                is CustomResultModelDomain.Error -> {
                    CustomResultModelDomain.Error(gameDataRes.exception)
                }
            }
        }

    override suspend fun getPlayersOfTeamInSeason(
        team: TeamModelDomain,
        season: SeasonModelDomain
    ): CustomResultModelDomain<List<PlayerModelDomain>, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext nbaApiPlayersNetworkRepository.getPlayersBySeasonAndTeam(
                team = team,
                season = season
            )
        }

}