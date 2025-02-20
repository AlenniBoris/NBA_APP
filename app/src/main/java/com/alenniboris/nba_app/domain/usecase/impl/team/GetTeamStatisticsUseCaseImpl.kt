package com.alenniboris.nba_app.domain.usecase.impl.team

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.TeamStatisticsModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiTeamsNetworkRepository
import com.alenniboris.nba_app.domain.usecase.team.IGetTeamStatisticsUseCase
import kotlinx.coroutines.withContext

class GetTeamStatisticsUseCaseImpl(
    private val nbaApiTeamsNetworkRepository: INbaApiTeamsNetworkRepository,
    private val dispatchers: IAppDispatchers
) : IGetTeamStatisticsUseCase {

    override suspend fun invoke(
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

}