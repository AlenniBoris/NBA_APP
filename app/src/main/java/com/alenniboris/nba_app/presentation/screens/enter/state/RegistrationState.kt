package com.alenniboris.nba_app.presentation.screens.enter.state


data class RegistrationState(
    override val enteredEmail: String = "",
    override val enteredPassword: String = "",
    val enteredPasswordCheck: String = "",
) : IEnterState