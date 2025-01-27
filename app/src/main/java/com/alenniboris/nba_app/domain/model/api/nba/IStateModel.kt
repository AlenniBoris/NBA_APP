package com.alenniboris.nba_app.domain.model.api.nba

sealed interface IStateModel {
    val id: Int
    val isFollowed: Boolean
}