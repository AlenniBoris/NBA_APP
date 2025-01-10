package com.alenniboris.nba_app.domain.model.params.api.nba

import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain

data class TeamRequestParamsModelDomain(
    override val requestType: INbaApiRequestType = NbaApiTeamRequestType.TEAMS_COUNTRY,
    override val possibleRequestTypes: List<INbaApiRequestType> = NbaApiTeamRequestType.entries,
    val requestedQuery: String = "",
    val requestedSeason: SeasonModelDomain? = null,
    val requestedCountry: CountryModelDomain? = null,
    val requestedLeague: LeagueModelDomain? = null
) : INbaApiRequestParams
