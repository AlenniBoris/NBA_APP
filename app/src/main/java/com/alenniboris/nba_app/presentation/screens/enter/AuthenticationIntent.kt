package com.alenniboris.nba_app.presentation.screens.enter

sealed interface AuthenticationIntent {

    data object SwitchBetweenLoginAndRegister : AuthenticationIntent

    data class UpdateEnteredEmail(val value: String) : AuthenticationIntent

    data class UpdateEnteredPassword(val value: String) : AuthenticationIntent

    data class UpdateEnteredPasswordCheck(val value: String) : AuthenticationIntent

    data object ButtonClickProceed : AuthenticationIntent

}