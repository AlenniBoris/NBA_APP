package com.alenniboris.nba_app.presentation.screens.showing

import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain

interface IShowingScreenEvent {

    data class ShowToastMessage(val message: Int) : IShowingScreenEvent

    data class NavigateToGameDetailsPage(val game: GameModelDomain) : IShowingScreenEvent

    data class NavigateToTeamDetailsPage(val team: TeamModelDomain) : IShowingScreenEvent

    data class NavigateToPlayerDetailsPage(val player: PlayerModelDomain) : IShowingScreenEvent

    data object NavigateToUserDetailsScreen : IShowingScreenEvent

    data object NavigateToSettingsPage : IShowingScreenEvent

}