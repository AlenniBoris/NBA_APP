package com.alenniboris.nba_app.presentation.screens.details.game.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.PlayerStatisticsModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.TeamInGameStatisticsModelDomain
import com.alenniboris.nba_app.presentation.screens.destinations.PlayerDetailsScreenDestination
import com.alenniboris.nba_app.presentation.screens.destinations.TeamDetailsScreenDestination
import com.alenniboris.nba_app.presentation.screens.details.game.GameDetailsScreenState
import com.alenniboris.nba_app.presentation.screens.details.game.GameDetailsScreenVM
import com.alenniboris.nba_app.presentation.screens.details.game.GameStatisticsType
import com.alenniboris.nba_app.presentation.screens.details.game.GameTeamType
import com.alenniboris.nba_app.presentation.screens.details.game.IGameDetailsScreenEvent
import com.alenniboris.nba_app.presentation.screens.details.game.IGameDetailsScreenUpdateIntent
import com.alenniboris.nba_app.presentation.screens.utils.GameElementContentContainer
import com.alenniboris.nba_app.presentation.screens.utils.PlayerStatisticsCard
import com.alenniboris.nba_app.presentation.uikit.theme.TBShowingScreenPadding
import com.alenniboris.nba_app.presentation.uikit.theme.appColor
import com.alenniboris.nba_app.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.nba_app.presentation.uikit.views.AppAlertScreen
import com.alenniboris.nba_app.presentation.uikit.views.AppEmptyScreen
import com.alenniboris.nba_app.presentation.uikit.views.AppProgressBar
import com.alenniboris.nba_app.presentation.uikit.views.AppTopBar
import com.alenniboris.nba_app.presentation.uikit.views.appVideoPlayer.AppVideoPlayer
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
fun GameDetailsScreen(
    gameId: Int,
    navigator: DestinationsNavigator
) {
    val gameDetailsScreenVM = koinViewModel<GameDetailsScreenVM> { parametersOf(gameId) }
    val state by gameDetailsScreenVM.screenState.collectAsStateWithLifecycle()
    val proceedIntentAction by remember { mutableStateOf(gameDetailsScreenVM::proceedUpdateIntent) }
    val event by remember { mutableStateOf(gameDetailsScreenVM.event) }
    val context = LocalContext.current
    var toastMessage by remember {
        mutableStateOf(
            Toast.makeText(context, "", Toast.LENGTH_SHORT)
        )
    }

    LaunchedEffect(Unit) {
        launch {
            event.filterIsInstance<IGameDetailsScreenEvent.NavigateToPreviousPage>().collect() {
                navigator.popBackStack()
            }
        }

        launch {
            event.filterIsInstance<IGameDetailsScreenEvent.NavigateToPlayerDetailsScreen>()
                .collect() {
                    navigator.navigate(PlayerDetailsScreenDestination(playerId = it.player.id))
                }
        }

        launch {
            event.filterIsInstance<IGameDetailsScreenEvent.NavigateToTeamDetailsScreen>()
                .collect() {
                    navigator.navigate(TeamDetailsScreenDestination(teamId = it.team.id))
                }
        }

        event.filterIsInstance<IGameDetailsScreenEvent.ShowToastMessage>().collect { value ->
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

    GameDetailsScreenUi(
        state = state,
        proceedIntentAction = proceedIntentAction
    )
}

@Composable
@Preview
private fun GameDetailsScreenUi(
    state: GameDetailsScreenState = GameDetailsScreenState(game = GameModelDomain()),
    proceedIntentAction: (IGameDetailsScreenUpdateIntent) -> Unit = {}
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
            .padding(TBShowingScreenPadding),
        topBar = {

            val iconPainterRes = remember(state.game.isFollowed) {
                if (state.game.isFollowed) {
                    R.drawable.icon_in_followed
                } else R.drawable.icon_not_in_followed
            }

            AppTopBar(
                modifier = Modifier
                    .fillMaxWidth(),
                leftBtnPainter = painterResource(R.drawable.icon_navigate_to_previous_page),
                onLeftBtnClicked = { proceedIntentAction(IGameDetailsScreenUpdateIntent.NavigateToPreviousScreen) },
                rightBtnPainter = painterResource(iconPainterRes),
                isRightBtnAnimated = true,
                onRightBtnClicked = { proceedIntentAction(IGameDetailsScreenUpdateIntent.ProceedIsFollowedAction) }
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
                state.isLoading -> {
                    AppProgressBar(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }

                state.isReloadedWithError -> {
                    AppAlertScreen(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }

                else -> {
                    GameElementContentContainer(
                        modifier = Modifier.fillMaxWidth(),
                        textColor = appTopBarElementsColor,
                        element = state.game,
                        onTeamSectionClicked = { id ->
                            proceedIntentAction(
                                IGameDetailsScreenUpdateIntent.NavigateToTeamDetailsScreen(
                                    id
                                )
                            )
                        }
                    )

                    AppVideoPlayer(
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .fillMaxWidth()
                            .height(200.dp),
                        videoUrl = state.game.gameHighlightsUrl,
                        textString = stringResource(R.string.highlights_text),
                        textColor = appTopBarElementsColor
                    )

                    GameDetailsTabSection(
                        currentlyViewedTeam = state.currentlyViewedTeam,
                        playersStatisticsModelDomain = when (state.currentlyViewedTeam) {
                            GameTeamType.Home -> state.gameStatistics.homePlayersStatistics
                            GameTeamType.Visitors -> state.gameStatistics.visitorPlayersStatistics
                        },
                        teamStatisticsModelDomain = when (state.currentlyViewedTeam) {
                            GameTeamType.Home -> state.gameStatistics.homeTeamStatistics
                            GameTeamType.Visitors -> state.gameStatistics.visitorsTeamStatistics
                        },
                        proceedIntentAction = proceedIntentAction
                    )
                }
            }
        }
    }

}

@Composable
@Preview
private fun GameDetailsTabSection(
    currentlyViewedTeam: GameTeamType = GameTeamType.Home,
    playersStatisticsModelDomain: List<PlayerStatisticsModelDomain> = emptyList(),
    teamStatisticsModelDomain: TeamInGameStatisticsModelDomain? = TeamInGameStatisticsModelDomain(),
    proceedIntentAction: (IGameDetailsScreenUpdateIntent) -> Unit = {}
) {

    Column {
        CurrentViewedTeamSelectionTabRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            currentlyViewedTeam = currentlyViewedTeam,
            proceedIntentAction = proceedIntentAction
        )

        CurrentViewedGameStatisticsContainer(
            playersStatisticsModelDomain = playersStatisticsModelDomain,
            teamStatisticsModelDomain = teamStatisticsModelDomain,
            proceedIntentAction = proceedIntentAction
        )

    }

}

@Composable
@Preview
private fun CurrentViewedTeamSelectionTabRow(
    modifier: Modifier = Modifier,
    currentlyViewedTeam: GameTeamType = GameTeamType.Home,
    proceedIntentAction: (IGameDetailsScreenUpdateIntent) -> Unit = {}
) {
    val teamTypes by remember { mutableStateOf(GameTeamType.entries.toList()) }

    TabRow(
        divider = {
            Spacer(modifier = Modifier.height(5.dp))
        },
        indicator = { tabPositions ->
            SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(
                    tabPositions[
                        teamTypes.indexOf(
                            currentlyViewedTeam
                        )
                    ]
                ),
                height = 5.dp,
                color = appTopBarElementsColor
            )
        },
        modifier = modifier,
        selectedTabIndex = teamTypes.indexOf(currentlyViewedTeam)
    ) {
        teamTypes.forEach { team ->
            Tab(
                modifier = Modifier
                    .background(appColor)
                    .fillMaxWidth(),
                text = {
                    Text(
                        text = team.name,
                        color = appTopBarElementsColor
                    )
                },
                selected = currentlyViewedTeam == team,
                onClick = {
                    proceedIntentAction(
                        IGameDetailsScreenUpdateIntent.ProceedChangeViewedTeamAction(newTeam = team)
                    )
                }
            )
        }
    }
}


@Composable
@Preview
private fun CurrentViewedGameStatisticsContainer(
    playersStatisticsModelDomain: List<PlayerStatisticsModelDomain> = emptyList(),
    teamStatisticsModelDomain: TeamInGameStatisticsModelDomain? = TeamInGameStatisticsModelDomain(),
    proceedIntentAction: (IGameDetailsScreenUpdateIntent) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {

        val detailsTypes by remember { mutableStateOf(GameStatisticsType.entries.toList()) }
        val detailsPagerState = rememberPagerState(pageCount = { detailsTypes.size })
        val scope = rememberCoroutineScope()

        TabRow(
            selectedTabIndex = detailsPagerState.currentPage,
            divider = {
                Spacer(modifier = Modifier.height(5.dp))
            },
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[detailsPagerState.currentPage]),
                    height = 5.dp,
                    color = appTopBarElementsColor
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            detailsTypes.forEachIndexed { index, statisticsType ->
                Tab(
                    modifier = Modifier
                        .background(appColor)
                        .fillMaxWidth(),
                    text = {
                        Text(
                            text = statisticsType.name,
                            color = appTopBarElementsColor
                        )
                    },
                    selected = detailsPagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            detailsPagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }

        HorizontalPager(
            state = detailsPagerState,
            verticalAlignment = Alignment.Top
        ) { ind ->

            val currentStatisticsType by remember { mutableStateOf(detailsTypes[ind]) }

            when (currentStatisticsType) {
                GameStatisticsType.Players -> {
                    if (playersStatisticsModelDomain.isNotEmpty()) {

                        Column(modifier = Modifier.fillMaxHeight()) {
                            playersStatisticsModelDomain.forEach { playerStatistics ->
                                PlayerStatisticsCard(
                                    playerStatistics = playerStatistics,
                                    isExploreBtnNeeded = true,
                                    exploreBtnText = stringResource(R.string.player_explore_btn_text),
                                    onExploreBtnClicked = {
                                        proceedIntentAction(
                                            IGameDetailsScreenUpdateIntent.NavigateToPlayerDetailsScreen(
                                                playerId = playerStatistics.playerId
                                            )
                                        )
                                    },
                                )
                            }
                        }

                    } else {
                        AppEmptyScreen(
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }

                GameStatisticsType.Game -> {
                    teamStatisticsModelDomain?.let {
                        if (!teamStatisticsModelDomain.isEmpty) {
                            TeamStatisticsInGameUi(
                                teamStatistics = teamStatisticsModelDomain
                            )
                        } else {
                            AppEmptyScreen(
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }
                    } ?: AppEmptyScreen(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }

    }
}


