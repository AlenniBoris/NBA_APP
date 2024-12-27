package com.alenniboris.nba_app.presentation.model.filter

data class CountryFilterUiModel(
    override val id: Int = 0,
    override val name: String? = "",
    val code: String? = "",
    val flag: String? = ""
) : IStateFilterUiModel
