package com.alenniboris.nba_app.presentation.model

data class PlayerUiModel(
    override val id: Int = 0,
    override val isFollowed: Boolean = false,
    val name: String = "",
    val number: String = "",
    val country: String = "",
    val position: String = "",
    val age: Int = 0
) : IStateUiModel