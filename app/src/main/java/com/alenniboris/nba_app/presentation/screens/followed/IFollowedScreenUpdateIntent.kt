package com.alenniboris.nba_app.presentation.screens.followed

import com.alenniboris.nba_app.domain.model.IStateModel
import com.alenniboris.nba_app.domain.model.entity.IEntityModelDomain

interface IFollowedScreenUpdateIntent {

    data class proceedRemovingAction(val element: IStateModel) : IFollowedScreenUpdateIntent

    data class proceedOpeningElementDetailsScreenAction(val element: IStateModel) :
        IFollowedScreenUpdateIntent

    data object navigateToPreviousScreen: IFollowedScreenUpdateIntent
}