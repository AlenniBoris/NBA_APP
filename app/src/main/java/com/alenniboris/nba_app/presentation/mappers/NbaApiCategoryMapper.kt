package com.alenniboris.nba_app.presentation.mappers

import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.domain.utils.NbaApiCategory

fun NbaApiCategory.toStringMessage(): Int =
    when(this){
        NbaApiCategory.Games -> R.string.games_category
        NbaApiCategory.Teams -> R.string.teams_category
        NbaApiCategory.Players -> R.string.players_category
    }