package com.alenniboris.nba_app.presentation.screens.showing.state

import com.alenniboris.nba_app.domain.model.params.api.nba.GameRequestParamsModelDomain
import com.alenniboris.nba_app.domain.model.params.api.nba.INbaApiRequestParams
import com.alenniboris.nba_app.domain.model.IStateModel
import com.alenniboris.nba_app.presentation.screens.showing.ShowingScreenValues.Category


data class ShowingState(
    val currentCategory: Category = Category.Games,
    val elements: List<IStateModel> = emptyList(),
    val isLoading: Boolean = false,
    val filter: ShowingFilter = ShowingFilter(),
    val requestParams: INbaApiRequestParams = GameRequestParamsModelDomain(
        requestedDate = filter.selectedDate
    ),
    val mutableFilter: ShowingFilter = filter,
    val isCategoriesVisible: Boolean = false,
    val isPersonalActionsVisible: Boolean = false,
    val isFilterSheetVisible: Boolean = false,
    val isRequestTypeChooserVisible: Boolean = false
)