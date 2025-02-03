package com.alenniboris.nba_app.domain.model.filters

data class CountryModelDomain(
    override val id: Int = -1,
    override val name: String? = "",
    val code: String? = null,
    val flag: String? = null
) : IShowingFilterModel