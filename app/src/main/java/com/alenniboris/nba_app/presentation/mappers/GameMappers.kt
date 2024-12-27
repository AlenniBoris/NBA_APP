package com.alenniboris.nba_app.presentation.mappers

import androidx.compose.ui.graphics.Color
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.domain.utils.EnumValues
import com.alenniboris.nba_app.presentation.uikit.theme.BadStatusColor
import com.alenniboris.nba_app.presentation.uikit.theme.GoodStatusColor
import com.alenniboris.nba_app.presentation.uikit.theme.NeutralStatusColor


fun EnumValues.GameStatus.toStringMessageForStatus(): Int = when (this) {
    EnumValues.GameStatus.In_Play -> R.string.status_in_play
    EnumValues.GameStatus.Not_Started -> R.string.status_not_started
    EnumValues.GameStatus.Finished -> R.string.status_finished
    EnumValues.GameStatus.Postponed -> R.string.status_postponed
    EnumValues.GameStatus.Cancelled -> R.string.status_cancelled
    EnumValues.GameStatus.Suspended -> R.string.status_suspended
    EnumValues.GameStatus.Awarded -> R.string.status_awarded
    EnumValues.GameStatus.Abandoned -> R.string.status_abandoned
}

fun EnumValues.GameStatus.toColorValue(): Color = when (this) {
    EnumValues.GameStatus.In_Play -> GoodStatusColor
    EnumValues.GameStatus.Not_Started -> NeutralStatusColor
    EnumValues.GameStatus.Finished -> BadStatusColor
    EnumValues.GameStatus.Postponed -> NeutralStatusColor
    EnumValues.GameStatus.Cancelled -> BadStatusColor
    EnumValues.GameStatus.Suspended -> NeutralStatusColor
    EnumValues.GameStatus.Awarded -> BadStatusColor
    EnumValues.GameStatus.Abandoned -> BadStatusColor
}