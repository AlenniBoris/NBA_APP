package com.alenniboris.nba_app.presentation.screens.followed


import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.IStateModel
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain


interface IFollowedScreenUpdateIntent {

    data class ProceedRemovingFromFollowedAction(val element: IStateModel) :
        IFollowedScreenUpdateIntent

    data class ProceedNavigationToGameDetailsScreen(val game: GameModelDomain) :
        IFollowedScreenUpdateIntent

    data class ProceedNavigationToTeamDetailsScreen(val team: TeamModelDomain) :
        IFollowedScreenUpdateIntent

    data class ProceedNavigationToPlayerDetailsScreen(val player: PlayerModelDomain) :
        IFollowedScreenUpdateIntent

    data object NavigateToPreviousScreen : IFollowedScreenUpdateIntent
}