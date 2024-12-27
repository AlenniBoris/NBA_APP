package com.alenniboris.nba_app.presentation.screens.showing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.nba_app.domain.manager.IAuthenticationManager
import com.alenniboris.nba_app.domain.utils.EnumValues.GameStatus
import com.alenniboris.nba_app.domain.utils.SingleFlowEvent
import com.alenniboris.nba_app.presentation.model.GameUiModel
import com.alenniboris.nba_app.presentation.model.IStateUiModel
import com.alenniboris.nba_app.presentation.model.PlayerUiModel
import com.alenniboris.nba_app.presentation.model.TeamUiModel
import com.alenniboris.nba_app.presentation.model.filter.CountryFilterUiModel
import com.alenniboris.nba_app.presentation.model.filter.LeagueFilterUiModel
import com.alenniboris.nba_app.presentation.model.filter.SeasonFilterUiModel
import com.alenniboris.nba_app.presentation.screens.showing.ShowingScreenValues.Category
import com.alenniboris.nba_app.presentation.screens.showing.ShowingScreenValues.PersonalBtnAction
import com.alenniboris.nba_app.presentation.screens.showing.state.ShowingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class ShowingScreenVM(
    private val authenticationManager: IAuthenticationManager
) : ViewModel() {

    private val _screenState = MutableStateFlow(ShowingState())
    val screenState = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<ShowingScreenEvent>(viewModelScope)
    val event = _event.flow

    private var _job: Job? = null

    init {
        initialLoadingOfActiveGames()
    }

    private fun initialLoadingOfActiveGames() {
        _event.emit(
            ShowingScreenEvent.ShowToastMessage(
                "Active games loading"
            )
        )
        load()
    }

    fun proceedIntentAction(updateIntent: ShowingScreenUpdateIntent) {
        when (updateIntent) {
            is ShowingScreenUpdateIntent.UpdateSelectedDate -> updateSelectedDate(updateIntent.newDate)
            is ShowingScreenUpdateIntent.UpdateEnteredSearch -> updateEnteredSearch(updateIntent.newQuery)
            is ShowingScreenUpdateIntent.UpdateSelectedSeason -> updateSelectedSeason(updateIntent.newSeason)
            is ShowingScreenUpdateIntent.UpdateSelectedStatus -> updateSelectedStatus(updateIntent.newStatus)
            is ShowingScreenUpdateIntent.UpdateSelectedCountry -> updateSelectedCountry(updateIntent.newCountry)
            is ShowingScreenUpdateIntent.UpdateSelectedLeague -> updateSelectedLeague(updateIntent.newLeague)
            is ShowingScreenUpdateIntent.ProceedSearchRequestByFilterQuery -> proceedFilterQuerySearchRequest()
            is ShowingScreenUpdateIntent.ChangeFilterParametersToPreviousValues -> changeCurrentFilterValuesToPrevious()
            is ShowingScreenUpdateIntent.ProceedPersonalAction -> proceedPersonalAction(updateIntent.action)
            is ShowingScreenUpdateIntent.UpdateCurrentStateToAnother -> updateCurrentStateToAnother(
                updateIntent.category
            )

            is ShowingScreenUpdateIntent.ProceedElementActionWithFollowedDatabase -> proceedElementActionWithFollowedDatabase(
                updateIntent.element
            )

            is ShowingScreenUpdateIntent.UpdateFilterSheetVisibility -> updateSheetVisibility()

            is ShowingScreenUpdateIntent.UpdateCategoriesListVisibility -> updateCategoriesListVisibility(
                updateIntent.isVisible
            )

            is ShowingScreenUpdateIntent.UpdatePersonalActionsVisibility -> updatePersonalActionsVisibility(
                updateIntent.isVisible
            )
        }
    }

    private fun updateCategoriesListVisibility(isVisible: Boolean) {
        _screenState.update { state ->
            state.copy(
                isCategoriesVisible = isVisible
            )
        }
    }

    private fun updatePersonalActionsVisibility(isVisible: Boolean) {
        _screenState.update { state ->
            state.copy(
                isPersonalActionsVisible = isVisible
            )
        }
    }

    private fun updateSheetVisibility() {
        _screenState.update { state ->
            state.copy(isFilterSheetVisible = !state.isFilterSheetVisible)
        }
    }

    private fun proceedElementActionWithFollowedDatabase(element: IStateUiModel) {
        if (!element.isFollowed) {
            addElementToFollowedDatabase(element)
        } else {
            removeElementFromFollowedDatabase(element)
        }
    }

    private fun addElementToFollowedDatabase(element: IStateUiModel) {
        _event.emit(
            ShowingScreenEvent.ShowToastMessage(
                "Adding to database"
            )
        )
        _screenState.update { state ->
            state.copy(
                elements = state.elements.map { currentElement ->
                    if (currentElement == element) {
                        when (currentElement) {
                            is GameUiModel -> currentElement.copy(isFollowed = true)
                            is PlayerUiModel -> currentElement.copy(isFollowed = true)
                            is TeamUiModel -> currentElement.copy(isFollowed = true)
                        }
                    } else currentElement
                }
            )
        }
    }

    private fun removeElementFromFollowedDatabase(element: IStateUiModel) {
        _event.emit(
            ShowingScreenEvent.ShowToastMessage(
                "Deleting from database"
            )
        )
        _screenState.update { state ->
            state.copy(
                elements = state.elements.map { currentElement ->
                    if (currentElement == element) {
                        when (currentElement) {
                            is GameUiModel -> currentElement.copy(isFollowed = false)
                            is PlayerUiModel -> currentElement.copy(isFollowed = false)
                            is TeamUiModel -> currentElement.copy(isFollowed = false)
                        }
                    } else currentElement
                }
            )
        }
    }

    private fun changeCurrentFilterValuesToPrevious() {
        _screenState.update { state ->
            state.copy(
                currentCategory = state.currentCategory,
                mutableFilter = state.filter
            )
        }
    }

    private fun proceedFilterQuerySearchRequest() {
        changePreviousFilterValuesToCurrent()
        load()
        _event.emit(
            ShowingScreenEvent.ShowToastMessage("Loading by query is emitted")
        )
    }

    private fun changePreviousFilterValuesToCurrent() {
        _screenState.update { state ->
            state.copy(
                currentCategory = state.currentCategory,
                filter = state.mutableFilter
            )
        }
    }

    private fun proceedPersonalAction(action: PersonalBtnAction) {
        when (action) {
            PersonalBtnAction.Details -> emitOpenUserDetailScreenEvent()
            PersonalBtnAction.Exit -> signOutCurrentUserFromApplication()
        }
    }

    private fun emitOpenUserDetailScreenEvent() {
        _event.emit(
            ShowingScreenEvent.NavigateToUserDetailsScreen
        )
    }

    private fun signOutCurrentUserFromApplication() {
        viewModelScope.launch {
            authenticationManager.signOut()
        }
    }

    private fun updateCurrentStateToAnother(category: Category) {
        when (category) {
            Category.Games -> updateStateToGamesState()
            Category.Teams -> updateStateToTeamsState()
            Category.Players -> updateStateToPlayersState()
        }
    }

    private fun updateStateToGamesState() {
        _screenState.update { state ->
            state.copy(
                currentCategory = Category.Games,

                )
        }
        load()
    }

    private fun updateStateToTeamsState() {
        _screenState.update { state ->
            state.copy(
                currentCategory = Category.Teams,
                mutableFilter = state.mutableFilter.copy(enteredQuery = "")
            )
        }
        load()
    }

    private fun updateStateToPlayersState() {
        _screenState.update { state ->
            state.copy(
                currentCategory = Category.Players,
                mutableFilter = state.mutableFilter.copy(enteredQuery = "")
            )
        }
        load()
    }

    private fun load() {
        _job?.cancel()
        _job = viewModelScope.launch(Dispatchers.IO) {
            changeIsLoading(true)
            delay(2000)
            changeIsLoading(false)
        }
        _event.emit(
            ShowingScreenEvent.ShowToastMessage(
                "Loading ${_screenState.value.currentCategory.name} items"
            )
        )
    }

    private fun changeIsLoading(isLoading: Boolean) {
        _screenState.update { state ->
            state.copy(
                isLoading = isLoading
            )
        }
    }

    private fun updateSelectedLeague(newSelectedLeague: LeagueFilterUiModel) {
        _screenState.update { state ->
            val isValueTheSame = state.mutableFilter.selectedLeague == newSelectedLeague
            state.copy(
                currentCategory = state.currentCategory,
                mutableFilter = state.mutableFilter.copy(
                    selectedLeague = if (!isValueTheSame) newSelectedLeague else LeagueFilterUiModel()
                )
            )
        }
    }

    private fun updateSelectedSeason(newSelectedSeason: SeasonFilterUiModel) {
        _screenState.update { state ->
            val isValueTheSame = state.mutableFilter.selectedSeason == newSelectedSeason
            state.copy(
                currentCategory = state.currentCategory,
                mutableFilter = state.mutableFilter.copy(
                    selectedSeason = if (!isValueTheSame) newSelectedSeason else SeasonFilterUiModel()
                )
            )
        }
    }

    private fun updateSelectedDate(newSelectedDate: Date) {
        _screenState.update { state ->
            state.copy(
                currentCategory = state.currentCategory,
                mutableFilter = state.mutableFilter.copy(
                    selectedDate = newSelectedDate
                )
            )
        }
    }

    private fun updateSelectedCountry(newSelectedCountry: CountryFilterUiModel) {
        _screenState.update { state ->
            val isValueTheSame = state.mutableFilter.selectedCountry == newSelectedCountry
            state.copy(
                currentCategory = state.currentCategory,
                mutableFilter = state.mutableFilter.copy(
                    selectedCountry = if (!isValueTheSame) newSelectedCountry else CountryFilterUiModel()
                )
            )
        }
    }

    private fun updateSelectedStatus(newSelectedStatus: GameStatus) {
        _screenState.update { state ->
            val isValueTheSame = state.mutableFilter.selectedStatus == newSelectedStatus
            state.copy(
                currentCategory = state.currentCategory,
                mutableFilter = state.mutableFilter.copy(
                    selectedStatus = if (!isValueTheSame) newSelectedStatus else null
                )
            )
        }
    }

    private fun updateEnteredSearch(newEnteredQuery: String) {
        _screenState.update { state ->
            state.copy(
                currentCategory = state.currentCategory,
                mutableFilter = state.mutableFilter.copy(
                    enteredQuery = newEnteredQuery
                )
            )
        }
    }
}