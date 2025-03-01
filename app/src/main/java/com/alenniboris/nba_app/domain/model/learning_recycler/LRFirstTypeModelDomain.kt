package com.alenniboris.nba_app.domain.model.learning_recycler

data class LRFirstTypeModelDomain(
    override val id: Int,
    val name: String,
    val text: String
): LRModelDomain
