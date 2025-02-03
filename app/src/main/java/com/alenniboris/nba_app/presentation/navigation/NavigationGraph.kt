package com.alenniboris.nba_app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alenniboris.nba_app.presentation.screens.details.game.views.GameDetailsScreen
import com.alenniboris.nba_app.presentation.screens.details.player.views.PlayerDetailsScreen
import com.alenniboris.nba_app.presentation.screens.details.team.views.TeamDetailsScreen
import com.alenniboris.nba_app.presentation.screens.enter.views.EnterScreen
import com.alenniboris.nba_app.presentation.screens.followed.views.FollowedScreen
import com.alenniboris.nba_app.presentation.screens.showing.views.ShowingScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    isUserAuthenticated: Boolean
) {

    NavHost(
        navController = navController,
        startDestination =
        if (!isUserAuthenticated) Route.EnterScreenRoute.route
        else Route.ShowingScreenRoute.route,
    ) {

        composable(Route.EnterScreenRoute.route) {
            EnterScreen()
        }

        composable(Route.ShowingScreenRoute.route) {
            ShowingScreen(
                navHostController = navController
            )
        }

        composable(Route.FollowedScreenRoute.route) {
            FollowedScreen(
                navHostController = navController
            )
        }

        composable(
            route = Route.GameDetailsScreenRoute.route,
            arguments = listOf(
                navArgument(NavigationValues.GameIdParameter) { type = NavType.IntType },
            )
        ) { backStackEntry ->

            val gameId = backStackEntry.arguments?.getInt(NavigationValues.GameIdParameter)!!

            GameDetailsScreen(
                gameId = gameId,
                navHostController = navController,
            )
        }

        composable(
            route = Route.PlayerDetailsScreenRoute.route,
            arguments = listOf(
                navArgument(NavigationValues.PlayerIdParameter) { type = NavType.IntType },
            )
        ) { backStackEntry ->

            val playerId = backStackEntry.arguments?.getInt(NavigationValues.PlayerIdParameter)!!

            PlayerDetailsScreen(
                playerId = playerId,
                navHostController = navController,
            )
        }

        composable(
            route = Route.TeamDetailsScreenRoute.route,
            arguments = listOf(
                navArgument(NavigationValues.TeamIdParameter) { type = NavType.IntType },
            )
        ) { backStackEntry ->

            val teamId = backStackEntry.arguments?.getInt(NavigationValues.TeamIdParameter)!!

            TeamDetailsScreen(
                teamId = teamId,
                navHostController = navController,
            )
        }

    }

}