package com.alenniboris.nba_app.presentation.learning_recycler.main

import com.alenniboris.nba_app.domain.model.learning_recycler.LearningRecyclerDataModelDomain

data class LRMainScreenState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val data: List<LearningRecyclerDataModelDomain> = emptyList()
)
