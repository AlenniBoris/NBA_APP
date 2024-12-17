package com.alenniboris.nba_app.presentation.screens.enter.state


data class RegistrationState(
    override val enteredEmail: String = "",
    override val enteredPassword: String = "",
    override val errorText: String = "",
    override val someErrorHappened: Boolean = false,
    val enteredPasswordCheck: String = "",
    val isSuccessfullyRegistered: Boolean = false
): IEnterState