package com.alenniboris.nba_app.domain.model.params.api.nba

import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import java.util.Calendar
import java.util.Date

data class GameRequestParamsModelDomain(
    override val elementsRequestType: INbaApiElementsRequestType = NbaApiGameTypeElementsRequest.GAMES_DATE,
    override val possibleElementsRequestTypes: List<INbaApiElementsRequestType> = NbaApiGameTypeElementsRequest.entries,
    val requestedGame: GameModelDomain? = null,
    val requestedSeason: SeasonModelDomain? = null,
    val requestedLeague: LeagueModelDomain? = null,
    val requestedDate: Date = Calendar.getInstance().time
) : INbaApiElementsRequestParams
