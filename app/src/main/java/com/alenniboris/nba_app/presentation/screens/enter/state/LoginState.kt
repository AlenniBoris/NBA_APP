package com.alenniboris.nba_app.presentation.screens.enter.state

data class LoginState(
    override val enteredEmail: String = "",
    override val enteredPassword: String = "",
    override val errorText: String = "",
    override val someErrorHappened: Boolean = false,
    val isSuccessfullyEntered: Boolean = false
): IEnterState