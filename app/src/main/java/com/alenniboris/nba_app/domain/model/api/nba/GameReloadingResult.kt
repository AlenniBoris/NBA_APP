package com.alenniboris.nba_app.domain.model.api.nba

import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.GameStatisticsModelDomain

data class GameReloadingResult(
    val game: GameModelDomain,
    val statistics: GameStatisticsModelDomain
)
