package com.alenniboris.nba_app.presentation.test_pagination.presentation

sealed interface ITestPaginationIntent {
    data object LoadMoreData: ITestPaginationIntent
}