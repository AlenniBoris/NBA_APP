package com.alenniboris.nba_app.domain.manager

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.IStateModel
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamReloadingResult
import com.alenniboris.nba_app.domain.model.entity.api.nba.GameEntityModelDomain
import com.alenniboris.nba_app.domain.model.entity.api.nba.PlayerEntityModelDomain
import com.alenniboris.nba_app.domain.model.entity.api.nba.TeamEntityModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.model.params.api.nba.INbaApiElementsRequestParams
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.GameStatisticsModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.PlayerStatisticsModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.TeamStatisticsModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.TeamsInGameStatisticsModelDomain
import kotlinx.coroutines.flow.SharedFlow

interface INbaApiManager {

    val followedGames: SharedFlow<List<GameEntityModelDomain>>
    val followedTeams: SharedFlow<List<TeamEntityModelDomain>>
    val followedPlayers: SharedFlow<List<PlayerEntityModelDomain>>

    suspend fun makeRequestForListOfElements(
        elementsRequestParameters: INbaApiElementsRequestParams
    ): CustomResultModelDomain<List<IStateModel>, NbaApiExceptionModelDomain>?

    suspend fun getAllSeasons(): CustomResultModelDomain<List<SeasonModelDomain>, NbaApiExceptionModelDomain>

    suspend fun getAllCountries(): CustomResultModelDomain<List<CountryModelDomain>, NbaApiExceptionModelDomain>

    suspend fun getGameDataById(
        id: Int
    ): CustomResultModelDomain<GameModelDomain, NbaApiExceptionModelDomain>

    suspend fun reloadDataForTeamAndLoadLeagues(
        team: TeamModelDomain
    ): CustomResultModelDomain<TeamReloadingResult, NbaApiExceptionModelDomain>

    suspend fun getLeaguesByCountry(
        country: CountryModelDomain
    ): CustomResultModelDomain<List<LeagueModelDomain>, NbaApiExceptionModelDomain>

    suspend fun proceedElementIsFollowingUpdate(
        element: IStateModel
    ): CustomResultModelDomain<Unit, NbaApiExceptionModelDomain>

    suspend fun requestForTeamsStatisticsInGame(
        game: GameModelDomain
    ): CustomResultModelDomain<TeamsInGameStatisticsModelDomain, NbaApiExceptionModelDomain>

    suspend fun requestForGameStatistics(
        game: GameModelDomain
    ): CustomResultModelDomain<GameStatisticsModelDomain, NbaApiExceptionModelDomain>

    suspend fun requestForTeamStatistics(
        team: TeamModelDomain,
        season: SeasonModelDomain?,
        league: LeagueModelDomain?
    ): CustomResultModelDomain<TeamStatisticsModelDomain, NbaApiExceptionModelDomain>

    suspend fun requestForPlayersStatisticsInSeason(
        season: SeasonModelDomain,
        player: PlayerModelDomain
    ): CustomResultModelDomain<List<PlayerStatisticsModelDomain>, NbaApiExceptionModelDomain>

    suspend fun getPlayerDataById(
        id: Int
    ): CustomResultModelDomain<PlayerModelDomain, NbaApiExceptionModelDomain>

}