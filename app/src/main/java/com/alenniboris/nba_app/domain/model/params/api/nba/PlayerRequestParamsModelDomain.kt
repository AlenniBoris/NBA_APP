package com.alenniboris.nba_app.domain.model.params.api.nba

import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain

data class PlayerRequestParamsModelDomain(
    override val elementsRequestType: INbaApiElementsRequestType = NbaApiPlayerTypeElementsRequest.PLAYER_SEASON_TEAM,
    override val possibleElementsRequestTypes: List<INbaApiElementsRequestType> = NbaApiPlayerTypeElementsRequest.entries,
    val requestedQuery: String = "",
    val requestedSeason: SeasonModelDomain? = null,
    val requestedLeague: LeagueModelDomain? = null,
    val requestedTeam: TeamModelDomain? = null
) : INbaApiElementsRequestParams
