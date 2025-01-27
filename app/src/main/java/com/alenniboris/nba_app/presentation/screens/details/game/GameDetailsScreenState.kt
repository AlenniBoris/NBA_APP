package com.alenniboris.nba_app.presentation.screens.details.game

import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.GameStatisticsModelDomain

data class GameDetailsScreenState(
    val game: GameModelDomain,
    val gameStatistics: GameStatisticsModelDomain = GameStatisticsModelDomain(),
    val isGameDataReloading: Boolean = false,
    val isGameStatisticsLoading: Boolean = false,
    val isDetailsVisible: Boolean = false,
    val currentlyViewedTeam: GameTeamType = GameTeamType.Home,
)
