package com.alenniboris.nba_app.presentation.screens.followed.views

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.IStateModel
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.utils.NbaApiCategory
import com.alenniboris.nba_app.presentation.mappers.toStringMessage
import com.alenniboris.nba_app.presentation.navigation.Route
import com.alenniboris.nba_app.presentation.screens.details.game.views.getGameDetailsScreenRoute
import com.alenniboris.nba_app.presentation.screens.details.team.views.getTeamDetailsScreenRoute
import com.alenniboris.nba_app.presentation.screens.followed.FollowedScreenVM
import com.alenniboris.nba_app.presentation.screens.followed.FollowedState
import com.alenniboris.nba_app.presentation.screens.followed.IFollowedScreenEvent
import com.alenniboris.nba_app.presentation.screens.followed.IFollowedScreenUpdateIntent
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemHorizontalPadding
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemShape
import com.alenniboris.nba_app.presentation.uikit.theme.RowFilterItemTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.TBShowingScreenPadding
import com.alenniboris.nba_app.presentation.uikit.theme.appColor
import com.alenniboris.nba_app.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.nba_app.presentation.uikit.theme.bodyStyle
import com.alenniboris.nba_app.presentation.uikit.theme.categoryItemColor
import com.alenniboris.nba_app.presentation.uikit.theme.pagerCurrentPageCircleSize
import com.alenniboris.nba_app.presentation.uikit.theme.pagerCurrentPagePadding
import com.alenniboris.nba_app.presentation.uikit.theme.pagerElementsEmptyHeight
import com.alenniboris.nba_app.presentation.uikit.theme.pagerNotSelectedColor
import com.alenniboris.nba_app.presentation.uikit.theme.pagerSectionPadding
import com.alenniboris.nba_app.presentation.uikit.theme.pagerSelectedColor
import com.alenniboris.nba_app.presentation.uikit.views.AppDividerWithHeader
import com.alenniboris.nba_app.presentation.uikit.views.AppTopBar
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun FollowedScreen(
    navHostController: NavHostController
) {

    val followedScreenVM: FollowedScreenVM = koinViewModel<FollowedScreenVM>()
    val state by followedScreenVM.screenState.collectAsStateWithLifecycle()
    val proceedIntentAction by remember {
        mutableStateOf(followedScreenVM::proceedUpdateIntent)
    }
    val event by remember { mutableStateOf(followedScreenVM.event) }

    LaunchedEffect(Unit) {
        launch {
            event.filterIsInstance<IFollowedScreenEvent.NavigateToPreviousPage>().collect() {
                navHostController.popBackStack()
            }
        }

        launch {
            event.filterIsInstance<IFollowedScreenEvent.NavigateToGameDetailsPage>().collect {
                navHostController.navigate(
                    getGameDetailsScreenRoute(
                        game = it.game,
                        isReloadingDataNeeded = true
                    )
                )
            }
        }

        launch {
            event.filterIsInstance<IFollowedScreenEvent.NavigateToTeamDetailsPage>().collect {
                navHostController.navigate(
                    getTeamDetailsScreenRoute(
                        team = it.team,
                        isReloadingDataNeeded = true
                    )
                )
            }
        }

        launch {
            event.filterIsInstance<IFollowedScreenEvent.NavigateToPlayerDetailsPage>().collect {
                navHostController.navigate(Route.PlayerDetailsScreenRoute.route)
            }
        }
    }

    FollowedScreenUi(
        state = state,
        proceedIntentAction = proceedIntentAction
    )
}

