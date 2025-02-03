package com.alenniboris.nba_app.presentation.navigation

sealed class Route(
    val route: String,
    val baseRoute: String = ""
) {

    data object EnterScreenRoute : Route(NavigationValues.EnterScreenRoute)

    data object ShowingScreenRoute : Route(NavigationValues.ShowingScreenRoute)

    data object FollowedScreenRoute : Route(NavigationValues.FollowedScreenRoute)

    data object GameDetailsScreenRoute : Route(
        route = NavigationValues.GameDetailsScreenFullRoute,
        baseRoute = NavigationValues.GameDetailsScreenBaseRoute
    )

    data object TeamDetailsScreenRoute : Route(
        route = NavigationValues.TeamDetailsScreenFullRoute,
        baseRoute = NavigationValues.TeamDetailsScreenBaseRoute
    )

    data object PlayerDetailsScreenRoute : Route(
        route = NavigationValues.PlayerDetailsScreenFullRoute,
        baseRoute = NavigationValues.PlayerDetailsScreenBaseRoute
    )

}