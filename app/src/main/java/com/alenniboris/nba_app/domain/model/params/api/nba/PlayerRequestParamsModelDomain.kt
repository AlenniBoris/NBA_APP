package com.alenniboris.nba_app.domain.model.params.api.nba

import com.alenniboris.nba_app.domain.model.TeamModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain

data class PlayerRequestParamsModelDomain(
    override val requestType: INbaApiRequestType = NbaApiPlayerRequestType.PLAYER_SEASON_TEAM,
    override val possibleRequestTypes: List<INbaApiRequestType> = NbaApiPlayerRequestType.entries,
    val requestedQuery: String = "",
    val requestedSeason: SeasonModelDomain? = null,
    val requestedLeague: LeagueModelDomain? = null,
    val requestedTeam: TeamModelDomain? = null
) : INbaApiRequestParams
