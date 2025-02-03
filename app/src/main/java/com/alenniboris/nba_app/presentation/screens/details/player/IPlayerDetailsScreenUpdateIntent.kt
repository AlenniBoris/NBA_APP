package com.alenniboris.nba_app.presentation.screens.details.player

import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain

sealed interface IPlayerDetailsScreenUpdateIntent {

    data object ProceedIsFollowedAction : IPlayerDetailsScreenUpdateIntent

    data object NavigateToPreviousScreen : IPlayerDetailsScreenUpdateIntent

    data class NavigateToGameDetailsScreen(val gameId: Int) :
        IPlayerDetailsScreenUpdateIntent

    data class UpdateSelectedSeason(val newSeason: SeasonModelDomain) :
        IPlayerDetailsScreenUpdateIntent

}