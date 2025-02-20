package com.alenniboris.nba_app.presentation.screens.followed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.IStateModel
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.entity.api.nba.toGameModelDomain
import com.alenniboris.nba_app.domain.model.entity.api.nba.toPlayerModelDomain
import com.alenniboris.nba_app.domain.model.entity.api.nba.toTeamModelDomain
import com.alenniboris.nba_app.domain.usecase.game.IGetFollowedGamesUseCase
import com.alenniboris.nba_app.domain.usecase.game.IUpdateGameIsFollowedUseCase
import com.alenniboris.nba_app.domain.usecase.player.IGetFollowedPlayersUseCase
import com.alenniboris.nba_app.domain.usecase.player.IUpdatePlayerIsFollowedUseCase
import com.alenniboris.nba_app.domain.usecase.team.IGetFollowedTeamsUseCase
import com.alenniboris.nba_app.domain.usecase.team.IUpdateTeamIsFollowedUseCase
import com.alenniboris.nba_app.domain.utils.SingleFlowEvent
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FollowedScreenVM(
    private val getFollowedGamesUseCase: IGetFollowedGamesUseCase,
    private val getFollowedPlayersUseCase: IGetFollowedPlayersUseCase,
    private val getFollowedTeamsUseCase: IGetFollowedTeamsUseCase,
    private val updateGameIsFollowedUseCase: IUpdateGameIsFollowedUseCase,
    private val updateTeamIsFollowedUseCase: IUpdateTeamIsFollowedUseCase,
    private val updatePlayerIsFollowedUseCase: IUpdatePlayerIsFollowedUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(FollowedState())
    val screenState = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IFollowedScreenEvent>(viewModelScope)
    val event = _event.flow

    init {
        viewModelScope.launch {
            getFollowedGamesUseCase.followedFlow
                .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
                .distinctUntilChanged()
                .collect { elements ->
                    _screenState.update {
                        it.copy(
                            followedGames = elements.map { it.toGameModelDomain() }
                        )
                    }
                }
        }
    }

    init {
        viewModelScope.launch {
            getFollowedTeamsUseCase.followedFlow
                .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
                .distinctUntilChanged()
                .collect { elements ->
                    _screenState.update {
                        it.copy(
                            followedTeams = elements.map { it.toTeamModelDomain() }
                        )
                    }
                }
        }
    }

    init {
        viewModelScope.launch {
            getFollowedPlayersUseCase.followedFlow
                .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
                .distinctUntilChanged()
                .collect { elements ->
                    _screenState.update {
                        it.copy(
                            followedPlayers = elements.map { it.toPlayerModelDomain() }
                        )
                    }
                }
        }
    }

    fun proceedUpdateIntent(intent: IFollowedScreenUpdateIntent) {
        when (intent) {
            is IFollowedScreenUpdateIntent.ProceedRemovingFromFollowedAction -> deleteElementFromDatabase(
                intent.element
            )

            is IFollowedScreenUpdateIntent.ProceedNavigationToGameDetailsScreen -> navigateToGameDetailsScreen(
                intent.game
            )

            is IFollowedScreenUpdateIntent.ProceedNavigationToTeamDetailsScreen -> navigateToTeamDetailsScreen(
                intent.team
            )

            is IFollowedScreenUpdateIntent.ProceedNavigationToPlayerDetailsScreen -> navigateToPlayerDetailsScreen(
                intent.player
            )

            is IFollowedScreenUpdateIntent.NavigateToPreviousScreen -> navigateToPreviousScreen()
        }
    }

    private fun navigateToGameDetailsScreen(game: GameModelDomain) {
        _event.emit(
            IFollowedScreenEvent.NavigateToGameDetailsPage(game = game)
        )
    }

    private fun navigateToTeamDetailsScreen(team: TeamModelDomain) {
        _event.emit(
            IFollowedScreenEvent.NavigateToTeamDetailsPage(team = team)
        )
    }

    private fun navigateToPlayerDetailsScreen(player: PlayerModelDomain) {
        _event.emit(
            IFollowedScreenEvent.NavigateToPlayerDetailsPage(player = player)
        )
    }

    private fun deleteElementFromDatabase(element: IStateModel) {
        viewModelScope.launch {
            when (element) {
                is GameModelDomain -> {
                    updateGameIsFollowedUseCase.invoke(game = element)
                }

                is PlayerModelDomain -> {
                    updatePlayerIsFollowedUseCase.invoke(player = element)
                }

                is TeamModelDomain -> {
                    updateTeamIsFollowedUseCase.invoke(team = element)
                }
            }
        }
    }

    private fun navigateToPreviousScreen() {
        _event.emit(
            IFollowedScreenEvent.NavigateToPreviousPage
        )
    }

}