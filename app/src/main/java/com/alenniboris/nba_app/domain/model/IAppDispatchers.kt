package com.alenniboris.nba_app.domain.model

import kotlinx.coroutines.CoroutineDispatcher

interface IAppDispatchers {
    val Main: CoroutineDispatcher
    val IO: CoroutineDispatcher
    val Default: CoroutineDispatcher
}