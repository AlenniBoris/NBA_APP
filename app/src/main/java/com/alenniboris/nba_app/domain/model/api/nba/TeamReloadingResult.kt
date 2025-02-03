package com.alenniboris.nba_app.domain.model.api.nba

import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain

data class TeamReloadingResult(
    val teamData: TeamModelDomain,
    val leaguesData: List<LeagueModelDomain>
)
