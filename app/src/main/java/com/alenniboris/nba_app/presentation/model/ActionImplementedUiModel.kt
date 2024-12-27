package com.alenniboris.nba_app.presentation.model

data class ActionImplementedUiModel(
    val name: String = "",
    val onClick: () -> Unit = {}
)