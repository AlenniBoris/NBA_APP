package com.alenniboris.nba_app.data.model.api.nba.team


import com.alenniboris.nba_app.data.model.api.nba.country.CountryModelData
import com.alenniboris.nba_app.data.model.api.nba.country.toModelDomain
import com.alenniboris.nba_app.data.model.api.nba.league.LeagueModelData
import com.alenniboris.nba_app.data.model.api.nba.league.toModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.TeamStatisticsModelDomain
import com.alenniboris.nba_app.domain.utils.LogPrinter

data class StatisticsForTeamModelData(
    val league: LeagueModelData?,
    val country: CountryModelData?,
    val team: TeamModelData?,
    val games: TeamPlayedGamesModelData?,
    val points: TeamEarnedPointsModelData?
)

fun StatisticsForTeamModelData.toModelDomain(): TeamStatisticsModelDomain? = runCatching {
    TeamStatisticsModelDomain(
        league = this.league?.toModelDomain(),
        country = this.country?.toModelDomain(),
        team = this.team?.toModelDomain(),
        games = this.games?.toModelDomain(),
        points = this.points?.toModelDomain()
    )
}.getOrElse {
    LogPrinter.printLog("MappingError", "StatisticsForTeamModelData")
    null
}