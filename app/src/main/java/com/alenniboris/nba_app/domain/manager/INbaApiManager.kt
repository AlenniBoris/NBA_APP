package com.alenniboris.nba_app.domain.manager

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IStateModel
import com.alenniboris.nba_app.domain.model.entity.GameEntityModelDomain
import com.alenniboris.nba_app.domain.model.entity.PlayerEntityModelDomain
import com.alenniboris.nba_app.domain.model.entity.TeamEntityModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.model.params.api.nba.INbaApiRequestParams
import kotlinx.coroutines.flow.SharedFlow

interface INbaApiManager {

    val followedGames: SharedFlow<List<GameEntityModelDomain>>
    val followedTeams: SharedFlow<List<TeamEntityModelDomain>>
    val followedPlayers: SharedFlow<List<PlayerEntityModelDomain>>

    suspend fun makeRequestForListOfElements(
        requestParameters: INbaApiRequestParams
    ): CustomResultModelDomain<List<IStateModel>, NbaApiExceptionModelDomain>?

    suspend fun getAllSeasons(): CustomResultModelDomain<List<SeasonModelDomain>, NbaApiExceptionModelDomain>

    suspend fun getAllCountries(): CustomResultModelDomain<List<CountryModelDomain>, NbaApiExceptionModelDomain>

    suspend fun getLeaguesByCountry(
        country: CountryModelDomain
    ): CustomResultModelDomain<List<LeagueModelDomain>, NbaApiExceptionModelDomain>

    suspend fun proceedElementIsFollowingUpdate(
        element: IStateModel
    ): CustomResultModelDomain<Unit, NbaApiExceptionModelDomain>

}