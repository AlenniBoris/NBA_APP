package com.alenniboris.nba_app.presentation.screens.enter.state

data class LoginState(
    override val enteredEmail: String = "",
    override val enteredPassword: String = "",
) : IEnterState