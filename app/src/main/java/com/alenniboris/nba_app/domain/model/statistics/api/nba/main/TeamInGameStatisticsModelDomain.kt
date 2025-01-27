package com.alenniboris.nba_app.domain.model.statistics.api.nba.main

import com.alenniboris.nba_app.domain.model.statistics.api.nba.extra.GoalsStatisticsModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.extra.ReboundsModelDomain

data class TeamInGameStatisticsModelDomain(
    val teamId: Int,
    val fieldGoals: GoalsStatisticsModelDomain?,
    val threePointGoals: GoalsStatisticsModelDomain?,
    val freeThrowsGoals: GoalsStatisticsModelDomain?,
    val rebounds: ReboundsModelDomain?,
    val assists: Int?,
    val steals: Int?,
    val blocks: Int?,
    val turnovers: Int?,
    val personalFouls: Int?
): IStatisticsModel