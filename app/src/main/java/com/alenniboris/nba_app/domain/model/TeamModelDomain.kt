package com.alenniboris.nba_app.domain.model

import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain

data class TeamModelDomain(
    override val id: Int,
    override val isFollowed: Boolean,
    val name: String,
    val isNational: Boolean?,
    val logo: String?,
    val country: CountryModelDomain?
) : IStateModel