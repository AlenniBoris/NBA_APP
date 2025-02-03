package com.alenniboris.nba_app.presentation.screens.details.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.nba_app.domain.manager.INbaApiManager
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.utils.SingleFlowEvent
import com.alenniboris.nba_app.presentation.mappers.toUiMessageString
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TeamDetailsScreenVM(
    private val teamId: Int,
    private val nbaApiManager: INbaApiManager
) : ViewModel() {

    private val _screenState = MutableStateFlow(
        TeamDetailsScreenState(
            team = TeamModelDomain(id = teamId)
        )
    )
    val screenState = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<ITeamDetailsScreenEvent>(viewModelScope)
    val event = _event.flow

    private var _seasonsJob: Job? = null

    init {
        reloadDataForTeamAndLoadLeagues()
        loadSeasons()
        loadTeamStatisticsBySelectedLeagueAndSeason()
    }

    init {
        viewModelScope.launch {
            nbaApiManager.followedTeams
                .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
                .distinctUntilChanged()
                .collect { teams ->
                    val ids = teams.map { it.teamId }
                    _screenState.update {
                        it.copy(
                            team = it.team.copy(isFollowed = ids.contains(it.team.id))
                        )
                    }
                }
        }
    }

    private fun loadSeasons() {
        _seasonsJob?.cancel()
        _seasonsJob = viewModelScope.launch {
            _screenState.update { it.copy(isSeasonsLoading = true) }

            when (val res = nbaApiManager.getAllSeasons()) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update {
                        it.copy(
                            selectedSeason = res.result.firstOrNull(),
                            listOfSeasons = res.result
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        ITeamDetailsScreenEvent.ShowToastMessage(
                            res.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isSeasonsLoading = false) }
        }
    }

    private fun reloadDataForTeamAndLoadLeagues() {
        viewModelScope.launch {
            _screenState.update { it.copy(isTeamDataReloading = true) }

            when (val res =
                nbaApiManager.reloadDataForTeamAndLoadLeagues(team = _screenState.value.team)) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update {
                        it.copy(
                            team = res.result.teamData,
                            listOfLeagues = res.result.leaguesData
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        ITeamDetailsScreenEvent.ShowToastMessage(res.exception.toUiMessageString())
                    )
                }
            }

            _screenState.update { it.copy(isTeamDataReloading = false) }
        }
    }

    fun proceedIntentAction(intent: ITeamDetailsScreenUpdateIntent) = when (intent) {
        is ITeamDetailsScreenUpdateIntent.UpdateSelectedLeague ->
            updateSelectedLeague(newSelectedLeague = intent.league)

        is ITeamDetailsScreenUpdateIntent.UpdateSelectedSeason ->
            updateSelectedSeason(newSelectedSeason = intent.season)

        ITeamDetailsScreenUpdateIntent.NavigateToPreviousScreen -> navigateToPreviousScreen()

        ITeamDetailsScreenUpdateIntent.ProceedIsFollowedAction -> proceedIsFollowedAction()
    }

    private fun navigateToPreviousScreen() {
        _event.emit(ITeamDetailsScreenEvent.NavigateToPreviousPage)
    }

    private fun proceedIsFollowedAction() {
        viewModelScope.launch {
            nbaApiManager.proceedElementIsFollowingUpdate(_screenState.value.team)
        }
    }


    private fun updateSelectedSeason(newSelectedSeason: SeasonModelDomain) {
        val isValueTheSame = _screenState.value.selectedSeason == newSelectedSeason
        val league = _screenState.value.selectedLeague
        _screenState.update { state ->
            state.copy(
                selectedSeason = if (!isValueTheSame) newSelectedSeason else null,
            )
        }
        if (!isValueTheSame && league != null) {
            loadTeamStatisticsBySelectedLeagueAndSeason()
        }
    }

    private fun updateSelectedLeague(newSelectedLeague: LeagueModelDomain) {
        val isValueTheSame = _screenState.value.selectedLeague == newSelectedLeague
        val season = _screenState.value.selectedSeason
        _screenState.update { state ->
            state.copy(
                selectedLeague = if (!isValueTheSame) newSelectedLeague else null,
            )
        }
        if (!isValueTheSame && season != null) {
            loadTeamStatisticsBySelectedLeagueAndSeason()
        }
    }

    private fun loadTeamStatisticsBySelectedLeagueAndSeason() {
        viewModelScope.launch {
            when (
                val res = nbaApiManager.requestForTeamStatistics(
                    team = _screenState.value.team,
                    season = _screenState.value.selectedSeason,
                    league = _screenState.value.selectedLeague
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update { it.copy(teamStatistics = res.result) }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        ITeamDetailsScreenEvent.ShowToastMessage(res.exception.toUiMessageString())
                    )
                }
            }
        }
    }

}