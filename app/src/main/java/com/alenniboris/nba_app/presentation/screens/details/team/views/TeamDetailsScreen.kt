package com.alenniboris.nba_app.presentation.screens.details.team.views

import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.extra.GamesDetailedStatsModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.extra.GamesSimpleStatsModelDomain
import com.alenniboris.nba_app.domain.model.statistics.api.nba.main.TeamStatisticsModelDomain
import com.alenniboris.nba_app.domain.utils.GsonUtil.toJson
import com.alenniboris.nba_app.presentation.model.ActionImplementedUiModel
import com.alenniboris.nba_app.presentation.navigation.Route
import com.alenniboris.nba_app.presentation.screens.details.team.ITeamDetailsScreenEvent
import com.alenniboris.nba_app.presentation.screens.details.team.ITeamDetailsScreenUpdateIntent
import com.alenniboris.nba_app.presentation.screens.details.team.TeamDetailsScreenState
import com.alenniboris.nba_app.presentation.screens.details.team.TeamDetailsScreenVM
import com.alenniboris.nba_app.presentation.screens.utils.StatisticAtActivityComplicatedScoreboard
import com.alenniboris.nba_app.presentation.uikit.theme.CustomRowFilterTopPadding
import com.alenniboris.nba_app.presentation.uikit.theme.DividerWithHeaderTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionDateTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionMainTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionStartTextPadding
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemTextSectionVerticalRowPadding
import com.alenniboris.nba_app.presentation.uikit.theme.Placeholder
import com.alenniboris.nba_app.presentation.uikit.theme.TBShowingScreenPadding
import com.alenniboris.nba_app.presentation.uikit.theme.TeamColumnItemFlagSize
import com.alenniboris.nba_app.presentation.uikit.theme.appColor
import com.alenniboris.nba_app.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.nba_app.presentation.uikit.theme.bodyStyle
import com.alenniboris.nba_app.presentation.uikit.theme.rowItemColor
import com.alenniboris.nba_app.presentation.uikit.theme.rowItemTextColor
import com.alenniboris.nba_app.presentation.uikit.theme.statisticsColumnHorizontalPadding
import com.alenniboris.nba_app.presentation.uikit.theme.statisticsColumnShape
import com.alenniboris.nba_app.presentation.uikit.theme.statisticsColumnTopPadding
import com.alenniboris.nba_app.presentation.uikit.theme.statisticsElementTopPadding
import com.alenniboris.nba_app.presentation.uikit.theme.statisticsElementVerticalPadding
import com.alenniboris.nba_app.presentation.uikit.theme.statisticsPBHeight
import com.alenniboris.nba_app.presentation.uikit.theme.statisticsRowFontSize
import com.alenniboris.nba_app.presentation.uikit.views.AppDividerWithHeader
import com.alenniboris.nba_app.presentation.uikit.views.AppEmptyScreen
import com.alenniboris.nba_app.presentation.uikit.views.AppIconButton
import com.alenniboris.nba_app.presentation.uikit.views.AppItemPictureSection
import com.alenniboris.nba_app.presentation.uikit.views.AppProgressBar
import com.alenniboris.nba_app.presentation.uikit.views.AppRowFilter
import com.alenniboris.nba_app.presentation.uikit.views.AppTopBar
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


fun getTeamDetailsScreenRoute(
    team: TeamModelDomain,
    isReloadingDataNeeded: Boolean = false
): String {
    return Route.TeamDetailsScreenRoute.baseRoute + Uri.encode(team.toJson()) + "&" + isReloadingDataNeeded
}

