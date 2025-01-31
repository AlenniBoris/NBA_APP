package com.alenniboris.nba_app.data.repository.network.api.nba

import com.alenniboris.nba_app.data.mappers.toNbaApiExceptionModelDomain
import com.alenniboris.nba_app.data.model.api.nba.game.toModelDomain
import com.alenniboris.nba_app.data.model.api.nba.player.toModelDomain
import com.alenniboris.nba_app.data.source.remote.api.nba.INbaApiService
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.game.GamePlayerStatisticsErrorsModelData
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.game.GameResponseModel
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.game.GameTeamStatisticsErrorsModelData
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.game.GamesResponseErrorsModelData
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.PlayersInGameStatisticsModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.TeamsInGameStatisticsModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiGamesNetworkRepository
import com.alenniboris.nba_app.domain.utils.GsonUtil.fromJson
import com.alenniboris.nba_app.domain.utils.GsonUtil.toJson
import com.alenniboris.nba_app.domain.utils.LogPrinter
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
        return@withContext NbaApiNetworkRepositoryFunctions.getElementsFromApi(
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

    override suspend fun getTeamsStatisticsInGame(
        game: GameModelDomain
    ): CustomResultModelDomain<TeamsInGameStatisticsModelDomain, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            runCatching {
                val response = apiService.getGameStatisticsForTeamsByGameId(game.id.toString())

                if (response.isSomePropertyNotReceived) {
                    return@runCatching CustomResultModelDomain.Error(
                        NbaApiExceptionModelDomain.SomeUnknownExceptionOccurred
                    )
                }

                runCatching {
                    response.responseErrors?.toJson()
                        ?.fromJson<GameTeamStatisticsErrorsModelData>()
                }.getOrNull()?.let {
                    return@runCatching CustomResultModelDomain.Error(
                        NbaApiExceptionModelDomain.SomeUnknownExceptionOccurred
                    )
                }

                val listOfTeams = response.responseList?.mapNotNull { statistics ->
                    statistics?.toModelDomain()
                }

                val res = listOfTeams?.let {
                    TeamsInGameStatisticsModelDomain(
                        homeTeamStatistics = it.firstOrNull { teamsStatistics ->
                            teamsStatistics.teamId == game.homeTeam.id
                        },
                        visitorsTeamStatistics = it.firstOrNull { teamsStatistics ->
                            teamsStatistics.teamId == game.visitorsTeam.id
                        }
                    )
                }
                    ?: return@runCatching CustomResultModelDomain.Error<TeamsInGameStatisticsModelDomain, NbaApiExceptionModelDomain>(
                        NbaApiExceptionModelDomain.SomeUnknownExceptionOccurred
                    )

                return@withContext CustomResultModelDomain.Success<TeamsInGameStatisticsModelDomain, NbaApiExceptionModelDomain>(
                    res
                )

            }.getOrElse { exception ->
                LogPrinter.printLog("NbaApiRepositoryImpl", exception.stackTraceToString())
                return@withContext CustomResultModelDomain.Error(exception.toNbaApiExceptionModelDomain())
            }
        }


    override suspend fun getGameStatisticsForPlayersInGame(
        game: GameModelDomain
    ): CustomResultModelDomain<PlayersInGameStatisticsModelDomain, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {

            runCatching {
                val response = apiService.getGameStatisticsForPlayersByGameId(game.id.toString())

                if (response.isSomePropertyNotReceived) {
                    return@runCatching CustomResultModelDomain.Error(
                        NbaApiExceptionModelDomain.SomeUnknownExceptionOccurred
                    )
                }

                runCatching {
                    response.responseErrors?.toJson()
                        ?.fromJson<GamePlayerStatisticsErrorsModelData>()
                }.getOrNull()?.let {
                    return@runCatching CustomResultModelDomain.Error(
                        NbaApiExceptionModelDomain.SomeUnknownExceptionOccurred
                    )
                }

                val listOfPlayers = response.responseList?.mapNotNull { statistics ->
                    statistics?.toModelDomain()
                }

                val res = listOfPlayers?.let {
                    PlayersInGameStatisticsModelDomain(
                        homeTeamPlayersStatistics = it.filter { playerStatistics ->
                            playerStatistics.teamId == game.homeTeam.id
                        },
                        visitorsTeamPlayersStatistics = it.filter { playerStatistics ->
                            playerStatistics.teamId == game.visitorsTeam.id
                        }
                    )
                }
                    ?: return@runCatching CustomResultModelDomain.Error<PlayersInGameStatisticsModelDomain, NbaApiExceptionModelDomain>(
                        NbaApiExceptionModelDomain.SomeUnknownExceptionOccurred
                    )

                return@withContext CustomResultModelDomain.Success<PlayersInGameStatisticsModelDomain, NbaApiExceptionModelDomain>(
                    res
                )

            }.getOrElse { exception ->
                LogPrinter.printLog("NbaApiRepositoryImpl", exception.stackTraceToString())
                return@withContext CustomResultModelDomain.Error(exception.toNbaApiExceptionModelDomain())
            }

        }

    override suspend fun getGameDataById(
        id: Int
    ): CustomResultModelDomain<GameModelDomain, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext NbaApiNetworkRepositoryFunctions.getElementFromApi(
                apiCall = { apiService.getDataForGameById(gameId = id) },
                dispatcher = dispatchers.IO,
                transform = { responseList ->
                    responseList?.firstNotNullOfOrNull { game ->
                        game?.toModelDomain()
                    }
                },
                errorsParser = { json ->
                    json?.fromJson<GamesResponseErrorsModelData>()
                }
            )
        }

}