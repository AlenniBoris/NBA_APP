package com.alenniboris.nba_app.domain.model

sealed interface IStateModel {
    val id: Int
    val isFollowed: Boolean
}