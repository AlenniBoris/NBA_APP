package com.alenniboris.nba_app.presentation.screens.followed
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain

interface IFollowedScreenEvent {

    data object NavigateToPreviousPage : IFollowedScreenEvent

    data class NavigateToGameDetailsPage(val game: GameModelDomain) : IFollowedScreenEvent

    data class NavigateToTeamDetailsPage(val team: TeamModelDomain) : IFollowedScreenEvent

    data class NavigateToPlayerDetailsPage(val player: PlayerModelDomain) : IFollowedScreenEvent

}