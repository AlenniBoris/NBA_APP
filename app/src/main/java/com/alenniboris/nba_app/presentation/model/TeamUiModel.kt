package com.alenniboris.nba_app.presentation.model

import com.alenniboris.nba_app.presentation.model.filter.CountryFilterUiModel

data class TeamUiModel(
    override val id: Int = 0,
    override val isFollowed: Boolean = false,
    val name: String? = "",
    val logoUrl: String? = "",
    val national: Boolean = false,
    val country: CountryFilterUiModel = CountryFilterUiModel()
) : IStateUiModel
