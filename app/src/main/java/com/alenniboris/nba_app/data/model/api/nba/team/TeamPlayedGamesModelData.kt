package com.alenniboris.nba_app.data.model.api.nba.team

import com.alenniboris.nba_app.domain.model.statistics.api.nba.extra.TeamPlayedGamesModelDomain
import com.alenniboris.nba_app.domain.utils.LogPrinter

data class TeamPlayedGamesModelData(
    val draws: GamesDetailedStatsModelData?,
    val loses: GamesDetailedStatsModelData?,
    val played: GamesSimpleStatsModelData?,
    val wins: GamesDetailedStatsModelData?
)


fun TeamPlayedGamesModelData.toModelDomain(): TeamPlayedGamesModelDomain? = runCatching {
    TeamPlayedGamesModelDomain(
        draws = this.draws?.toModelDomain(),
        loses = this.loses?.toModelDomain(),
        played = this.played?.toModelDomain(),
        wins = this.wins?.toModelDomain()
    )
}.getOrElse {
    LogPrinter.printLog("MappingError", "TeamPlayedGamesModelData")
    null
}
