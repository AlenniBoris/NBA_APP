package com.alenniboris.nba_app.presentation.screens.showing.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.domain.model.GameModelDomain
import com.alenniboris.nba_app.domain.model.IStateModel
import com.alenniboris.nba_app.domain.model.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.TeamModelDomain
import com.alenniboris.nba_app.domain.model.params.api.nba.GameRequestParamsModelDomain
import com.alenniboris.nba_app.domain.model.params.api.nba.INbaApiRequestType
import com.alenniboris.nba_app.domain.model.params.api.nba.INbaApiRequestParams
import com.alenniboris.nba_app.domain.model.params.api.nba.toRequestString
import com.alenniboris.nba_app.presentation.model.ActionImplementedUiModel
import com.alenniboris.nba_app.presentation.screens.showing.IShowingScreenEvent
import com.alenniboris.nba_app.presentation.screens.showing.IShowingScreenUpdateIntent
import com.alenniboris.nba_app.presentation.screens.showing.ShowingScreenVM
import com.alenniboris.nba_app.presentation.screens.showing.ShowingScreenValues.Category
import com.alenniboris.nba_app.presentation.screens.showing.ShowingScreenValues.PersonalBtnAction
import com.alenniboris.nba_app.presentation.screens.showing.state.ShowingState
import com.alenniboris.nba_app.presentation.uikit.theme.FloatingActionButtonShape
import com.alenniboris.nba_app.presentation.uikit.theme.FloatingActionButtonTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.FloatingActionButtonWidth
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemHorizontalPadding
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemShape
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemVerticalMargin
import com.alenniboris.nba_app.presentation.uikit.theme.RequestTypeDialogFontSize
import com.alenniboris.nba_app.presentation.uikit.theme.RequestTypeDialogShape
import com.alenniboris.nba_app.presentation.uikit.theme.RequestTypeDialogTopPadding
import com.alenniboris.nba_app.presentation.uikit.theme.TBShowingScreenPadding
import com.alenniboris.nba_app.presentation.uikit.theme.TopBarUIBoxEndPadding
import com.alenniboris.nba_app.presentation.uikit.theme.appColor
import com.alenniboris.nba_app.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.nba_app.presentation.uikit.theme.bodyStyle
import com.alenniboris.nba_app.presentation.uikit.theme.categoryItemColor
import com.alenniboris.nba_app.presentation.uikit.theme.floatingActionButtonColor
import com.alenniboris.nba_app.presentation.uikit.theme.floatingActionButtonTextColor
import com.alenniboris.nba_app.presentation.uikit.theme.titleStyle
import com.alenniboris.nba_app.presentation.uikit.views.AppDropdownMenu
import com.alenniboris.nba_app.presentation.uikit.views.AppEmptyScreen
import com.alenniboris.nba_app.presentation.uikit.views.AppIconButton
import com.alenniboris.nba_app.presentation.uikit.views.AppProgressBar
import com.alenniboris.nba_app.presentation.uikit.views.AppTopBar
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ShowingScreen() {

    val showingScreenVM: ShowingScreenVM = koinViewModel<ShowingScreenVM>()

    val state by showingScreenVM.screenState.collectAsStateWithLifecycle()
    val event by remember { mutableStateOf(showingScreenVM.event) }
    val context = LocalContext.current
    var toastMessage by remember {
        mutableStateOf(
            Toast.makeText(context, "", Toast.LENGTH_SHORT)
        )
    }
    val proceedIntentAction by remember {
        mutableStateOf(showingScreenVM::proceedIntentAction)
    }

    LaunchedEffect(Unit) {
        launch {
            event.filterIsInstance<IShowingScreenEvent.ShowToastMessage>().collect { value ->
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
            event.filterIsInstance<IShowingScreenEvent.NavigateToUserDetailsScreen>().collect {
                toastMessage.cancel()
                toastMessage =
                    Toast.makeText(context, "Navigation", Toast.LENGTH_SHORT)
                toastMessage.show()
            }
        }
    }


    ShowingScreenUi(
        state = state,
        proceedIntentAction = proceedIntentAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
private fun ShowingScreenUi(
    state: ShowingState = ShowingState(),
    proceedIntentAction: (IShowingScreenUpdateIntent) -> Unit = {}
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
            .padding(TBShowingScreenPadding),
        topBar = {
            TopBarUI(
                headerText = state.currentCategory.name,
                isCategoriesListVisible = state.isCategoriesVisible,
                isPersonalActionsVisible = state.isPersonalActionsVisible,
                proceedIntentAction = proceedIntentAction
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    proceedIntentAction(IShowingScreenUpdateIntent.UpdateFilterSheetVisibility)
                },
                modifier = Modifier
                    .border(
                        width = FloatingActionButtonWidth,
                        color = floatingActionButtonTextColor,
                        shape = FloatingActionButtonShape
                    ),
                containerColor = floatingActionButtonColor,
                contentColor = floatingActionButtonTextColor,
                content = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(R.string.fab_icon_description)
                    )
                    Text(
                        text = stringResource(R.string.filter_options_open_action),
                        style = titleStyle.copy(
                            fontSize = FloatingActionButtonTextSize,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            )
        }
    ) { pv ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(appColor)
                .padding(pv)
        ) {

            if (state.isLoading) {
                AppProgressBar(
                    modifier = Modifier.fillMaxSize(),
                )
            } else if (state.elements.isEmpty()) {
                AppEmptyScreen()
            } else {
                ShowElementsUi(
                    category = state.currentCategory,
                    elements = state.elements,
                    proceedIntentAction = proceedIntentAction
                )
            }

            if (state.isRequestTypeChooserVisible) {
                RequestTypeChooserDialog(
                    requestParams = state.requestParams,
                    onTypeSelected = { selectedType ->
                        proceedIntentAction(
                            IShowingScreenUpdateIntent.UpdateRequestType(
                                newType = selectedType
                            )
                        )
                    },
                    onAccept = {
                        proceedIntentAction(
                            IShowingScreenUpdateIntent.SearchForItemsAfterRequestType
                        )
                    },
                    onDecline = {
                        proceedIntentAction(
                            IShowingScreenUpdateIntent.UpdateRequestTypeChooserVisibility(
                                isVisible = false
                            )
                        )
                    }
                )
            }

            if (state.isFilterSheetVisible) {
                ShowingScreenFilter(
                    onDismiss = {
                        proceedIntentAction(
                            IShowingScreenUpdateIntent.ChangeFilterParametersToPreviousValues
                        )
                        proceedIntentAction(
                            IShowingScreenUpdateIntent.UpdateFilterSheetVisibility
                        )
                    },
                    onAccept = {
                        proceedIntentAction(
                            IShowingScreenUpdateIntent.UpdateRequestTypeChooserVisibility(
                                isVisible = true
                            )
                        )
                    },
                    currentCategory = state.currentCategory,
                    mutableFilter = state.mutableFilter,
                    proceedIntentAction = proceedIntentAction
                )
            }

        }
    }

}

@Composable
@Preview
fun RequestTypeChooserDialog(
    requestParams: INbaApiRequestParams = GameRequestParamsModelDomain(),
    onTypeSelected: (INbaApiRequestType) -> Unit = {},
    onAccept: () -> Unit = {},
    onDecline: () -> Unit = {}
) {

    AlertDialog(
        onDismissRequest = onDecline,
        containerColor = appColor,
        shape = RequestTypeDialogShape,
        text = {

            Column(
                modifier = Modifier
                    .background(appColor),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                LazyColumn {
                    items(requestParams.possibleRequestTypes) { type ->

                        Row(
                            modifier = Modifier.padding(RequestTypeDialogTopPadding)
                        ) {
                            Checkbox(
                                checked = type == requestParams.requestType,
                                onCheckedChange = {
                                    onTypeSelected(type)
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = appTopBarElementsColor,
                                )
                            )

                            Text(
                                textAlign = TextAlign.Center,
                                text = stringResource(type.toRequestString()),
                                color = appTopBarElementsColor,
                                style = bodyStyle.copy(
                                    fontSize = RequestTypeDialogFontSize
                                ),
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    AppIconButton(
                        onClick = onDecline,
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

            }

        },
        confirmButton = {}
    )

}

@Composable
@Preview
fun ShowElementsUi(
    category: Category = Category.Games,
    elements: List<IStateModel> = emptyList(),
    followedElementsIds: List<Int> = emptyList(),
    proceedIntentAction: (IShowingScreenUpdateIntent) -> Unit = {}
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(elements) { element ->
            when (category) {
                Category.Games ->
                    (element as? GameModelDomain)?.let {
                        GameColumnItem(
                            modifier = Modifier
                                .padding(
                                    GameColumnItemVerticalMargin
                                )
                                .background(
                                    color = categoryItemColor,
                                    shape = GameColumnItemShape
                                )
                                .padding(GameColumnItemHorizontalPadding),
                            element = element,
                            isElementFollowed = followedElementsIds.contains(element.id),
                            onFollowGameButtonClicked = {
                                proceedIntentAction(
                                    IShowingScreenUpdateIntent.ProceedElementActionWithFollowedDatabase(
                                        element
                                    )
                                )
                            }
                        )
                    }


                Category.Teams ->
                    (element as? TeamModelDomain)?.let {
                        TeamColumnItem(
                            modifier = Modifier
                                .padding(
                                    GameColumnItemVerticalMargin
                                )
                                .background(
                                    color = categoryItemColor,
                                    shape = GameColumnItemShape
                                )
                                .padding(GameColumnItemHorizontalPadding),
                            element = element,
                            isElementFollowed = followedElementsIds.contains(element.id),
                            onFollowGameButtonClicked = {
                                proceedIntentAction(
                                    IShowingScreenUpdateIntent.ProceedElementActionWithFollowedDatabase(
                                        element
                                    )
                                )
                            }
                        )
                    }


                Category.Players ->
                    (element as? PlayerModelDomain)?.let {
                        PlayerColumnItem(
                            modifier = Modifier
                                .padding(
                                    GameColumnItemVerticalMargin
                                )
                                .background(
                                    color = categoryItemColor,
                                    shape = GameColumnItemShape
                                )
                                .padding(GameColumnItemHorizontalPadding),
                            element = element,
                            isElementFollowed = followedElementsIds.contains(element.id),
                            onFollowGameButtonClicked = {
                                proceedIntentAction(
                                    IShowingScreenUpdateIntent.ProceedElementActionWithFollowedDatabase(
                                        element
                                    )
                                )
                            }
                        )
                    }
            }
        }
    }

}

@Composable
@Preview
private fun TopBarUI(
    headerText: String = "",
    isCategoriesListVisible: Boolean = false,
    isPersonalActionsVisible: Boolean = false,
    proceedIntentAction: (IShowingScreenUpdateIntent) -> Unit = {},
) {

    val listOfCategories by remember {
        mutableStateOf(
            Category.entries.toList()
        )
    }
    val listOfPersonalActions by remember {
        mutableStateOf(
            PersonalBtnAction.entries.toList()
        )
    }

    Column {
        AppTopBar(
            modifier = Modifier
                .fillMaxWidth(),
            headerTextString = headerText,
            leftBtnPainter = painterResource(R.drawable.basketball_ball),
            onLeftBtnClicked = {
                proceedIntentAction(
                    IShowingScreenUpdateIntent.UpdateCategoriesListVisibility(
                        isVisible = true
                    )
                )
            },
            rightBtnPainter = painterResource(R.drawable.icon_personal_btn),
            onRightBtnClicked = {
                proceedIntentAction(
                    IShowingScreenUpdateIntent.UpdatePersonalActionsVisibility(
                        isVisible = true
                    )
                )
            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(TopBarUIBoxEndPadding)
        ) {
            AppDropdownMenu(
                modifier = Modifier.align(Alignment.TopStart),
                isMenuVisible = isCategoriesListVisible,
                onDismiss = {
                    proceedIntentAction(
                        IShowingScreenUpdateIntent.UpdateCategoriesListVisibility(
                            isVisible = false
                        )
                    )
                },
                listOfItems = listOfCategories.map { category ->
                    ActionImplementedUiModel(
                        name = category.name,
                        onClick = {
                            proceedIntentAction(
                                IShowingScreenUpdateIntent.UpdateCurrentStateToAnother(
                                    category
                                )
                            )
                            proceedIntentAction(
                                IShowingScreenUpdateIntent.UpdateCategoriesListVisibility(
                                    false
                                )
                            )
                        }
                    )
                }
            )

            AppDropdownMenu(
                modifier = Modifier.align(Alignment.TopEnd),
                isMenuVisible = isPersonalActionsVisible,
                onDismiss = {
                    proceedIntentAction(
                        IShowingScreenUpdateIntent.UpdatePersonalActionsVisibility(
                            isVisible = false
                        )
                    )
                },
                listOfItems = listOfPersonalActions.map { action ->
                    ActionImplementedUiModel(
                        name = action.name,
                        onClick = {
                            proceedIntentAction(
                                IShowingScreenUpdateIntent.ProceedPersonalAction(action)
                            )
                            proceedIntentAction(
                                IShowingScreenUpdateIntent.UpdatePersonalActionsVisibility(
                                    false
                                )
                            )
                        }
                    )
                }
            )
        }
    }
}