@Composable
fun TeamDetailsScreen(
    team: TeamModelDomain,
    isReloadingDataNeeded: Boolean = false,
    navHostController: NavHostController
) {

    val teamDetailsScreenVM = koinViewModel<TeamDetailsScreenVM> {
        parametersOf(
            team,
            isReloadingDataNeeded
        )
    }
    val proceedIntentAction by remember { mutableStateOf(teamDetailsScreenVM::proceedIntentAction) }
    val state by teamDetailsScreenVM.screenState.collectAsStateWithLifecycle()
    val event by remember { mutableStateOf(teamDetailsScreenVM.event) }
    val context = LocalContext.current
    var toastMessage by remember {
        mutableStateOf(
            Toast.makeText(context, "", Toast.LENGTH_SHORT)
        )
    }

    LaunchedEffect(Unit) {
        launch {
            event.filterIsInstance<ITeamDetailsScreenEvent.ShowToastMessage>().collect { value ->
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

        launch {
            event.filterIsInstance<ITeamDetailsScreenEvent.NavigateToPreviousPage>().collect() {
                navHostController.popBackStack()
            }
        }
    }

    TeamDetailsScreenUi(
        state = state,
        proceedIntentAction = proceedIntentAction
    )

}


@Composable
@Preview
fun TeamDetailsScreenUi(
    state: TeamDetailsScreenState = TeamDetailsScreenState(team = TeamModelDomain()),
    proceedIntentAction: (ITeamDetailsScreenUpdateIntent) -> Unit = {}
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
            .padding(TBShowingScreenPadding),
        topBar = {

            val iconPainterRes = remember(state.team.isFollowed) {
                if (state.team.isFollowed) {
                    R.drawable.icon_in_followed
                } else R.drawable.icon_not_in_followed
            }

            AppTopBar(
                modifier = Modifier
                    .fillMaxWidth(),
                leftBtnPainter = painterResource(R.drawable.icon_navigate_to_previous_page),
                onLeftBtnClicked = { proceedIntentAction(ITeamDetailsScreenUpdateIntent.NavigateToPreviousScreen) },
                rightBtnPainter = painterResource(iconPainterRes),
                isRightBtnAnimated = true,
                onRightBtnClicked = { proceedIntentAction(ITeamDetailsScreenUpdateIntent.ProceedIsFollowedAction) }
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

            if (state.isTeamDataReloading) {
                AppProgressBar(
                    modifier = Modifier
                        .height(statisticsPBHeight)
                        .fillMaxWidth()
                )
            } else {
                TeamDetailsInfoSection(
                    modifier = Modifier.fillMaxWidth(),
                    element = state.team
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
                                ITeamDetailsScreenUpdateIntent.UpdateSelectedSeason(it)
                            )
                        }
                    )
                },
                isLoading = state.isSeasonsLoading,
                currentSelectedElement = ActionImplementedUiModel(
                    name = state.selectedSeason?.name ?: stringResource(R.string.nan_text)
                )
            )

            AppRowFilter(
                modifier = Modifier
                    .padding(CustomRowFilterTopPadding)
                    .fillMaxWidth(),
                headerColor = appTopBarElementsColor,
                headerText = stringResource(R.string.league_filter),
                emptyText = stringResource(R.string.nothing_found),
                elements = state.listOfLeagues.map {
                    ActionImplementedUiModel(
                        name = it.name,
                        onClick = {
                            proceedIntentAction(
                                ITeamDetailsScreenUpdateIntent.UpdateSelectedLeague(it)
                            )
                        }
                    )
                },
                isLoading = state.isLeaguesLoading,
                currentSelectedElement = ActionImplementedUiModel(
                    name = state.selectedLeague?.name ?: stringResource(R.string.nan_text)
                ),
            )

            if (state.isStatisticsDataLoading) {
                AppProgressBar(
                    modifier = Modifier
                        .height(statisticsPBHeight)
                        .fillMaxWidth()
                )
            } else if (state.teamStatistics == null) {
                AppEmptyScreen()
            } else {
                TeamStatisticsSection(
                    modifier = Modifier.fillMaxWidth(),
                    teamStatistics = state.teamStatistics
                )
            }

        }

    }

}


@Composable
@Preview
private fun TeamStatisticsSection(
    modifier: Modifier = Modifier,
    teamStatistics: TeamStatisticsModelDomain = TeamStatisticsModelDomain()
) {
    Column(
        modifier = modifier
    ) {

        Row(
            modifier = Modifier
                .padding(statisticsElementTopPadding)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.country_filter),
                style = bodyStyle.copy(
                    fontSize = DividerWithHeaderTextSize
                ),
                color = appTopBarElementsColor
            )

            Text(
                text = teamStatistics.country?.name ?: stringResource(R.string.nan_text),
                style = bodyStyle.copy(
                    fontSize = DividerWithHeaderTextSize
                ),
                color = appTopBarElementsColor
            )
        }

        AppDividerWithHeader(
            modifier = Modifier
                .padding(statisticsElementTopPadding)
                .fillMaxWidth(),
            headerText = stringResource(R.string.games_category),
            insidesColor = appTopBarElementsColor
        )

        TeamPlayedGamesStatsUi(
            playedGamesStats = teamStatistics.games?.played
        )

        TeamDetailedGamesStatsUi(
            winsStats = teamStatistics.games?.wins,
            drawsStats = teamStatistics.games?.draws,
            losesStats = teamStatistics.games?.loses,
        )

        AppDividerWithHeader(
            modifier = Modifier
                .padding(statisticsElementTopPadding)
                .fillMaxWidth(),
            headerText = stringResource(R.string.points_text),
            insidesColor = appTopBarElementsColor
        )

        TeamPointsStatsUi(
            pointsHeader = stringResource(R.string.points_for_text),
            pointsStats = teamStatistics.points?.playingFor
        )

        TeamPointsStatsUi(
            pointsHeader = stringResource(R.string.points_against_text),
            pointsStats = teamStatistics.points?.playingAgainst
        )

    }

}


