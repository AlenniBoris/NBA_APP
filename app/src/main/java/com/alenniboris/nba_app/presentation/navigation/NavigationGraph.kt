package com.alenniboris.nba_app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alenniboris.nba_app.presentation.screens.enter.views.EnterScreen
import com.alenniboris.nba_app.presentation.screens.games.GamesScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    isUserAuthenticated: Boolean
) {

    NavHost(
        navController = navController,
        startDestination =
        if (!isUserAuthenticated) Route.EnterScreenRoute.route
        else Route.GamesScreenRoute.route,
    ) {

        composable(Route.EnterScreenRoute.route) {
            EnterScreen()
        }

        composable(Route.GamesScreenRoute.route) {
            GamesScreen()
        }

    }

}