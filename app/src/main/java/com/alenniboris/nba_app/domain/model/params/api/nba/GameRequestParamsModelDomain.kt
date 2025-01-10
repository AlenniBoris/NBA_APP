package com.alenniboris.nba_app.domain.model.params.api.nba

import com.alenniboris.nba_app.domain.model.GameModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import java.util.Calendar
import java.util.Date

data class GameRequestParamsModelDomain(
    override val requestType: INbaApiRequestType = NbaApiGameRequestType.GAMES_DATE,
    override val possibleRequestTypes: List<INbaApiRequestType> = NbaApiGameRequestType.entries,
    val requestedGame: GameModelDomain? = null,
    val requestedSeason: SeasonModelDomain? = null,
    val requestedLeague: LeagueModelDomain? = null,
    val requestedDate: Date = Calendar.getInstance().time
) : INbaApiRequestParams
