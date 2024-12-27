package com.alenniboris.nba_app.presentation.screens.showing.state

import com.alenniboris.nba_app.presentation.model.IStateUiModel
import com.alenniboris.nba_app.presentation.screens.showing.ShowingScreenValues.Category


data class ShowingState(
    val currentCategory: Category = Category.Games,
    val elements: List<IStateUiModel> = emptyList(),
//    val elements: List<IStateUiModel> = PresentationValues.PossibleGamesStub,
//    val elements: List<IStateUiModel> = PresentationValues.PossiblePlayersStub,
//    val elements: List<IStateUiModel> = PresentationValues.PossibleTeamsStub,
    val isLoading: Boolean = false,
    val filter: ShowingFilter = ShowingFilter(),
    val mutableFilter: ShowingFilter = filter,

    val isCategoriesVisible: Boolean = false,
    val isPersonalActionsVisible: Boolean = false,
    val isFilterSheetVisible: Boolean = false
)