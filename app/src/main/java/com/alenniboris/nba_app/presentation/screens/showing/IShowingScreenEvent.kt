package com.alenniboris.nba_app.presentation.screens.showing

interface IShowingScreenEvent {

    data class ShowToastMessage(val message: Int) : IShowingScreenEvent

    data object NavigateToUserDetailsScreen : IShowingScreenEvent

}