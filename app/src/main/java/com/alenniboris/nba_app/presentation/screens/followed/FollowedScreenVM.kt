package com.alenniboris.nba_app.presentation.screens.followed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.nba_app.domain.manager.INbaApiManager
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.IStateModel
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.entity.api.nba.toGameModelDomain
import com.alenniboris.nba_app.domain.model.entity.api.nba.toPlayerModelDomain
import com.alenniboris.nba_app.domain.model.entity.api.nba.toTeamModelDomain
import com.alenniboris.nba_app.domain.utils.SingleFlowEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FollowedScreenVM(
    private val nbaApiManager: INbaApiManager
) : ViewModel() {

    private val _screenState = MutableStateFlow(FollowedState())
    val screenState = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IFollowedScreenEvent>(viewModelScope)
    val event = _event.flow

    init {
        viewModelScope.launch(Dispatchers.IO) {
            nbaApiManager.followedGames
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
            nbaApiManager.followedTeams
                .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
                .distinctUntilChanged()
                .collect { elements ->
                    _screenState.update {
                        it.copy(
                            followedTeams = elements.mapNotNull { it.toTeamModelDomain() }
                        )
                    }
                }
        }
    }

    init {
        viewModelScope.launch {
            nbaApiManager.followedPlayers
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
        viewModelScope.launch(Dispatchers.IO) {
            nbaApiManager.proceedElementIsFollowingUpdate(element)
        }
    }

    private fun navigateToPreviousScreen() {
        _event.emit(
            IFollowedScreenEvent.NavigateToPreviousPage
        )
    }

}