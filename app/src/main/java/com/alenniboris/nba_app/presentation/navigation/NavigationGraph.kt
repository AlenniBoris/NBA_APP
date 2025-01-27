package com.alenniboris.nba_app.presentation.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.utils.GsonUtil.fromJson
import com.alenniboris.nba_app.presentation.screens.details.game.views.GameDetailsScreen
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
                navArgument("game") { type = NavType.StringType },
                navArgument("isReloadingNeeded") { type = NavType.BoolType }
            )
        ) { backStackEntry ->

            val gameStr = backStackEntry.arguments?.getString("game")!!
            val game = Uri.decode(gameStr).fromJson<GameModelDomain>()
            val isReloadingNeeded =
                backStackEntry.arguments?.getBoolean("isReloadingNeeded")!!

            GameDetailsScreen(
                game = game,
                isReloadingDataNeeded = isReloadingNeeded,
                navHostController = navController,
            )
        }

    }

}