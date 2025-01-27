package com.alenniboris.nba_app.data.model.api.nba.team

import android.util.Log
import com.alenniboris.nba_app.domain.model.statistics.api.nba.extra.TeamPlayedGamesModelDomain

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
    Log.e("MappingError", "TeamPlayedGamesModelData")
    null
}