@Composable
@Preview
private fun FollowedScreenUi(
    state: FollowedState = FollowedState(),
    proceedIntentAction: (IFollowedScreenUpdateIntent) -> Unit = {}
) {

    val listOfCategories by remember {
        mutableStateOf(
            NbaApiCategory.entries.toList()
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
            .padding(TBShowingScreenPadding),
        topBar = {
            AppTopBar(
                modifier = Modifier
                    .fillMaxWidth(),
                headerTextString = stringResource(R.string.followed_screen_header),
                leftBtnPainter = painterResource(R.drawable.icon_navigate_to_previous_page),
                onLeftBtnClicked = {
                    proceedIntentAction(
                        IFollowedScreenUpdateIntent.NavigateToPreviousScreen
                    )
                }
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

            listOfCategories.forEach { category ->
                PagerSection(
                    modifier = Modifier
                        .padding(pagerSectionPadding)
                        .fillMaxWidth(),
                    categoryText = stringResource(category.toStringMessage()),
                    emptyText = stringResource(R.string.nothing_followed_category),
                    elements = when (category) {
                        NbaApiCategory.Games -> state.followedGames
                        NbaApiCategory.Teams -> state.followedTeams
                        NbaApiCategory.Players -> state.followedPlayers
                    },
                    proceedIntentAction = proceedIntentAction
                )
            }

        }

    }

}

@Composable
@Preview
private fun PagerSection(
    modifier: Modifier = Modifier,
    categoryText: String = "",
    emptyText: String = "",
    elements: List<IStateModel> = emptyList(),
    proceedIntentAction: (IFollowedScreenUpdateIntent) -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        AppDividerWithHeader(
            modifier = Modifier.fillMaxWidth(),
            headerText = categoryText,
            insidesColor = appTopBarElementsColor
        )

        if (elements.isNotEmpty()) {
            PagerSectionElementsCards(
                modifier = Modifier
                    .padding(pagerSectionPadding)
                    .fillMaxWidth(),
                list = elements,
                proceedIntentAction = proceedIntentAction
            )
        } else {
            Box(
                modifier = Modifier
                    .height(pagerElementsEmptyHeight)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = emptyText,
                    color = appTopBarElementsColor,
                    style = bodyStyle.copy(
                        fontSize = RowFilterItemTextSize
                    ),
                )
            }
        }
    }
}

@Composable
@Preview
fun PagerSectionElementsCards(
    modifier: Modifier = Modifier,
    list: List<IStateModel> = emptyList(),
    proceedIntentAction: (IFollowedScreenUpdateIntent) -> Unit = {}
) {

    val pagerState = rememberPagerState(
        pageCount = { list.size }
    )

    Column(
        modifier = modifier
    ) {
        HorizontalPager(
            state = pagerState,
        ) {
            PagerItem(
                element = list.getOrNull(it),
                proceedIntentAction = proceedIntentAction
            )
        }

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) pagerSelectedColor else pagerNotSelectedColor
                Box(
                    modifier = Modifier
                        .padding(pagerCurrentPagePadding)
                        .clip(CircleShape)
                        .background(
                            animateColorAsState(
                                color,
                                tween(800),
                                label = ""
                            ).value
                        )
                        .size(pagerCurrentPageCircleSize)
                )
            }
        }
    }

}

@Composable
@Preview
fun PagerItem(
    element: IStateModel? = GameModelDomain(),
    proceedIntentAction: (IFollowedScreenUpdateIntent) -> Unit = {}
) {
    element?.let {
        when (element) {
            is GameModelDomain ->
                PagerGameItem(
                    element = element,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = categoryItemColor,
                            shape = GameColumnItemShape
                        )
                        .padding(GameColumnItemHorizontalPadding)
                        .clickable {
                            proceedIntentAction(
                                IFollowedScreenUpdateIntent.ProceedNavigationToGameDetailsScreen(
                                    element
                                )
                            )
                        },
                    onFollowedBtnClicked = {
                        proceedIntentAction(
                            IFollowedScreenUpdateIntent.ProceedRemovingFromFollowedAction(element)
                        )
                    },
                )

            is PlayerModelDomain ->
                PagerPlayerItem(
                    modifier = Modifier
                        .background(
                            color = categoryItemColor,
                            shape = GameColumnItemShape
                        )
                        .padding(GameColumnItemHorizontalPadding)
                        .clickable {
                            proceedIntentAction(
                                IFollowedScreenUpdateIntent.ProceedNavigationToPlayerDetailsScreen(
                                    element
                                )
                            )
                        },
                    element = element,
                    onFollowedBtnClicked = {
                        proceedIntentAction(
                            IFollowedScreenUpdateIntent.ProceedRemovingFromFollowedAction(element)
                        )
                    }
                )

            is TeamModelDomain ->
                PagerTeamItem(
                    modifier = Modifier
                        .background(
                            color = categoryItemColor,
                            shape = GameColumnItemShape
                        )
                        .padding(GameColumnItemHorizontalPadding)
                        .clickable {
                            proceedIntentAction(
                                IFollowedScreenUpdateIntent.ProceedNavigationToTeamDetailsScreen(
                                    element
                                )
                            )
                        },
                    element = element,
                    onFollowedBtnClicked = {
                        proceedIntentAction(
                            IFollowedScreenUpdateIntent.ProceedRemovingFromFollowedAction(element)
                        )
                    }
                )

        }
    }
}




