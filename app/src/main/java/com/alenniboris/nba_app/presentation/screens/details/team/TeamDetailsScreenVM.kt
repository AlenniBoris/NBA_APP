package com.alenniboris.nba_app.presentation.screens.details.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.usecase.player.IGetPlayersForTeamInSeasonUseCase
import com.alenniboris.nba_app.domain.usecase.seasons.IGetSeasonsUseCase
import com.alenniboris.nba_app.domain.usecase.team.IGetFollowedTeamsUseCase
import com.alenniboris.nba_app.domain.usecase.team.IGetTeamStatisticsUseCase
import com.alenniboris.nba_app.domain.usecase.team.IReloadDataForTeamAndLoadLeaguesUseCase
import com.alenniboris.nba_app.domain.usecase.team.IUpdateTeamIsFollowedUseCase
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
    private val getFollowedTeamsUseCase: IGetFollowedTeamsUseCase,
    private val getSeasonsUseCase: IGetSeasonsUseCase,
    private val getPlayersForTeamInSeasonUseCase: IGetPlayersForTeamInSeasonUseCase,
    private val reloadDataForTeamAndLoadLeaguesUseCase: IReloadDataForTeamAndLoadLeaguesUseCase,
    private val updateTeamIsFollowedUseCase: IUpdateTeamIsFollowedUseCase,
    private val getTeamStatisticsUseCase: IGetTeamStatisticsUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(
        TeamDetailsScreenState(
            team = TeamModelDomain(id = teamId)
        )
    )
    val screenState = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<ITeamDetailsScreenEvent>(viewModelScope)
    val event = _event.flow

    private var _statisticsJob: Job? = null
    private var _teamPlayersJob: Job? = null

    init {
        reloadDataForTeamAndLoadLeagues()
    }

    init {
        viewModelScope.launch {
            getFollowedTeamsUseCase.followedFlow
                .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
                .distinctUntilChanged()
                .collect { games ->
                    val ids = games.map { it.teamId }.toList()
                    _screenState.update { state ->
                        state.copy(
                            team = state.team.copy(
                                isFollowed = ids.contains(state.team.id)
                            )
                        )
                    }
                }
        }
    }

    private fun loadSeasons() {
        viewModelScope.launch {
            _screenState.update { it.copy(isSeasonsLoading = true) }

            when (
                val res = getSeasonsUseCase.invoke()
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update {
                        it.copy(
                            selectedSeason = res.result.firstOrNull(),
                            listOfSeasons = res.result
                        )
                    }
                    loadTeamPlayersInSeason()
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

    private fun loadTeamPlayersInSeason() {
        _teamPlayersJob?.cancel()
        _teamPlayersJob = viewModelScope.launch {
            _screenState.update { it.copy(isTeamPlayersLoading = true) }

            _screenState.value.selectedSeason?.let { season ->
                when (
                    val res = getPlayersForTeamInSeasonUseCase.invoke(
                        team = _screenState.value.team,
                        season = season
                    )
                ) {
                    is CustomResultModelDomain.Success -> {
                        _screenState.update { it.copy(teamPlayers = res.result) }
                    }

                    is CustomResultModelDomain.Error -> {
                        _event.emit(
                            ITeamDetailsScreenEvent.ShowToastMessage(res.exception.toUiMessageString())
                        )
                    }
                }
            }

            _screenState.update { it.copy(isTeamPlayersLoading = false) }
        }
    }

    private fun reloadDataForTeamAndLoadLeagues() {
        viewModelScope.launch {
            _screenState.update { it.copy(isTeamDataReloading = true) }

            when (
                val res =
                    reloadDataForTeamAndLoadLeaguesUseCase.invoke(
                        team = _screenState.value.team
                    )
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update {
                        it.copy(
                            team = res.result.teamData,
                            listOfLeagues = res.result.leaguesData,
                            isTeamDataReloadedWithError = false
                        )
                    }
                    loadSeasons()
                }

                is CustomResultModelDomain.Error -> {
                    _screenState.update { it.copy(isTeamDataReloadedWithError = true) }
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

        is ITeamDetailsScreenUpdateIntent.NavigateToPlayerDetailsScreen ->
            navigateToPlayerDetailsScreen(
                intent.player
            )
    }

    private fun navigateToPlayerDetailsScreen(player: PlayerModelDomain) {
        _event.emit(ITeamDetailsScreenEvent.NavigateToPlayerDetailsScreen(player))
    }

    private fun navigateToPreviousScreen() {
        _event.emit(ITeamDetailsScreenEvent.NavigateToPreviousPage)
    }

    private fun proceedIsFollowedAction() {
        viewModelScope.launch {
            updateTeamIsFollowedUseCase.invoke(_screenState.value.team)
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
        if (isValueTheSame) {
            _teamPlayersJob?.cancel()
            _screenState.update {
                it.copy(
                    teamStatistics = null,
                    teamPlayers = emptyList()
                )
            }
        } else {
            loadTeamPlayersInSeason()
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
        if (isValueTheSame) {
            _statisticsJob?.cancel()
            _screenState.update {
                it.copy(
                    teamStatistics = null,
                )
            }
        }
    }

    private fun loadTeamStatisticsBySelectedLeagueAndSeason() {
        _statisticsJob?.cancel()
        _statisticsJob = viewModelScope.launch {
            _screenState.update { it.copy(isStatisticsDataLoading = true) }
            when (
                val res = getTeamStatisticsUseCase.invoke(
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
            _screenState.update { it.copy(isStatisticsDataLoading = false) }
        }
    }

}