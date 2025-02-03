package com.alenniboris.nba_app.presentation.screens.details.player.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.presentation.model.ActionImplementedUiModel
import com.alenniboris.nba_app.presentation.navigation.Route
import com.alenniboris.nba_app.presentation.screens.details.game.views.getGameDetailsScreenRoute
import com.alenniboris.nba_app.presentation.screens.details.player.IPlayerDetailsScreenEvent
import com.alenniboris.nba_app.presentation.screens.details.player.IPlayerDetailsScreenUpdateIntent
import com.alenniboris.nba_app.presentation.screens.details.player.PlayerDetailsScreenState
import com.alenniboris.nba_app.presentation.screens.details.player.PlayerDetailsScreenVM
import com.alenniboris.nba_app.presentation.screens.utils.PlayerElementContentContainer
import com.alenniboris.nba_app.presentation.screens.utils.PlayerStatisticsCard
import com.alenniboris.nba_app.presentation.uikit.theme.CustomRowFilterTopPadding
import com.alenniboris.nba_app.presentation.uikit.theme.TBShowingScreenPadding
import com.alenniboris.nba_app.presentation.uikit.theme.appColor
import com.alenniboris.nba_app.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.nba_app.presentation.uikit.theme.statisticsPBHeight
import com.alenniboris.nba_app.presentation.uikit.views.AppEmptyScreen
import com.alenniboris.nba_app.presentation.uikit.views.AppProgressBar
import com.alenniboris.nba_app.presentation.uikit.views.AppRowFilter
import com.alenniboris.nba_app.presentation.uikit.views.AppTopBar
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

fun getPlayerDetailsScreenRoute(
    playerId: Int,
): String {
    return Route.PlayerDetailsScreenRoute.baseRoute + playerId
}

@Composable
fun PlayerDetailsScreen(
    playerId: Int,
    navHostController: NavHostController
) {

    val playerDetailsScreenVM = koinViewModel<PlayerDetailsScreenVM> { parametersOf(playerId) }
    val state by playerDetailsScreenVM.screenState.collectAsStateWithLifecycle()
    val proceedIntentAction by remember { mutableStateOf(playerDetailsScreenVM::proceedIntentAction) }
    val event by remember { mutableStateOf(playerDetailsScreenVM.event) }
    val context = LocalContext.current
    var toastMessage by remember {
        mutableStateOf(
            Toast.makeText(context, "", Toast.LENGTH_SHORT)
        )
    }

    LaunchedEffect(Unit) {
        launch {
            event.filterIsInstance<IPlayerDetailsScreenEvent.NavigateToPreviousPage>().collect {
                navHostController.popBackStack()
            }
        }

        launch {
            event.filterIsInstance<IPlayerDetailsScreenEvent.NavigateToGameDetailsScreen>()
                .collect { value ->
                    navHostController.navigate(
                        getGameDetailsScreenRoute(
                            gameId = value.game.id
                        )
                    )
                }
        }

        launch {
            event.filterIsInstance<IPlayerDetailsScreenEvent.ShowToastMessage>().collect { value ->
                toastMessage.cancel()
                toastMessage =
                    Toast.makeText(
                        context,
                        context.getString(value.message),
                        Toast.LENGTH_SHORT
                    )
                toastMessage.show()
            }
        }
    }

    PlayerDetailsScreenUi(
        state = state,
        proceedIntentAction = proceedIntentAction
    )

}

@Composable
@Preview
fun PlayerDetailsScreenUi(
    state: PlayerDetailsScreenState = PlayerDetailsScreenState(player = PlayerModelDomain()),
    proceedIntentAction: (IPlayerDetailsScreenUpdateIntent) -> Unit = {}
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
            .padding(TBShowingScreenPadding),
        topBar = {

            val iconPainterRes = remember(state.player.isFollowed) {
                if (state.player.isFollowed) {
                    R.drawable.icon_in_followed
                } else R.drawable.icon_not_in_followed
            }

            AppTopBar(
                modifier = Modifier
                    .fillMaxWidth(),
                leftBtnPainter = painterResource(R.drawable.icon_navigate_to_previous_page),
                onLeftBtnClicked = { proceedIntentAction(IPlayerDetailsScreenUpdateIntent.NavigateToPreviousScreen) },
                rightBtnPainter = painterResource(iconPainterRes),
                isRightBtnAnimated = true,
                onRightBtnClicked = { proceedIntentAction(IPlayerDetailsScreenUpdateIntent.ProceedIsFollowedAction) }
            )

        }
    ) { pv ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(appColor)
                .padding(pv)
                .verticalScroll(rememberScrollState()),
        ) {

            if (state.isPlayerDataLoading) {
                AppProgressBar(
                    modifier = Modifier
                        .height(statisticsPBHeight)
                        .fillMaxWidth()
                )
            } else {
                PlayerElementContentContainer(
                    modifier = Modifier.fillMaxWidth(),
                    element = state.player,
                    textColor = appTopBarElementsColor
                )
            }

            AppRowFilter(
                modifier = Modifier
                    .padding(CustomRowFilterTopPadding)
                    .fillMaxWidth(),
                headerColor = appTopBarElementsColor,
                headerText = stringResource(R.string.season_filter),
                elements = state.listOfSeasons.map {
                    ActionImplementedUiModel(
                        name = it.name,
                        onClick = {
                            proceedIntentAction(
                                IPlayerDetailsScreenUpdateIntent.UpdateSelectedSeason(it)
                            )
                        }
                    )
                },
                isLoading = state.isSeasonsLoading,
                currentSelectedElement = ActionImplementedUiModel(
                    name = state.selectedSeason?.name ?: stringResource(R.string.nan_text)
                )
            )

            if (state.isPlayerStatisticsLoading) {
                AppProgressBar(
                    modifier = Modifier
                        .height(statisticsPBHeight)
                        .fillMaxWidth()
                )
            } else if (state.playerStatistics.isEmpty()) {
                AppEmptyScreen()
            } else {
                Column(modifier = Modifier.fillMaxHeight()) {
                    state.playerStatistics.forEach { playerStatistics ->
                        PlayerStatisticsCard(
                            playerStatistics = playerStatistics,
                            exploreBtnText = stringResource(R.string.game_explore_btn_text),
                            isExploreBtnNeeded = true,
                            onExploreBtnClicked = {
                                proceedIntentAction(
                                    IPlayerDetailsScreenUpdateIntent.NavigateToGameDetailsScreen(
                                        gameId = playerStatistics.gameId
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }

    }

}