package com.alenniboris.nba_app.presentation.screens.enter

sealed class AuthIntent {

    data object SwitchBetweenLoginAndRegister : AuthIntent()

    data class UpdateEnteredEmail(val value: String) : AuthIntent()

    data class UpdateEnteredPassword(val value: String) : AuthIntent()

    data class UpdateEnteredPasswordCheck(val value: String) : AuthIntent()

    data object ButtonClickProceed: AuthIntent()

}