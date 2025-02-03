package com.alenniboris.nba_app.presentation.screens.details.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.nba_app.domain.manager.INbaApiManager
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
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

class GameDetailsScreenVM(
    private val nbaApiManager: INbaApiManager,
    private val gameId: Int
) : ViewModel() {

    private val _screenState = MutableStateFlow(
        GameDetailsScreenState(
            game = GameModelDomain(id = gameId)
        )
    )
    val screenState = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IGameDetailsScreenEvent>(viewModelScope)
    val event = _event.flow

    val a: Job? = null

    init {
        reloadDataForGame()
        loadGameStatistics()
    }

    init {
        viewModelScope.launch {
            nbaApiManager.followedGames
                .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
                .distinctUntilChanged()
                .collect { games ->
                    val ids = games.map { it.gameId }.toList()

                    _screenState.update { state ->
                        state.copy(
                            game = state.game.copy(
                                isFollowed = ids.contains(state.game.id)
                            )
                        )
                    }
                }

        }
    }

    private fun reloadDataForGame() {
        viewModelScope.launch {
            _screenState.update { it.copy(isGameDataReloading = true) }

            when (val gameRes =
                nbaApiManager.getGameDataById(id = _screenState.value.game.id)) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update { state ->
                        state.copy(game = gameRes.result)
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IGameDetailsScreenEvent.ShowToastMessage(
                            gameRes.exception.toUiMessageString()
                        )
                    )
                }
            }

            _screenState.update { it.copy(isGameDataReloading = false) }
        }
    }

    fun proceedUpdateIntent(intent: IGameDetailsScreenUpdateIntent) = when (intent) {
        IGameDetailsScreenUpdateIntent.ProceedIsFollowedAction -> proceedIsFollowedAction()

        is IGameDetailsScreenUpdateIntent.ProceedChangeViewedTeamAction -> proceedChangeViewedTeamAction(
            intent.newTeam
        )

        is IGameDetailsScreenUpdateIntent.NavigateToPreviousScreen -> navigateToPreviousScreen()

        is IGameDetailsScreenUpdateIntent.NavigateToPlayerDetailsScreen ->
            navigateToPlayerDetailsScreen(intent.playerId)

        is IGameDetailsScreenUpdateIntent.NavigateToTeamDetailsScreen ->
            navigateToTeamDetailsScreen(intent.teamId)
    }

    private fun navigateToPlayerDetailsScreen(playerId: Int) {
        _event.emit(
            IGameDetailsScreenEvent.NavigateToPlayerDetailsScreen(
                PlayerModelDomain(id = playerId)
            )
        )
    }

    private fun navigateToTeamDetailsScreen(teamId: Int) {
        _event.emit(
            IGameDetailsScreenEvent.NavigateToTeamDetailsScreen(
                TeamModelDomain(id = teamId)
            )
        )
    }

    private fun proceedIsFollowedAction() {
        viewModelScope.launch {
            nbaApiManager.proceedElementIsFollowingUpdate(_screenState.value.game)
        }
    }

    private fun loadGameStatistics() {
        viewModelScope.launch {
            _screenState.update { it.copy(isGameStatisticsLoading = true) }

            when (val statsRes =
                nbaApiManager.requestForGameStatistics(game = _screenState.value.game)) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update {
                        it.copy(
                            gameStatistics = statsRes.result
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(IGameDetailsScreenEvent.ShowToastMessage(statsRes.exception.toUiMessageString()))
                }
            }

            _screenState.update { it.copy(isGameStatisticsLoading = false) }
        }
    }

    private fun navigateToPreviousScreen() {
        _event.emit(IGameDetailsScreenEvent.NavigateToPreviousPage)
    }

    private fun proceedChangeViewedTeamAction(newTeam: GameTeamType) {
        _screenState.update {
            it.copy(currentlyViewedTeam = newTeam)
        }

    }


}