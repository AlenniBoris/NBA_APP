package com.alenniboris.nba_app.domain.model.filters

data class LeagueModelDomain(
    override val id: Int = 0,
    override val name: String = "",
    val type: String? = null,
    val logo: String? = null,
    val country: CountryModelDomain? = null,
) : IShowingFilterModel
