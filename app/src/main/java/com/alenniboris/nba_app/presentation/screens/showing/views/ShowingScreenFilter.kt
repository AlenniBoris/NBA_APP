package com.alenniboris.nba_app.presentation.screens.showing.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.presentation.screens.showing.IShowingScreenUpdateIntent
import com.alenniboris.nba_app.domain.utils.NbaApiCategory
import com.alenniboris.nba_app.presentation.screens.showing.state.ShowingFilter
import com.alenniboris.nba_app.presentation.uikit.theme.FilterDragHandleHeight
import com.alenniboris.nba_app.presentation.uikit.theme.FilterDragHandlePadding
import com.alenniboris.nba_app.presentation.uikit.theme.FilterDragHandleShape
import com.alenniboris.nba_app.presentation.uikit.theme.FilterDragHandleWidth
import com.alenniboris.nba_app.presentation.uikit.theme.FilterSheetColumnMargin
import com.alenniboris.nba_app.presentation.uikit.theme.FilterSheetColumnPadding
import com.alenniboris.nba_app.presentation.uikit.theme.FilterSheetFilterColumnMargin
import com.alenniboris.nba_app.presentation.uikit.theme.FilterSheetOpeningCoefficient
import com.alenniboris.nba_app.presentation.uikit.theme.FilterSheetShape
import com.alenniboris.nba_app.presentation.uikit.theme.FilterSheetTonalElevation
import com.alenniboris.nba_app.presentation.uikit.theme.appColor
import com.alenniboris.nba_app.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.nba_app.presentation.uikit.views.AppIconButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun ShowingScreenFilter(
    onDismiss: () -> Unit = {},
    onAccept: () -> Unit = {},
    sheetState: SheetState = rememberModalBottomSheetState(),
    currentCategory: NbaApiCategory = NbaApiCategory.Games,
    mutableFilter: ShowingFilter = ShowingFilter(),
    proceedIntentAction: (IShowingScreenUpdateIntent) -> Unit = {}
) {
    ModalBottomSheet(
        modifier = Modifier.height(
            (LocalConfiguration.current.screenHeightDp.dp.value * FilterSheetOpeningCoefficient).dp
        ),
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = FilterSheetShape,
        containerColor = appColor,
        tonalElevation = FilterSheetTonalElevation,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(FilterDragHandlePadding)
                    .width(FilterDragHandleWidth)
                    .height(FilterDragHandleHeight)
                    .clip(FilterDragHandleShape)
                    .background(appTopBarElementsColor)

            )
        }
    ) {

        Column(
            modifier = Modifier
                .padding(FilterSheetColumnMargin)
                .fillMaxSize()
                .background(appColor)
                .padding(FilterSheetColumnPadding)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                AppIconButton(
                    onClick = onDismiss,
                    iconPainter = painterResource(R.drawable.close_filter_sheet_btn),
                    contentDescription = stringResource(R.string.decline_icon_description),
                    tint = appTopBarElementsColor
                )

                AppIconButton(
                    onClick = onAccept,
                    iconPainter = painterResource(R.drawable.accept_filter_changes_btn),
                    contentDescription = stringResource(R.string.accept_icon_description),
                    tint = appTopBarElementsColor
                )

            }

            FilterColumn(
                modifier = Modifier
                    .padding(FilterSheetFilterColumnMargin)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                currentCategory = currentCategory,
                mutableFilter = mutableFilter,
                proceedIntentAction = proceedIntentAction
            )
        }

    }
}

@Composable
@Preview
private fun FilterColumn(
    modifier: Modifier = Modifier,
    currentCategory: NbaApiCategory = NbaApiCategory.Games,
    mutableFilter: ShowingFilter = ShowingFilter(),
    proceedIntentAction: (IShowingScreenUpdateIntent) -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {

        when (currentCategory) {
            NbaApiCategory.Games -> GamesFilters(
                currentDateTime = mutableFilter.selectedDate,
                currentDateTimeText = mutableFilter.selectedDateText,

                countries = mutableFilter.listOfCountries,
                currentSelectedCountry = mutableFilter.selectedCountry,
                isCountriesLoading = mutableFilter.isCountriesLoading,

                statuses = mutableFilter.listOfStatuses,
                currentSelectedStatus = mutableFilter.selectedStatus,

                leagues = mutableFilter.listOfLeagues,
                currentSelectedLeague = mutableFilter.selectedLeague,
                isLeaguesLoading = mutableFilter.isLeaguesLoading,

                seasons = mutableFilter.listOfSeasons,
                currentSelectedSeason = mutableFilter.selectedSeason,
                isSeasonsLoading = mutableFilter.isSeasonsLoading,

                proceedIntentAction = proceedIntentAction
            )

            NbaApiCategory.Teams -> TeamsFilters(
                seasons = mutableFilter.listOfSeasons,
                currentSelectedSeason = mutableFilter.selectedSeason,
                isSeasonsLoading = mutableFilter.isSeasonsLoading,

                leagues = mutableFilter.listOfLeagues,
                currentSelectedLeague = mutableFilter.selectedLeague,
                isLeaguesLoading = mutableFilter.isLeaguesLoading,

                countries = mutableFilter.listOfCountries,
                currentSelectedCountry = mutableFilter.selectedCountry,
                isCountriesLoading = mutableFilter.isCountriesLoading,

                enteredQueryValue = mutableFilter.enteredQuery,
                proceedIntentAction = proceedIntentAction
            )

            NbaApiCategory.Players -> PlayersFilters(
                seasons = mutableFilter.listOfSeasons,
                currentSelectedSeason = mutableFilter.selectedSeason,
                isSeasonsLoading = mutableFilter.isSeasonsLoading,

                leagues = mutableFilter.listOfLeagues,
                currentSelectedLeague = mutableFilter.selectedLeague,
                isLeaguesLoading = mutableFilter.isLeaguesLoading,

                enteredQueryValue = mutableFilter.enteredQuery,

                countries = mutableFilter.listOfCountries,
                currentSelectedCountry = mutableFilter.selectedCountry,
                isCountriesLoading = mutableFilter.isCountriesLoading,

                teams = mutableFilter.listOfTeams,
                currentSelectedTeam = mutableFilter.selectedTeam,
                isTeamsLoading = mutableFilter.isTeamsLoading,

                proceedIntentAction= proceedIntentAction
            )
        }

    }
}