package com.alenniboris.nba_app.domain.model.statistics.api.nba.main

import com.alenniboris.nba_app.domain.model.statistics.api.nba.extra.GoalsStatisticsModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.extra.ReboundsModelDomain

data class TeamInGameStatisticsModelDomain(
    val teamId: Int = 0,
    val fieldGoals: GoalsStatisticsModelDomain? = null,
    val threePointGoals: GoalsStatisticsModelDomain? = null,
    val freeThrowsGoals: GoalsStatisticsModelDomain? = null,
    val rebounds: ReboundsModelDomain? = null,
    val assists: Int? = null,
    val steals: Int? = null,
    val blocks: Int? = null,
    val turnovers: Int? = null,
    val personalFouls: Int? = null
) : IStatisticsModel {
    val isEmpty: Boolean
        get() = this == TeamInGameStatisticsModelDomain()
}
