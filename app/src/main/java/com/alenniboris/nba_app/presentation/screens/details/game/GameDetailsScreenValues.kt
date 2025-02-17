package com.alenniboris.nba_app.presentation.screens.details.game

import com.alenniboris.nba_app.R

enum class GameTeamType(val stringRes: Int) {
    Home(R.string.home_team_text),
    Visitors(R.string.visitors_team_text)
}

enum class GameStatisticsType(val stringRes: Int) {
    Players(R.string.players_category),
    Game(R.string.games_category)
}