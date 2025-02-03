package com.alenniboris.nba_app.domain.model.statistics.api.nba.main

import com.alenniboris.nba_app.domain.model.statistics.api.nba.extra.GoalsStatisticsModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.extra.ReboundsModelDomain

data class PlayerStatisticsModelDomain(
    val gameId: Int = 0,
    val playerId: Int = 0,
    val playerName: String = "",
    val teamId: Int = 0,
    val playedMinutes: String? = null,
    val fieldGoals: GoalsStatisticsModelDomain? = null,
    val threePointGoals: GoalsStatisticsModelDomain? = null,
    val freeThrowsGoals: GoalsStatisticsModelDomain? = null,
    val rebounds: ReboundsModelDomain? = null,
    val assists: Int? = null,
    val earnedPoints: Int? = null
) : IStatisticsModel
