package com.alenniboris.nba_app.presentation.screens.details.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.usecase.game.IGetFollowedGamesUseCase
import com.alenniboris.nba_app.domain.usecase.game.IGetGameDataAndStatisticsByIdUseCase
import com.alenniboris.nba_app.domain.usecase.game.IUpdateGameIsFollowedUseCase
import com.alenniboris.nba_app.domain.utils.SingleFlowEvent
import com.alenniboris.nba_app.presentation.mappers.toUiMessageString
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameDetailsScreenVM(
    private val gameId: Int,
    private val getFollowedGamesUseCase: IGetFollowedGamesUseCase,
    private val getGameDataAndStatisticsByIdUseCase: IGetGameDataAndStatisticsByIdUseCase,
    private val updateGameIsFollowedUseCase: IUpdateGameIsFollowedUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(
        GameDetailsScreenState(
            game = GameModelDomain(id = gameId)
        )
    )
    val screenState = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IGameDetailsScreenEvent>(viewModelScope)
    val event = _event.flow

    init {
        loadGameDataAndStatistics()
    }

    init {
        viewModelScope.launch {
            getFollowedGamesUseCase.followedFlow
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


    private fun loadGameDataAndStatistics() {

        viewModelScope.launch {
            _screenState.update { it.copy(isLoading = true) }

            when (
                val res =
                    getGameDataAndStatisticsByIdUseCase.invoke(gameId = _screenState.value.game.id)
            ) {
                is CustomResultModelDomain.Success -> {
                    val loadedData = res.result
                    _screenState.update {
                        it.copy(
                            game = loadedData.game,
                            gameStatistics = loadedData.statistics,
                            isReloadedWithError = false
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _screenState.update { it.copy(isReloadedWithError = true) }
                    _event.emit(
                        IGameDetailsScreenEvent.ShowToastMessage(res.exception.toUiMessageString())
                    )
                }
            }

            _screenState.update { it.copy(isLoading = false) }
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
            updateGameIsFollowedUseCase.invoke(_screenState.value.game)
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