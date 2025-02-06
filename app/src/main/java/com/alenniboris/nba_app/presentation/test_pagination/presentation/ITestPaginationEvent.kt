package com.alenniboris.nba_app.presentation.test_pagination.presentation

sealed interface ITestPaginationEvent {
    data class ShowMessage(val message: String) : ITestPaginationEvent
}