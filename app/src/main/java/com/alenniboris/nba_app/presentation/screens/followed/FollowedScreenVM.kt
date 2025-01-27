package com.alenniboris.nba_app.presentation.screens.followed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.nba_app.domain.manager.INbaApiManager
import com.alenniboris.nba_app.domain.model.api.nba.IStateModel
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
                            followedTeams = elements.map { it.toTeamModelDomain() }
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
            is IFollowedScreenUpdateIntent.proceedRemovingAction -> deleteElementFromDatabase(intent.element)
            is IFollowedScreenUpdateIntent.proceedOpeningElementDetailsScreenAction -> navigateToElementDetailsScreen(
                intent.element
            )

            is IFollowedScreenUpdateIntent.navigateToPreviousScreen -> navigateToPreviousScreen()
        }
    }

    private fun deleteElementFromDatabase(element: IStateModel) {
        viewModelScope.launch(Dispatchers.IO) {
            nbaApiManager.proceedElementIsFollowingUpdate(element)
        }
    }

    private fun navigateToElementDetailsScreen(element: IStateModel) {
        _event.emit(
            IFollowedScreenEvent.NavigateToElementDetailsPage(element)
        )
    }

    private fun navigateToPreviousScreen() {
        _event.emit(
            IFollowedScreenEvent.NavigateToPreviousPage
        )
    }

}