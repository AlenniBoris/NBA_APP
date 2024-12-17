package com.alenniboris.nba_app.presentation.navigation

sealed class Route(
    val route: String
) {

    data object EnterScreenRoute : Route("enter_screen")

    data object GamesScreenRoute : Route("games_screen")

}