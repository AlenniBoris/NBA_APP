package com.alenniboris.nba_app.presentation.screens.details.team

import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain

sealed interface ITeamDetailsScreenEvent {

    data class ShowToastMessage(val message: Int) : ITeamDetailsScreenEvent

    data object NavigateToPreviousPage : ITeamDetailsScreenEvent

    data class NavigateToPlayerDetailsScreen(val player: PlayerModelDomain) :
        ITeamDetailsScreenEvent

}