@Composable
@Preview
private fun TeamPlayedGamesStatsUi(
    playedGamesStats: GamesSimpleStatsModelDomain? = GamesSimpleStatsModelDomain()
) {

    var isPlayedGamesStatsVisible by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .padding(statisticsColumnTopPadding)
            .fillMaxWidth()
            .background(
                color = rowItemColor,
                shape = statisticsColumnShape
            )
            .padding(statisticsColumnHorizontalPadding),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.played_games_text),
                color = rowItemTextColor,
                style = bodyStyle.copy(
                    fontSize = statisticsRowFontSize
                )
            )

            AppIconButton(
                isAnimated = true,
                iconPainter = painterResource(
                    if (isPlayedGamesStatsVisible) R.drawable.icon_close_details
                    else R.drawable.icon_open_details
                ),
                tint = rowItemTextColor,
                onClick = {
                    isPlayedGamesStatsVisible = !isPlayedGamesStatsVisible
                }
            )
        }
        AnimatedVisibility(
            visible = isPlayedGamesStatsVisible,
            enter = slideInVertically(
                initialOffsetY = { 0 }
            ),
            exit = slideOutVertically(
                targetOffsetY = { 0 }
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()

            ) {
                StatisticAtActivityComplicatedScoreboard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(statisticsElementVerticalPadding),
                    typeOfActivityText = stringResource(R.string.total_text),
                    firstActivityText = stringResource(R.string.home_text),
                    firstActivityCount = playedGamesStats?.total?.home,
                    secondActivityText = stringResource(R.string.away_text),
                    secondActivityCount = playedGamesStats?.total?.away,
                    thirdActivityText = stringResource(R.string.all_text),
                    thirdActivityCount = playedGamesStats?.total?.all,
                )

                StatisticAtActivityComplicatedScoreboard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(statisticsElementVerticalPadding),
                    typeOfActivityText = stringResource(R.string.average_text),
                    firstActivityText = stringResource(R.string.home_text),
                    firstActivityCount = playedGamesStats?.average?.home,
                    secondActivityText = stringResource(R.string.away_text),
                    secondActivityCount = playedGamesStats?.average?.away,
                    thirdActivityText = stringResource(R.string.all_text),
                    thirdActivityCount = playedGamesStats?.average?.all,
                )
            }
        }
    }
}


@Composable
@Preview
private fun TeamDetailedGamesStatsUi(
    winsStats: GamesDetailedStatsModelDomain? = null,
    losesStats: GamesDetailedStatsModelDomain? = null,
    drawsStats: GamesDetailedStatsModelDomain? = null,
) {

    var isGamesStatsVisible by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .padding(statisticsColumnTopPadding)
            .fillMaxWidth()
            .background(
                color = rowItemColor,
                shape = statisticsColumnShape
            )
            .padding(statisticsColumnHorizontalPadding),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.team_games_results_text),
                color = rowItemTextColor,
                style = bodyStyle.copy(
                    fontSize = statisticsRowFontSize
                )
            )

            AppIconButton(
                isAnimated = true,
                iconPainter = painterResource(
                    if (isGamesStatsVisible) R.drawable.icon_close_details
                    else R.drawable.icon_open_details
                ),
                tint = rowItemTextColor,
                onClick = {
                    isGamesStatsVisible = !isGamesStatsVisible
                }
            )
        }
        AnimatedVisibility(
            visible = isGamesStatsVisible,
            enter = slideInVertically(
                initialOffsetY = { 0 }
            ),
            exit = slideOutVertically(
                targetOffsetY = { 0 }
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()

            ) {
                StatisticAtActivityComplicatedScoreboard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(statisticsElementVerticalPadding),
                    typeOfActivityText = stringResource(R.string.wins_text),
                    firstActivityText = stringResource(R.string.home_text),
                    firstActivityCount = winsStats?.home?.total,
                    secondActivityText = stringResource(R.string.away_text),
                    secondActivityCount = winsStats?.away?.total,
                    thirdActivityText = stringResource(R.string.all_text),
                    thirdActivityCount = winsStats?.all?.total
                )

                StatisticAtActivityComplicatedScoreboard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(statisticsElementVerticalPadding),
                    typeOfActivityText = stringResource(R.string.draws_text),
                    firstActivityText = stringResource(R.string.home_text),
                    firstActivityCount = drawsStats?.home?.total,
                    secondActivityText = stringResource(R.string.away_text),
                    secondActivityCount = drawsStats?.away?.total,
                    thirdActivityText = stringResource(R.string.all_text),
                    thirdActivityCount = drawsStats?.all?.total
                )

                StatisticAtActivityComplicatedScoreboard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(statisticsElementVerticalPadding),
                    typeOfActivityText = stringResource(R.string.loses_text),
                    firstActivityText = stringResource(R.string.home_text),
                    firstActivityCount = losesStats?.home?.total,
                    secondActivityText = stringResource(R.string.away_text),
                    secondActivityCount = losesStats?.away?.total,
                    thirdActivityText = stringResource(R.string.all_text),
                    thirdActivityCount = losesStats?.all?.total
                )
            }
        }
    }

}


