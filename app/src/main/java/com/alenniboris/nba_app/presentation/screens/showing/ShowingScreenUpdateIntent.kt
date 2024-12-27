package com.alenniboris.nba_app.presentation.screens.showing

import com.alenniboris.nba_app.domain.utils.EnumValues
import com.alenniboris.nba_app.presentation.model.IStateUiModel
import com.alenniboris.nba_app.presentation.model.filter.CountryFilterUiModel
import com.alenniboris.nba_app.presentation.model.filter.LeagueFilterUiModel
import com.alenniboris.nba_app.presentation.model.filter.SeasonFilterUiModel
import java.util.Date

interface ShowingScreenUpdateIntent {

    data class UpdateSelectedDate(val newDate: Date) : ShowingScreenUpdateIntent

    data class UpdateSelectedSeason(val newSeason: SeasonFilterUiModel) : ShowingScreenUpdateIntent

    data class UpdateSelectedCountry(val newCountry: CountryFilterUiModel) :
        ShowingScreenUpdateIntent

    data class UpdateSelectedStatus(val newStatus: EnumValues.GameStatus) :
        ShowingScreenUpdateIntent

    data class UpdateEnteredSearch(val newQuery: String) : ShowingScreenUpdateIntent

    data class UpdateSelectedLeague(val newLeague: LeagueFilterUiModel) : ShowingScreenUpdateIntent

    data class UpdateCurrentStateToAnother(val category: ShowingScreenValues.Category) :
        ShowingScreenUpdateIntent

    data class ProceedPersonalAction(val action: ShowingScreenValues.PersonalBtnAction) :
        ShowingScreenUpdateIntent

    data object ProceedSearchRequestByFilterQuery : ShowingScreenUpdateIntent

    data object ChangeFilterParametersToPreviousValues : ShowingScreenUpdateIntent

    data class ProceedElementActionWithFollowedDatabase(val element: IStateUiModel) :
        ShowingScreenUpdateIntent

    data object UpdateFilterSheetVisibility : ShowingScreenUpdateIntent

    data class UpdateCategoriesListVisibility(val isVisible: Boolean) : ShowingScreenUpdateIntent

    data class UpdatePersonalActionsVisibility(val isVisible: Boolean) : ShowingScreenUpdateIntent

}