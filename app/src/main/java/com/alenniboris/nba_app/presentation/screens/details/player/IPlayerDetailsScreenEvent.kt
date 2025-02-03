package com.alenniboris.nba_app.presentation.screens.details.player

import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain

sealed interface IPlayerDetailsScreenEvent {

    data object NavigateToPreviousPage : IPlayerDetailsScreenEvent

    data class NavigateToGameDetailsScreen(val game: GameModelDomain) : IPlayerDetailsScreenEvent

    data class ShowToastMessage(val message: Int) : IPlayerDetailsScreenEvent

}