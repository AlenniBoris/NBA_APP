package com.alenniboris.nba_app.presentation.screens.showing.state

import com.alenniboris.nba_app.domain.model.api.nba.IStateModel
import com.alenniboris.nba_app.domain.model.params.api.nba.GameRequestParamsModelDomain
import com.alenniboris.nba_app.domain.model.params.api.nba.INbaApiElementsRequestParams
import com.alenniboris.nba_app.domain.utils.NbaApiCategory


data class ShowingState(
    val currentCategory: NbaApiCategory = NbaApiCategory.Games,
    val elements: List<IStateModel> = emptyList(),
    val isLoading: Boolean = false,
    val filter: ShowingFilter = ShowingFilter(),
    val requestParams: INbaApiElementsRequestParams = GameRequestParamsModelDomain(
        requestedDate = filter.selectedDate
    ),
    val mutableFilter: ShowingFilter = filter,
    val isCategoriesVisible: Boolean = false,
    val isPersonalActionsVisible: Boolean = false,
    val isFilterSheetVisible: Boolean = false,
    val isRequestTypeChooserVisible: Boolean = false
)