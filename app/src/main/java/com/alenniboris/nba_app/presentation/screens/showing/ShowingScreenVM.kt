package com.alenniboris.nba_app.presentation.screens.showing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.nba_app.domain.manager.IAuthenticationManager
import com.alenniboris.nba_app.domain.manager.INbaApiManager
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.GameModelDomain
import com.alenniboris.nba_app.domain.model.IStateModel
import com.alenniboris.nba_app.domain.model.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.TeamModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.model.params.api.nba.GameRequestParamsModelDomain
import com.alenniboris.nba_app.domain.model.params.api.nba.INbaApiRequestType
import com.alenniboris.nba_app.domain.model.params.api.nba.NbaApiTeamRequestType
import com.alenniboris.nba_app.domain.model.params.api.nba.PlayerRequestParamsModelDomain
import com.alenniboris.nba_app.domain.model.params.api.nba.TeamRequestParamsModelDomain
import com.alenniboris.nba_app.domain.utils.GameStatus
import com.alenniboris.nba_app.domain.utils.SingleFlowEvent
import com.alenniboris.nba_app.presentation.mappers.toUiMessageString
import com.alenniboris.nba_app.presentation.screens.showing.ShowingScreenValues.Category
import com.alenniboris.nba_app.presentation.screens.showing.ShowingScreenValues.PersonalBtnAction
import com.alenniboris.nba_app.presentation.screens.showing.state.ShowingState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class ShowingScreenVM(
    private val authenticationManager: IAuthenticationManager,
    private val nbaApiManager: INbaApiManager
) : ViewModel() {

    private val _screenState = MutableStateFlow(ShowingState())
    val screenState = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IShowingScreenEvent>(viewModelScope)
    val event = _event.flow

    private var _jobLoadingElements: Job? = null
    private var _jobLoadingLeagues: Job? = null
    private var _jobLoadingTeams: Job? = null

    init {
        makeSearchRequest()
        initialLoadingOfSeasons()
        initialLoadingOfCountries()
    }

    private fun initialLoadingOfSeasons() {
        viewModelScope.launch {
            changeIsSeasonsLoading(true)

            when (val seasonsResult = nbaApiManager.getAllSeasons()) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update { state ->
                        state.copy(
                            filter = state.filter.copy(
                                listOfSeasons = seasonsResult.result
                            ),
                            mutableFilter = state.mutableFilter.copy(
                                listOfSeasons = seasonsResult.result
                            )
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    (seasonsResult.exception as? NbaApiExceptionModelDomain)?.let {
                        _event.emit(IShowingScreenEvent.ShowToastMessage(it.toUiMessageString()))
                    }
                }
            }

            changeIsSeasonsLoading(false)
        }
    }

    private fun initialLoadingOfCountries() {
        viewModelScope.launch {
            changeIsCountriesLoading(true)

            when (val countriesResult = nbaApiManager.getAllCountries()) {
                is CustomResultModelDomain.Success -> {
                    val newCountries = countriesResult.result
                        .sortedBy { it.name }
                    _screenState.update { state ->
                        state.copy(
                            filter = state.filter.copy(
                                listOfCountries = newCountries
                            ),
                            mutableFilter = state.mutableFilter.copy(
                                listOfCountries = newCountries
                            )
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    (countriesResult.exception as? NbaApiExceptionModelDomain)?.let {
                        _event.emit(IShowingScreenEvent.ShowToastMessage(it.toUiMessageString()))
                    }
                }
            }

            changeIsCountriesLoading(false)
        }
    }

    fun proceedIntentAction(updateIntent: IShowingScreenUpdateIntent) {
        when (updateIntent) {
            is IShowingScreenUpdateIntent.UpdateSelectedDate -> updateSelectedDate(updateIntent.newDate)
            is IShowingScreenUpdateIntent.UpdateEnteredSearch -> updateEnteredSearch(updateIntent.newQuery)
            is IShowingScreenUpdateIntent.UpdateSelectedSeason -> updateSelectedSeason(updateIntent.newSeason)
            is IShowingScreenUpdateIntent.UpdateSelectedStatus -> updateSelectedStatus(updateIntent.newStatus)
            is IShowingScreenUpdateIntent.UpdateSelectedTeam -> updateSelectedTeam(updateIntent.newTeam)
            is IShowingScreenUpdateIntent.UpdateSelectedCountry -> updateSelectedCountry(
                updateIntent.newCountry
            )

            is IShowingScreenUpdateIntent.UpdateSelectedLeague -> updateSelectedLeague(updateIntent.newLeague)
            is IShowingScreenUpdateIntent.ProceedSearchRequestByFilterQuery -> proceedFilterQuerySearchRequest()
            is IShowingScreenUpdateIntent.ChangeFilterParametersToPreviousValues -> changeCurrentFilterValuesToPrevious()
            is IShowingScreenUpdateIntent.ProceedPersonalAction -> proceedPersonalAction(
                updateIntent.action
            )

            is IShowingScreenUpdateIntent.UpdateCurrentStateToAnother -> updateCurrentStateToAnother(
                updateIntent.category
            )

            is IShowingScreenUpdateIntent.ProceedElementActionWithFollowedDatabase -> proceedElementActionWithFollowedDatabase(
                updateIntent.element
            )

            is IShowingScreenUpdateIntent.UpdateFilterSheetVisibility -> updateFiltersSheetVisibility()

            is IShowingScreenUpdateIntent.UpdateCategoriesListVisibility -> updateCategoriesListVisibility(
                updateIntent.isVisible
            )

            is IShowingScreenUpdateIntent.UpdatePersonalActionsVisibility -> updatePersonalActionsVisibility(
                updateIntent.isVisible
            )

            is IShowingScreenUpdateIntent.UpdateRequestTypeChooserVisibility -> updateRequestTypeChooserVisibility(
                updateIntent.isVisible
            )

            is IShowingScreenUpdateIntent.UpdateRequestType -> updateRequestType(
                updateIntent.newType
            )

            is IShowingScreenUpdateIntent.SearchForItemsAfterRequestType -> searchItemsAfterConfirmingRequestType()

        }
    }

    private fun searchItemsAfterConfirmingRequestType() {
        proceedFilterQuerySearchRequest()
        updateFiltersSheetVisibility()
        updateRequestTypeChooserVisibility(false)
    }

    private fun updateRequestType(newType: INbaApiRequestType) {

        _screenState.update {
            it.copy(
                requestParams = when (it.requestParams) {
                    is GameRequestParamsModelDomain -> it.requestParams.copy(requestType = newType)
                    is PlayerRequestParamsModelDomain -> it.requestParams.copy(requestType = newType)
                    is TeamRequestParamsModelDomain -> it.requestParams.copy(requestType = newType)
                }
            )
        }

    }


    private fun updateRequestTypeChooserVisibility(isVisible: Boolean) {
        _screenState.update { state ->
            state.copy(
                isRequestTypeChooserVisible = isVisible
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

    private fun updateFiltersSheetVisibility() {
        _screenState.update { state ->
            state.copy(isFilterSheetVisible = !state.isFilterSheetVisible)
        }
    }

    private fun proceedElementActionWithFollowedDatabase(element: IStateModel) {
        if (!element.isFollowed) {
            addElementToFollowedDatabase(element)
        } else {
            removeElementFromFollowedDatabase(element)
        }
    }

    private fun addElementToFollowedDatabase(element: IStateModel) {
        _screenState.update { state ->
            state.copy(
                elements = state.elements.map { currentElement ->
                    if (currentElement == element) {
                        when (currentElement) {
                            is GameModelDomain -> currentElement.copy(isFollowed = true)
                            is PlayerModelDomain -> currentElement.copy(isFollowed = true)
                            is TeamModelDomain -> currentElement.copy(isFollowed = true)
                        }
                    } else currentElement
                }
            )
        }
    }

    private fun removeElementFromFollowedDatabase(element: IStateModel) {
        _screenState.update { state ->
            state.copy(
                elements = state.elements.map { currentElement ->
                    if (currentElement == element) {
                        when (currentElement) {
                            is GameModelDomain -> currentElement.copy(isFollowed = false)
                            is PlayerModelDomain -> currentElement.copy(isFollowed = false)
                            is TeamModelDomain -> currentElement.copy(isFollowed = false)
                        }
                    } else currentElement
                }
            )
        }
    }

    private fun changeCurrentFilterValuesToPrevious() {
        _screenState.update { state ->
            state.copy(
                mutableFilter = state.filter
            )
        }
    }

    private fun proceedFilterQuerySearchRequest() {
        changePreviousFilterValuesToCurrent()
        updateParamsValues()
        makeSearchRequest()
    }

    private fun updateParamsValues() {

        _screenState.update {
            it.copy(
                requestParams = when (it.requestParams) {
                    is GameRequestParamsModelDomain ->
                        it.requestParams.copy(
                            requestedSeason = it.filter.selectedSeason,
                            requestedLeague = it.filter.selectedLeague,
                            requestedDate = it.filter.selectedDate
                        )

                    is PlayerRequestParamsModelDomain ->
                        it.requestParams.copy(
                            requestedSeason = it.filter.selectedSeason,
                            requestedLeague = it.filter.selectedLeague,
                            requestedQuery = it.filter.enteredQuery,
                            requestedTeam = it.filter.selectedTeam
                        )

                    is TeamRequestParamsModelDomain ->
                        it.requestParams.copy(
                            requestedSeason = it.filter.selectedSeason,
                            requestedLeague = it.filter.selectedLeague,
                            requestedCountry = it.filter.selectedCountry,
                            requestedQuery = it.filter.enteredQuery
                        )
                }
            )
        }

    }

    private fun changePreviousFilterValuesToCurrent() {
        _screenState.update { state ->
            state.copy(
                filter = state.mutableFilter
            )
        }
    }

    private fun makeSearchRequest(
    ) {
        _jobLoadingElements?.cancel()
        _jobLoadingElements = viewModelScope.launch {

            val params = _screenState.value.requestParams

            changeIsElementsLoading(true)

            val requestResult = nbaApiManager.makeRequestForListOfElements(
                requestParameters = params
            )

            requestResult?.let {

                when (requestResult) {
                    is CustomResultModelDomain.Success -> {

                        val newElements = requestResult.result
                            .filter {
                                when (it) {
                                    is GameModelDomain -> it.gameStatus == _screenState.value.filter.selectedStatus
                                    is PlayerModelDomain -> true
                                    is TeamModelDomain -> true
                                }
                            }

                        _screenState.update {
                            it.copy(
                                elements = when (params) {
                                    is GameRequestParamsModelDomain -> {
                                        if (_screenState.value.filter.selectedStatus != null) newElements
                                        else requestResult.result
                                    }

                                    is PlayerRequestParamsModelDomain -> newElements
                                    is TeamRequestParamsModelDomain -> newElements
                                }
                            )
                        }

                    }

                    is CustomResultModelDomain.Error -> {
                        _event.emit(
                            IShowingScreenEvent.ShowToastMessage(
                                requestResult.exception.toUiMessageString()
                            )
                        )
                    }
                }

            }

            changeIsElementsLoading(false)
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
            IShowingScreenEvent.NavigateToUserDetailsScreen
        )
    }

    private fun signOutCurrentUserFromApplication() {
        viewModelScope.launch {
            authenticationManager.signOut()
        }
    }

    private fun updateCurrentStateToAnother(category: Category) {
        _screenState.update { state ->
            state.copy(
                currentCategory = category,
                requestParams = when (category) {
                    Category.Games -> {
                        GameRequestParamsModelDomain(
                            requestedDate = _screenState.value.filter.selectedDate,
                            requestedSeason = _screenState.value.filter.selectedSeason,
                            requestedLeague = _screenState.value.filter.selectedLeague
                        )
                    }

                    Category.Teams -> {
                        TeamRequestParamsModelDomain(
                            requestedQuery = _screenState.value.filter.enteredQuery,
                            requestedSeason = _screenState.value.filter.selectedSeason,
                            requestedCountry = _screenState.value.filter.selectedCountry,
                            requestedLeague = _screenState.value.filter.selectedLeague
                        )
                    }

                    Category.Players -> {
                        PlayerRequestParamsModelDomain(
                            requestedQuery = _screenState.value.filter.enteredQuery,
                            requestedSeason = _screenState.value.filter.selectedSeason,
                            requestedLeague = _screenState.value.filter.selectedLeague,
                            requestedTeam = _screenState.value.filter.selectedTeam
                        )
                    }
                }
            )
        }

        makeSearchRequest()
    }

    private fun changeIsElementsLoading(isLoading: Boolean) {
        _screenState.update { state ->
            state.copy(
                isLoading = isLoading
            )
        }
    }

    private fun changeIsCountriesLoading(isLoading: Boolean) {
        _screenState.update { state ->
            state.copy(
                mutableFilter = state.mutableFilter.copy(
                    isCountriesLoading = isLoading
                )
            )
        }
    }

    private fun changeIsSeasonsLoading(isLoading: Boolean) {
        _screenState.update { state ->
            state.copy(
                mutableFilter = state.mutableFilter.copy(
                    isSeasonsLoading = isLoading
                )
            )
        }
    }

    private fun changeIsLeaguesLoading(isLoading: Boolean) {
        _screenState.update { state ->
            state.copy(
                mutableFilter = state.mutableFilter.copy(
                    isLeaguesLoading = isLoading
                )
            )
        }
    }

    private fun changeIsTeamsLoading(isLoading: Boolean) {
        _screenState.update { state ->
            state.copy(
                mutableFilter = state.mutableFilter.copy(
                    isTeamsLoading = isLoading
                )
            )
        }
    }

    private fun updateSelectedLeague(newSelectedLeague: LeagueModelDomain) {
        _screenState.update { state ->
            val isValueTheSame = state.mutableFilter.selectedLeague == newSelectedLeague

            val season = _screenState.value.mutableFilter.selectedSeason
            if (!isValueTheSame && season != null) {
                loadTeamsBySelectedLeagueAndSeason(
                    season = season,
                    league = newSelectedLeague
                )
            }

            state.copy(
                mutableFilter = state.mutableFilter.copy(
                    selectedLeague = if (!isValueTheSame) newSelectedLeague else null,
                    listOfTeams = if (isValueTheSame) emptyList() else state.mutableFilter.listOfTeams,
                )
            )
        }


    }

    private fun updateSelectedSeason(newSelectedSeason: SeasonModelDomain) {
        _screenState.update { state ->
            val isValueTheSame = state.mutableFilter.selectedSeason == newSelectedSeason

            val league = _screenState.value.mutableFilter.selectedLeague
            if (!isValueTheSame && league != null) {
                loadTeamsBySelectedLeagueAndSeason(
                    season = newSelectedSeason,
                    league = league
                )
            }

            state.copy(
                mutableFilter = state.mutableFilter.copy(
                    selectedSeason = if (!isValueTheSame) newSelectedSeason else null,
                    listOfTeams = if (isValueTheSame) emptyList() else state.mutableFilter.listOfTeams
                )
            )
        }
    }

    private fun updateSelectedDate(newSelectedDate: Date) {
        _screenState.update { state ->
            state.copy(
                mutableFilter = state.mutableFilter.copy(
                    selectedDate = newSelectedDate
                )
            )
        }
    }

    private fun updateSelectedCountry(newSelectedCountry: CountryModelDomain) {
        _screenState.update { state ->
            val isValueTheSame = state.mutableFilter.selectedCountry == newSelectedCountry

            if (!isValueTheSame) {
                loadLeaguesBySelectedCountry(selectedCountry = newSelectedCountry)
            }

            state.copy(
                mutableFilter = state.mutableFilter.copy(
                    selectedCountry = if (!isValueTheSame) newSelectedCountry else null,
                    listOfLeagues = if (isValueTheSame) emptyList() else state.mutableFilter.listOfLeagues
                )
            )
        }
    }

    private fun updateSelectedStatus(newSelectedStatus: GameStatus) {
        _screenState.update { state ->
            val isValueTheSame = state.mutableFilter.selectedStatus == newSelectedStatus
            state.copy(
                mutableFilter = state.mutableFilter.copy(
                    selectedStatus = if (!isValueTheSame) newSelectedStatus else null
                )
            )
        }
    }

    private fun updateSelectedTeam(newSelectedTeam: TeamModelDomain) {
        _screenState.update { state ->
            val isValueTheSame = state.mutableFilter.selectedTeam == newSelectedTeam
            state.copy(
                mutableFilter = state.mutableFilter.copy(
                    selectedTeam = if (!isValueTheSame) newSelectedTeam else null
                )
            )
        }
    }

    private fun updateEnteredSearch(newEnteredQuery: String) {
        _screenState.update { state ->
            state.copy(
                mutableFilter = state.mutableFilter.copy(
                    enteredQuery = newEnteredQuery
                )
            )
        }
    }

    private fun loadLeaguesBySelectedCountry(selectedCountry: CountryModelDomain) {
        _jobLoadingLeagues?.cancel()
        _jobLoadingLeagues = viewModelScope.launch {
            changeIsLeaguesLoading(true)

            when (val leaguesResult = nbaApiManager.getLeaguesByCountry(selectedCountry)) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update { state ->
                        state.copy(
                            mutableFilter = state.mutableFilter.copy(
                                listOfLeagues = leaguesResult.result,
                            )
                        )
                    }
                    updateSelectedLeague(
                        newSelectedLeague = leaguesResult.result.first()
                    )

                }

                is CustomResultModelDomain.Error -> {
                    (leaguesResult.exception as? NbaApiExceptionModelDomain)?.let {
                        _event.emit(IShowingScreenEvent.ShowToastMessage(it.toUiMessageString()))
                    }
                }
            }

            changeIsLeaguesLoading(false)
        }
    }

    private fun loadTeamsBySelectedLeagueAndSeason(
        season: SeasonModelDomain,
        league: LeagueModelDomain
    ) {
        _jobLoadingTeams?.cancel()
        _jobLoadingTeams = viewModelScope.launch {
            changeIsTeamsLoading(true)

            val requestParams = TeamRequestParamsModelDomain(
                requestType = NbaApiTeamRequestType.TEAMS_SEASON_LEAGUE,
                requestedLeague = league,
                requestedSeason = season
            )

            val teamsResult = nbaApiManager.makeRequestForListOfElements(
                requestParameters = requestParams
            )

            teamsResult?.let {

                when (teamsResult) {
                    is CustomResultModelDomain.Success -> {

                        val newList = teamsResult.result.map { it as TeamModelDomain }

                        _screenState.update { state ->
                            state.copy(
                                mutableFilter = state.mutableFilter.copy(
                                    listOfTeams = newList,
                                    selectedTeam = newList.firstOrNull()
                                )
                            )
                        }

                    }

                    is CustomResultModelDomain.Error -> {
                        _event.emit(IShowingScreenEvent.ShowToastMessage(teamsResult.exception.toUiMessageString()))
                    }
                }

            }

            changeIsTeamsLoading(false)
        }
    }
}