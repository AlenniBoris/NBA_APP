package com.alenniboris.nba_app.presentation.mappers

import androidx.compose.ui.graphics.Color
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.domain.utils.GameStatus
import com.alenniboris.nba_app.presentation.uikit.theme.BadStatusColor
import com.alenniboris.nba_app.presentation.uikit.theme.GoodStatusColor
import com.alenniboris.nba_app.presentation.uikit.theme.NeutralStatusColor


fun GameStatus.toStringMessageForStatus(): Int = when (this) {
    GameStatus.In_Play -> R.string.status_in_play
    GameStatus.Not_Started -> R.string.status_not_started
    GameStatus.Game_Finished -> R.string.status_finished
    GameStatus.Postponed -> R.string.status_postponed
    GameStatus.Cancelled -> R.string.status_cancelled
    GameStatus.Suspended -> R.string.status_suspended
    GameStatus.Awarded -> R.string.status_awarded
    GameStatus.Abandoned -> R.string.status_abandoned
    GameStatus.NotDefined -> R.string.nan_text
}

fun GameStatus.toColorValue(): Color = when (this) {
    GameStatus.In_Play -> GoodStatusColor
    GameStatus.Not_Started -> NeutralStatusColor
    GameStatus.Game_Finished -> BadStatusColor
    GameStatus.Postponed -> NeutralStatusColor
    GameStatus.Cancelled -> BadStatusColor
    GameStatus.Suspended -> NeutralStatusColor
    GameStatus.Awarded -> BadStatusColor
    GameStatus.Abandoned -> BadStatusColor
    GameStatus.NotDefined -> BadStatusColor
}