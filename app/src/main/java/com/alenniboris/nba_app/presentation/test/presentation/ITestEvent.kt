package com.alenniboris.nba_app.presentation.test.presentation

sealed interface ITestEvent {
    data class ShowMessage(val message: String) : ITestEvent
}