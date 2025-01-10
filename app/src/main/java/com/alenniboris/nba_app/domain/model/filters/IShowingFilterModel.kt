package com.alenniboris.nba_app.domain.model.filters

sealed interface IShowingFilterModel {
    val id: Int
    val name: String
}