package com.alenniboris.nba_app.domain.model.entity.api.nba

import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain

data class TeamEntityModelDomain(
    val teamId: Int,
    val userId: String,
    val teamName: String,
    val isNational: Boolean?,
    val countryName: String,
    val id: String,
    val countryId: Int
) : IEntityModelDomain

fun TeamEntityModelDomain.toTeamModelDomain(): TeamModelDomain =
    TeamModelDomain(
        id = this.teamId,
        isFollowed = true,
        name = this.teamName,
        isNational = this.isNational,
        country =
        CountryModelDomain(
            id = this.countryId,
            name = this.countryName
        ),
        logo = null
    )
