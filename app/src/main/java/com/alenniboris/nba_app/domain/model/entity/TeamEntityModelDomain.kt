package com.alenniboris.nba_app.domain.model.entity

data class TeamEntityModelDomain(
    val teamId: Int,
    val userId: String,
    val teamName: String,
    val isNational: Boolean?,
    val countryName: String?,
    val logo: String?,
    val id: String
) : IEntityModelDomain
