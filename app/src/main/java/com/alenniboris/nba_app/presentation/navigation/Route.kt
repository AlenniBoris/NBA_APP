package com.alenniboris.nba_app.presentation.navigation

sealed class Route(
    val route: String,
    val baseRoute: String = ""
) {

    data object EnterScreenRoute : Route("enter_screen")

    data object ShowingScreenRoute : Route("showing_screen")

    data object FollowedScreenRoute : Route("followed_screen")

    data object GameDetailsScreenRoute : Route(
        route = "game_details_screen/{game}&{isReloadingNeeded}",
        baseRoute = "game_details_screen/"
    )

    data object TeamDetailsScreenRoute : Route(
        route = "team_details_screen/{team}",
        baseRoute = "team_details_screen/"
    )

    data object PlayerDetailsScreenRoute : Route(
        route = "player_details_screen/{player}",
        baseRoute = "player_details_screen/"
    )

}