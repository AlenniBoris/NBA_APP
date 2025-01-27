package com.alenniboris.nba_app.domain.model.statistics.api.nba.main

import com.alenniboris.nba_app.domain.model.statistics.api.nba.extra.GoalsStatisticsModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.extra.ReboundsModelDomain

data class PlayerStatisticsModelDomain(
    val playerId: Int,
    val playerName: String,
    val teamId: Int,
    val playedMinutes: String?,
    val fieldGoals: GoalsStatisticsModelDomain?,
    val threePointGoals: GoalsStatisticsModelDomain?,
    val freeThrowsGoals: GoalsStatisticsModelDomain?,
    val rebounds: ReboundsModelDomain?,
    val assists: Int?,
    val earnedPoints: Int?
): IStatisticsModel