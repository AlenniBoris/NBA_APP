package com.alenniboris.nba_app.domain.model.filters

data class LeagueModelDomain(
    override val id: Int,
    override val name: String,
    val type: String?,
    val logo: String?,
    val country: CountryModelDomain?,
): IShowingFilterModel
