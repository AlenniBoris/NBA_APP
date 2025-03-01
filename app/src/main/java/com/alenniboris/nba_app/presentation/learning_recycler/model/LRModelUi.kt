package com.alenniboris.nba_app.presentation.learning_recycler.model

import com.alenniboris.nba_app.domain.model.learning_recycler.LRModelDomain

interface LRModelUi {
    fun getModel(): LRModelDomain
}