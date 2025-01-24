package com.alenniboris.nba_app.domain.model

data class PlayerModelDomain(
    override val id: Int = 0,
    override val isFollowed: Boolean = false,
    val name: String = "",
    val number: String? = null,
    val country: String? = null,
    val position: String? = null,
    val age: Int? = null
) : IStateModel