@Composable
@Preview
private fun TeamPointsStatsUi(
    pointsStats: GamesSimpleStatsModelDomain? = null,
    pointsHeader: String = "points",
) {
    var isPointsStatsVisible by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .padding(statisticsColumnTopPadding)
            .fillMaxWidth()
            .background(
                color = rowItemColor,
                shape = statisticsColumnShape
            )
            .padding(statisticsColumnHorizontalPadding),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = pointsHeader,
                color = rowItemTextColor,
                style = bodyStyle.copy(
                    fontSize = statisticsRowFontSize
                )
            )

            AppIconButton(
                isAnimated = true,
                iconPainter = painterResource(
                    if (isPointsStatsVisible) R.drawable.icon_close_details
                    else R.drawable.icon_open_details
                ),
                tint = rowItemTextColor,
                onClick = {
                    isPointsStatsVisible = !isPointsStatsVisible
                }
            )
        }
        AnimatedVisibility(
            visible = isPointsStatsVisible,
            enter = slideInVertically(
                initialOffsetY = { 0 }
            ),
            exit = slideOutVertically(
                targetOffsetY = { 0 }
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()

            ) {
                StatisticAtActivityComplicatedScoreboard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(statisticsElementVerticalPadding),
                    typeOfActivityText = stringResource(R.string.total_text),
                    firstActivityText = stringResource(R.string.home_text),
                    firstActivityCount = pointsStats?.total?.home,
                    secondActivityText = stringResource(R.string.away_text),
                    secondActivityCount = pointsStats?.total?.away,
                    thirdActivityText = stringResource(R.string.all_text),
                    thirdActivityCount = pointsStats?.total?.all,
                )

                StatisticAtActivityComplicatedScoreboard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(statisticsElementVerticalPadding),
                    typeOfActivityText = stringResource(R.string.average_text),
                    firstActivityText = stringResource(R.string.home_text),
                    firstActivityCount = pointsStats?.average?.home,
                    secondActivityText = stringResource(R.string.away_text),
                    secondActivityCount = pointsStats?.average?.away,
                    thirdActivityText = stringResource(R.string.all_text),
                    thirdActivityCount = pointsStats?.average?.all,
                )
            }
        }
    }
}

@Composable
@Preview
private fun TeamDetailsInfoSection(
    modifier: Modifier = Modifier,
    element: TeamModelDomain = TeamModelDomain()
) {

    Column(
        modifier = modifier
    ) {

        AppItemPictureSection(
            name = element.name,
            logoUrl = element.logo,
            textColor = appTopBarElementsColor,
            textAlign = Alignment.CenterHorizontally
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = element.country.name,
                style = bodyStyle.copy(
                    fontSize = GameColumnItemTextSectionMainTextSize
                ),
                color = appTopBarElementsColor
            )

            AsyncImage(
                modifier = Modifier
                    .size(TeamColumnItemFlagSize),
                model = element.country.flag,
                placeholder = painterResource(Placeholder),
                contentDescription = stringResource(R.string.team_picture_description)
            )
        }

        Row(
            modifier = Modifier.padding(GameColumnItemTextSectionVerticalRowPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(GameColumnItemTextSectionStartTextPadding),
                text = (element.isNational)?.let {
                    stringResource(
                        if (it) R.string.league_national_text
                        else R.string.league_not_national_text
                    )
                } ?: stringResource(R.string.league_national_no_text_text),
                style = bodyStyle.copy(
                    fontSize = GameColumnItemTextSectionDateTextSize
                ),
                color = appTopBarElementsColor
            )
        }

    }

}