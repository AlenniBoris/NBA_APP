package com.alenniboris.nba_app.domain.model.filters

data class SeasonModelDomain(
    override val id: Int,
    override val name: String
) : IShowingFilterModel
