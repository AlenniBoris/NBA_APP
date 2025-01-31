package com.alenniboris.nba_app.presentation.screens.details.team

sealed interface ITeamDetailsScreenEvent {

    data class ShowToastMessage(val message: Int): ITeamDetailsScreenEvent

    data object NavigateToPreviousPage: ITeamDetailsScreenEvent

}