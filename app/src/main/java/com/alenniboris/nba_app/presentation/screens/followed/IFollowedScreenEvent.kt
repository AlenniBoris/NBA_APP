package com.alenniboris.nba_app.presentation.screens.followed

import com.alenniboris.nba_app.domain.model.api.nba.IStateModel

interface IFollowedScreenEvent {

    data object NavigateToPreviousPage: IFollowedScreenEvent

    data class NavigateToElementDetailsPage(val element: IStateModel): IFollowedScreenEvent

}