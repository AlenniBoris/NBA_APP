package com.alenniboris.nba_app.presentation.screens.details.game

sealed interface IGameDetailsScreenUpdateIntent {

    data object ProceedIsFollowedAction : IGameDetailsScreenUpdateIntent

    data class ProceedChangeViewedTeamAction(val newTeam: GameTeamType) :
        IGameDetailsScreenUpdateIntent

    data object NavigateToPreviousScreen : IGameDetailsScreenUpdateIntent

}