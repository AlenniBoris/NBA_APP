package com.alenniboris.nba_app.presentation.screens.details.game

import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain

sealed interface IGameDetailsScreenEvent {

    data object NavigateToPreviousPage : IGameDetailsScreenEvent

    data class ShowToastMessage(val message: Int) : IGameDetailsScreenEvent

    data class NavigateToPlayerDetailsScreen(val player: PlayerModelDomain) :
        IGameDetailsScreenEvent

    data class NavigateToTeamDetailsScreen(val team: TeamModelDomain) :
        IGameDetailsScreenEvent

}