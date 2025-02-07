package com.alenniboris.nba_app.presentation.screens.details.player.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.presentation.model.ActionImplementedUiModel
import com.alenniboris.nba_app.presentation.screens.destinations.GameDetailsScreenDestination
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
import com.alenniboris.nba_app.presentation.uikit.views.AppAlertScreen
import com.alenniboris.nba_app.presentation.uikit.views.AppEmptyScreen
import com.alenniboris.nba_app.presentation.uikit.views.AppProgressBar
import com.alenniboris.nba_app.presentation.uikit.views.AppRowFilter
import com.alenniboris.nba_app.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@RootNavGraph
@Destination
@Composable
fun PlayerDetailsScreen(
    playerId: Int,
    navigator: DestinationsNavigator
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
                navigator.popBackStack()
            }
        }

        launch {
            event.filterIsInstance<IPlayerDetailsScreenEvent.NavigateToGameDetailsScreen>()
                .collect {
                    navigator.navigate(GameDetailsScreenDestination(gameId = it.game.id))
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

            when {
                state.isPlayerDataLoading -> {
                    AppProgressBar(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }

                state.isPlayerStatisticsReloadedWithError -> {
                    AppAlertScreen(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }

                else -> {
                    PlayerElementContentContainer(
                        modifier = Modifier.fillMaxWidth(),
                        element = state.player,
                        textColor = appTopBarElementsColor
                    )

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

                    when {
                        state.isPlayerStatisticsLoading -> {
                            AppProgressBar(
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }

                        state.playerStatistics.isEmpty() -> {
                            AppEmptyScreen(
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }

                        else -> {
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

        }

    }

}