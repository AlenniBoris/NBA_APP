package com.alenniboris.nba_app.presentation.screens.details.player

import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.PlayerStatisticsModelDomain

data class PlayerDetailsScreenState(
    val player: PlayerModelDomain,
    val isPlayerDataLoading: Boolean = false,
    val playerStatistics: List<PlayerStatisticsModelDomain> = emptyList(),
    val isPlayerStatisticsLoading: Boolean = false,
    val listOfSeasons: List<SeasonModelDomain> = emptyList(),
    val selectedSeason: SeasonModelDomain? = null,
    val isSeasonsLoading: Boolean = false,
)
