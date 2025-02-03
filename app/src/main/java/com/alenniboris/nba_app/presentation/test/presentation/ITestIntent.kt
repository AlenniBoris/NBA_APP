package com.alenniboris.nba_app.presentation.test.presentation

sealed interface ITestIntent {
    data object LoadMoreData: ITestIntent
}