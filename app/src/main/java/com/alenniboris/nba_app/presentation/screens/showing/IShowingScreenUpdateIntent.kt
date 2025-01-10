package com.alenniboris.nba_app.presentation.screens.showing

import com.alenniboris.nba_app.domain.model.IStateModel
import com.alenniboris.nba_app.domain.model.TeamModelDomain
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.model.params.api.nba.INbaApiRequestType
import com.alenniboris.nba_app.domain.utils.GameStatus
import java.util.Date

interface IShowingScreenUpdateIntent {

    data class UpdateSelectedDate(val newDate: Date) : IShowingScreenUpdateIntent

    data class UpdateSelectedSeason(val newSeason: SeasonModelDomain) : IShowingScreenUpdateIntent

    data class UpdateSelectedCountry(val newCountry: CountryModelDomain) :
        IShowingScreenUpdateIntent

    data class UpdateSelectedStatus(val newStatus: GameStatus) :
        IShowingScreenUpdateIntent

    data class UpdateEnteredSearch(val newQuery: String) : IShowingScreenUpdateIntent

    data class UpdateSelectedLeague(val newLeague: LeagueModelDomain) : IShowingScreenUpdateIntent

    data class UpdateCurrentStateToAnother(val category: ShowingScreenValues.Category) :
        IShowingScreenUpdateIntent

    data class ProceedPersonalAction(val action: ShowingScreenValues.PersonalBtnAction) :
        IShowingScreenUpdateIntent

    data object ProceedSearchRequestByFilterQuery : IShowingScreenUpdateIntent

    data object ChangeFilterParametersToPreviousValues : IShowingScreenUpdateIntent

    data class ProceedElementActionWithFollowedDatabase(val element: IStateModel) :
        IShowingScreenUpdateIntent

    data object UpdateFilterSheetVisibility : IShowingScreenUpdateIntent

    data class UpdateCategoriesListVisibility(val isVisible: Boolean) : IShowingScreenUpdateIntent

    data class UpdatePersonalActionsVisibility(val isVisible: Boolean) : IShowingScreenUpdateIntent

    data class UpdateRequestTypeChooserVisibility(val isVisible: Boolean): IShowingScreenUpdateIntent

    data class UpdateRequestType(val newType: INbaApiRequestType): IShowingScreenUpdateIntent

    data object SearchForItemsAfterRequestType: IShowingScreenUpdateIntent

    data class UpdateSelectedTeam(val newTeam: TeamModelDomain): IShowingScreenUpdateIntent

}