package com.alenniboris.nba_app.presentation.learning_recycler.main

import com.alenniboris.nba_app.presentation.learning_recycler.model.LRModelUi

data class LRMainScreenState(
    val isLoading: Boolean = false,
    val data: List<LRModelUi> = emptyList()
)
