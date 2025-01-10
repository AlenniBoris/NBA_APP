package com.alenniboris.nba_app.domain.model

data class PlayerModelDomain(
    override val id: Int,
    override val isFollowed: Boolean,
    val name: String,
    val number: String?,
    val country: String?,
    val position: String?,
    val age: Int?
) : IStateModel