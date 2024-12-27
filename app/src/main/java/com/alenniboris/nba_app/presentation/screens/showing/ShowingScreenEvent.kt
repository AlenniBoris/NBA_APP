package com.alenniboris.nba_app.presentation.screens.showing

interface ShowingScreenEvent {

    data class ShowToastMessage(val message: String) : ShowingScreenEvent

    data object NavigateToUserDetailsScreen : ShowingScreenEvent

}