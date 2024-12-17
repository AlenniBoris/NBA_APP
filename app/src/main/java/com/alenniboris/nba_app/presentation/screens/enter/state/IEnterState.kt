package com.alenniboris.nba_app.presentation.screens.enter.state

sealed interface IEnterState {
    val enteredEmail: String
    val enteredPassword: String
    val someErrorHappened: Boolean
    val errorText: String
}