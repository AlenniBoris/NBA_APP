package com.alenniboris.nba_app.presentation.screens.showing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.IStateModel
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.entity.api.nba.GameEntityModelDomain
import com.alenniboris.nba_app.domain.model.entity.api.nba.PlayerEntityModelDomain
import com.alenniboris.nba_app.domain.model.entity.api.nba.TeamEntityModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.model.params.api.nba.GameRequestParamsModelDomain
import com.alenniboris.nba_app.domain.model.params.api.nba.INbaApiElementsRequestType
import com.alenniboris.nba_app.domain.model.params.api.nba.NbaApiGameTypeElementsRequest
import com.alenniboris.nba_app.domain.model.params.api.nba.NbaApiPlayerTypeElementsRequest
import com.alenniboris.nba_app.domain.model.params.api.nba.NbaApiTeamTypeElementsRequest
import com.alenniboris.nba_app.domain.model.params.api.nba.PlayerRequestParamsModelDomain
import com.alenniboris.nba_app.domain.model.params.api.nba.TeamRequestParamsModelDomain
import com.alenniboris.nba_app.domain.usecase.authentication.ISignOutUserUseCase
import com.alenniboris.nba_app.domain.usecase.countries.IGetCountriesUseCase
import com.alenniboris.nba_app.domain.usecase.game.IGetFollowedGamesUseCase
import com.alenniboris.nba_app.domain.usecase.game.IGetGamesByDateUseCase
import com.alenniboris.nba_app.domain.usecase.game.IGetGamesBySeasonAndLeagueUseCase
import com.alenniboris.nba_app.domain.usecase.game.IUpdateGameIsFollowedUseCase
import com.alenniboris.nba_app.domain.usecase.leagues.IGetLeaguesByCountryUseCase
import com.alenniboris.nba_app.domain.usecase.player.IGetFollowedPlayersUseCase
import com.alenniboris.nba_app.domain.usecase.player.IGetPlayersByQuerySeasonTeamUseCase
import com.alenniboris.nba_app.domain.usecase.player.IGetPlayersByQueryUseCase
import com.alenniboris.nba_app.domain.usecase.player.IGetPlayersBySeasonTeamUseCase
import com.alenniboris.nba_app.domain.usecase.player.IUpdatePlayerIsFollowedUseCase
import com.alenniboris.nba_app.domain.usecase.seasons.IGetSeasonsUseCase
import com.alenniboris.nba_app.domain.usecase.team.IGetFollowedTeamsUseCase
import com.alenniboris.nba_app.domain.usecase.team.IGetTeamsByCountryUseCase
import com.alenniboris.nba_app.domain.usecase.team.IGetTeamsByQuerySeasonLeagueCountryUseCase
import com.alenniboris.nba_app.domain.usecase.team.IGetTeamsByQuerySeasonLeagueUseCase
import com.alenniboris.nba_app.domain.usecase.team.IGetTeamsByQueryUseCase
import com.alenniboris.nba_app.domain.usecase.team.IGetTeamsBySeasonAndLeagueUseCase
import com.alenniboris.nba_app.domain.usecase.team.IUpdateTeamIsFollowedUseCase
import com.alenniboris.nba_app.domain.utils.GameStatus
import com.alenniboris.nba_app.domain.utils.NbaApiCategory
import com.alenniboris.nba_app.domain.utils.SingleFlowEvent
import com.alenniboris.nba_app.presentation.mappers.toUiMessageString
import com.alenniboris.nba_app.presentation.screens.showing.ShowingScreenValues.PersonalBtnAction
import com.alenniboris.nba_app.presentation.screens.showing.state.ShowingState
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class ShowingScreenVM(
    private val signOutUserUseCase: ISignOutUserUseCase,
    private val getFollowedGamesUseCase: IGetFollowedGamesUseCase,
    private val getFollowedPlayersUseCase: IGetFollowedPlayersUseCase,
    private val getFollowedTeamsUseCase: IGetFollowedTeamsUseCase,
    private val updateGameIsFollowedUseCase: IUpdateGameIsFollowedUseCase,
    private val updateTeamIsFollowedUseCase: IUpdateTeamIsFollowedUseCase,
    private val updatePlayerIsFollowedUseCase: IUpdatePlayerIsFollowedUseCase,
    private val getSeasonsUseCase: IGetSeasonsUseCase,
    private val getCountriesUseCase: IGetCountriesUseCase,
    private val getGamesByDateUseCase: IGetGamesByDateUseCase,
    private val getGamesBySeasonAndLeague: IGetGamesBySeasonAndLeagueUseCase,
    private val getPlayersByQueryUseCase: IGetPlayersByQueryUseCase,
    private val getPlayersBySeasonAndTeamUseCase: IGetPlayersBySeasonTeamUseCase,
    private val getPlayersBySeasonAndTeamAndQueryUseCase: IGetPlayersByQuerySeasonTeamUseCase,
    private val getTeamsByCountryUseCase: IGetTeamsByCountryUseCase,
    private val getTeamsByQueryAndSeasonAndLeagueAndCountryUseCase: IGetTeamsByQuerySeasonLeagueCountryUseCase,
    private val getTeamsByQueryAndSeasonAndLeagueUseCase: IGetTeamsByQuerySeasonLeagueUseCase,
    private val getTeamsByQueryUseCase: IGetTeamsByQueryUseCase,
    private val getTeamsBySeasonAndLeagueUseCase: IGetTeamsBySeasonAndLeagueUseCase,
    private val getLeaguesByCountryUseCase: IGetLeaguesByCountryUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(ShowingState())
    val screenState = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IShowingScreenEvent>(viewModelScope)
    val event = _event.flow

    private var _jobLoadingElements: Job? = null
    private var _jobLoadingLeagues: Job? = null
    private var _jobLoadingTeams: Job? = null


    init {
        viewModelScope.launch {
            _screenState.map { it.currentCategory }
                .flatMapLatest { category ->
                    when (category) {
                        NbaApiCategory.Games -> getFollowedGamesUseCase.followedFlow
                        NbaApiCategory.Teams -> getFollowedTeamsUseCase.followedFlow
                        NbaApiCategory.Players -> getFollowedPlayersUseCase.followedFlow
                    }
                }
                .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
                .distinctUntilChanged()
                .collect { selected ->
                    val ids = selected.map {
                        when (it) {
                            is GameEntityModelDomain -> it.gameId
                            is PlayerEntityModelDomain -> it.playerId
                            is TeamEntityModelDomain -> it.teamId
                        }
                    }

                    _screenState.update { state ->
                        state.copy(
                            elements = state.elements.map { el ->
                                when (el) {
                                    is GameModelDomain -> el.copy(isFollowed = ids.contains(el.id))
                                    is PlayerModelDomain -> el.copy(isFollowed = ids.contains(el.id))
                                    is TeamModelDomain -> el.copy(isFollowed = ids.contains(el.id))
                                }
                            }
                        )
                    }
                }
        }
    }

    init {
        makeSearchRequest()
        initialLoadingOfSeasons()
        initialLoadingOfCountries()
    }

    private fun initialLoadingOfSeasons() {
        viewModelScope.launch {
            changeIsSeasonsLoading(true)

            when (
                val seasonsResult = getSeasonsUseCase.invoke()
            ) {
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

            when (
                val countriesResult = getCountriesUseCase.invoke()
            ) {
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

            is IShowingScreenUpdateIntent.ProceedNavigationToGameDetailsScreen -> navigateToGameDetailsScreen(
                updateIntent.game
            )

            is IShowingScreenUpdateIntent.ProceedNavigationToTeamDetailsScreen -> navigateToTeamDetailsScreen(
                updateIntent.team
            )

            is IShowingScreenUpdateIntent.ProceedNavigationToPlayerDetailsScreen -> navigateToPlayerDetailsScreen(
                updateIntent.player
            )

            is IShowingScreenUpdateIntent.ProceedNavigationToSettingsScreen -> navigateToSettingsScreen()

        }
    }

    private fun navigateToSettingsScreen() {
        _event.emit(
            IShowingScreenEvent.NavigateToSettingsPage
        )
    }

    private fun navigateToGameDetailsScreen(game: GameModelDomain) {
        _event.emit(
            IShowingScreenEvent.NavigateToGameDetailsPage(game = game)
        )
    }

    private fun navigateToTeamDetailsScreen(team: TeamModelDomain) {
        _event.emit(
            IShowingScreenEvent.NavigateToTeamDetailsPage(team = team)
        )
    }

    private fun navigateToPlayerDetailsScreen(player: PlayerModelDomain) {
        _event.emit(
            IShowingScreenEvent.NavigateToPlayerDetailsPage(player = player)
        )
    }

    private fun searchItemsAfterConfirmingRequestType() {
        proceedFilterQuerySearchRequest()
        updateFiltersSheetVisibility()
        updateRequestTypeChooserVisibility(false)
    }

    private fun updateRequestType(newType: INbaApiElementsRequestType) {

        _screenState.update {
            it.copy(
                requestParams = when (it.requestParams) {
                    is GameRequestParamsModelDomain -> it.requestParams.copy(elementsRequestType = newType)
                    is PlayerRequestParamsModelDomain -> it.requestParams.copy(elementsRequestType = newType)
                    is TeamRequestParamsModelDomain -> it.requestParams.copy(elementsRequestType = newType)
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
        viewModelScope.launch {
            val followingActionResult = when (element) {
                is GameModelDomain ->
                    updateGameIsFollowedUseCase.invoke(game = element)

                is PlayerModelDomain ->
                    updatePlayerIsFollowedUseCase.invoke(player = element)

                is TeamModelDomain ->
                    updateTeamIsFollowedUseCase.invoke(team = element)
            }
            when (followingActionResult) {
                is CustomResultModelDomain.Success ->
                    _event.emit(
                        IShowingScreenEvent.ShowToastMessage(
                            R.string.action_to_followed_successful
                        )
                    )

                is CustomResultModelDomain.Error ->
                    _event.emit(
                        IShowingScreenEvent.ShowToastMessage(followingActionResult.exception.toUiMessageString())
                    )

            }
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

    private fun makeSearchRequest() {
        _jobLoadingElements?.cancel()
        _jobLoadingElements = viewModelScope.launch {

            val params = _screenState.value.requestParams

            changeIsElementsLoading(true)

            val requestResult = when (params.elementsRequestType) {
                NbaApiGameTypeElementsRequest.GAMES_DATE -> {
                    (params as? GameRequestParamsModelDomain)?.let {
                        getGamesByDateUseCase.invoke(it.requestedDate)
                    }
                }

                NbaApiGameTypeElementsRequest.GAMES_SEASON_LEAGUE -> {
                    (params as? GameRequestParamsModelDomain)?.let {
                        getGamesBySeasonAndLeague.invoke(
                            season = params.requestedSeason,
                            league = params.requestedLeague
                        )
                    }
                }

                NbaApiPlayerTypeElementsRequest.PLAYER_SEARCH -> {
                    (params as? PlayerRequestParamsModelDomain)?.let {
                        getPlayersByQueryUseCase.invoke(
                            query = params.requestedQuery
                        )
                    }
                }

                NbaApiPlayerTypeElementsRequest.PLAYER_SEASON_TEAM -> {
                    (params as? PlayerRequestParamsModelDomain)?.let {
                        getPlayersBySeasonAndTeamUseCase.invoke(
                            season = params.requestedSeason,
                            team = params.requestedTeam
                        )
                    }
                }

                NbaApiPlayerTypeElementsRequest.PLAYER_SEASON_TEAM_SEARCH -> {
                    (params as? PlayerRequestParamsModelDomain)?.let {
                        getPlayersBySeasonAndTeamAndQueryUseCase.invoke(
                            season = params.requestedSeason,
                            team = params.requestedTeam,
                            query = params.requestedQuery
                        )
                    }
                }

                NbaApiTeamTypeElementsRequest.TEAMS_SEARCH -> {
                    (params as? TeamRequestParamsModelDomain)?.let {
                        getTeamsByQueryUseCase.invoke(
                            query = params.requestedQuery
                        )
                    }
                }

                NbaApiTeamTypeElementsRequest.TEAMS_COUNTRY -> {
                    (params as? TeamRequestParamsModelDomain)?.let {
                        getTeamsByCountryUseCase.invoke(
                            country = params.requestedCountry
                        )
                    }
                }

                NbaApiTeamTypeElementsRequest.TEAMS_SEASON_LEAGUE -> {
                    (params as? TeamRequestParamsModelDomain)?.let {
                        getTeamsBySeasonAndLeagueUseCase.invoke(
                            season = params.requestedSeason,
                            league = params.requestedLeague
                        )
                    }
                }

                NbaApiTeamTypeElementsRequest.TEAMS_SEARCH_SEASON_LEAGUE -> {
                    (params as? TeamRequestParamsModelDomain)?.let {
                        getTeamsByQueryAndSeasonAndLeagueUseCase.invoke(
                            query = params.requestedQuery,
                            season = params.requestedSeason,
                            league = params.requestedLeague
                        )
                    }
                }

                NbaApiTeamTypeElementsRequest.TEAMS_SEARCH_SEASON_LEAGUE_COUNTRY -> {
                    (params as? TeamRequestParamsModelDomain)?.let {
                        getTeamsByQueryAndSeasonAndLeagueAndCountryUseCase.invoke(
                            query = params.requestedQuery,
                            season = params.requestedSeason,
                            league = params.requestedLeague,
                            country = params.requestedCountry
                        )
                    }
                }
            }

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
        _screenState.update { it.copy(isPersonalActionsVisible = false) }
        when (action) {
            PersonalBtnAction.Details -> emitOpenUserDetailScreenEvent()
            PersonalBtnAction.Exit -> signOutCurrentUserFromApplication()
            PersonalBtnAction.Settings -> navigateToSettingsScreen()
        }
    }

    private fun emitOpenUserDetailScreenEvent() {
        _event.emit(
            IShowingScreenEvent.NavigateToUserDetailsScreen
        )
    }

    private fun signOutCurrentUserFromApplication() {
        viewModelScope.launch {
            signOutUserUseCase.invoke()
        }
    }

    private fun updateCurrentStateToAnother(category: NbaApiCategory) {
        _screenState.update { state ->
            state.copy(
                currentCategory = category,
                requestParams = when (category) {
                    NbaApiCategory.Games -> {
                        GameRequestParamsModelDomain(
                            requestedDate = _screenState.value.filter.selectedDate,
                            requestedSeason = _screenState.value.filter.selectedSeason,
                            requestedLeague = _screenState.value.filter.selectedLeague
                        )
                    }

                    NbaApiCategory.Teams -> {
                        TeamRequestParamsModelDomain(
                            requestedQuery = _screenState.value.filter.enteredQuery,
                            requestedSeason = _screenState.value.filter.selectedSeason,
                            requestedCountry = _screenState.value.filter.selectedCountry,
                            requestedLeague = _screenState.value.filter.selectedLeague
                        )
                    }

                    NbaApiCategory.Players -> {
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

            when (
                val leaguesResult = getLeaguesByCountryUseCase.invoke(selectedCountry)
            ) {
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

            when (
                val teamsResult = getTeamsBySeasonAndLeagueUseCase.invoke(
                    season = season,
                    league = league
                )
            ) {
                is CustomResultModelDomain.Success -> {

                    val newList = teamsResult.result.mapNotNull { it as? TeamModelDomain }

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

            changeIsTeamsLoading(false)
        }
    }
}