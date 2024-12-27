package com.alenniboris.nba_app.presentation.model.filter

sealed interface IStateFilterUiModel {
    val id: Int?
    val name: String?
}