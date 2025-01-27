package com.alenniboris.nba_app.presentation.screens.details.game

sealed interface IGameDetailsScreenEvent {

    data object NavigateToPreviousPage : IGameDetailsScreenEvent

    data class ShowToastMessage(val message: Int) : IGameDetailsScreenEvent

}