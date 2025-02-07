package com.alenniboris.nba_app.presentation.screens.details.game

import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.GameStatisticsModelDomain

data class GameDetailsScreenState(
    val game: GameModelDomain,
    val gameStatistics: GameStatisticsModelDomain = GameStatisticsModelDomain(),
    val isReloadedWithError: Boolean = false,
    val isLoading: Boolean = false,
    val currentlyViewedTeam: GameTeamType = GameTeamType.Home,
)
