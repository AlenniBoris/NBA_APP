package com.alenniboris.nba_app.domain.model.api.nba

import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain

data class TeamModelDomain(
    override val id: Int = 0,
    override val isFollowed: Boolean = false,
    val name: String = "",
    val isNational: Boolean? = null,
    val logo: String? = null,
    val country: CountryModelDomain = CountryModelDomain()
) : IStateModel