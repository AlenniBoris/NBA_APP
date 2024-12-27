package com.alenniboris.nba_app.presentation.model

sealed interface IStateUiModel {
    val id: Int
    val isFollowed: Boolean
}