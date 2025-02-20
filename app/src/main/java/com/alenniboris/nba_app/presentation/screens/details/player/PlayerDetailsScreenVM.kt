package com.alenniboris.nba_app.presentation.screens.details.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.usecase.player.IGetFollowedPlayersUseCase
import com.alenniboris.nba_app.domain.usecase.player.IGetPlayerDataByIdUseCase
import com.alenniboris.nba_app.domain.usecase.player.IGetPlayerStatisticsInSeasonUseCase
import com.alenniboris.nba_app.domain.usecase.player.IUpdatePlayerIsFollowedUseCase
import com.alenniboris.nba_app.domain.usecase.seasons.IGetSeasonsUseCase
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

class PlayerDetailsScreenVM(
    private val getFollowedPlayersUseCase: IGetFollowedPlayersUseCase,
    private val getPlayerDataByIdUseCase: IGetPlayerDataByIdUseCase,
    private val getSeasonsUseCase: IGetSeasonsUseCase,
    private val updatePlayerIsFollowedUseCase: IUpdatePlayerIsFollowedUseCase,
    private val getPlayerStatisticsInSeasonUseCase: IGetPlayerStatisticsInSeasonUseCase,
    private val playerId: Int,
) : ViewModel() {

    private val _screenState = MutableStateFlow(
        PlayerDetailsScreenState(
            player = PlayerModelDomain(id = playerId)
        )
    )
    val screenState = _screenState.asStateFlow()

    private val _event = SingleFlowEvent<IPlayerDetailsScreenEvent>(viewModelScope)
    val event = _event.flow

    private var _statisticsJob: Job? = null


    init {
        reloadDataForPlayer()
    }


    init {
        viewModelScope.launch {
            getFollowedPlayersUseCase.followedFlow
                .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
                .distinctUntilChanged()
                .collect { games ->
                    val ids = games.map { it.playerId }.toList()
                    _screenState.update { state ->
                        state.copy(
                            player = state.player.copy(
                                isFollowed = ids.contains(state.player.id)
                            )
                        )
                    }
                }
        }
    }

    private fun reloadDataForPlayer() {
        viewModelScope.launch {
            _screenState.update { it.copy(isPlayerDataLoading = true) }

            when (
                val res = getPlayerDataByIdUseCase.invoke(_screenState.value.player.id)
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update {
                        it.copy(
                            player = res.result,
                            isPlayerStatisticsReloadedWithError = false
                        )
                    }
                    loadSeasons()
                }

                is CustomResultModelDomain.Error -> {
                    _screenState.update { it.copy(isPlayerStatisticsReloadedWithError = true) }
                    _event.emit(
                        IPlayerDetailsScreenEvent.ShowToastMessage(res.exception.toUiMessageString())
                    )
                }
            }

            _screenState.update { it.copy(isPlayerDataLoading = false) }
        }
    }

    private fun loadSeasons() {
        viewModelScope.launch {
            _screenState.update { it.copy(isSeasonsLoading = true) }

            when (
                val seasonsResult = getSeasonsUseCase.invoke()
            ) {
                is CustomResultModelDomain.Success -> {
                    _screenState.update { state ->
                        state.copy(
                            listOfSeasons = seasonsResult.result,
                            selectedSeason = seasonsResult.result.firstOrNull()
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    (seasonsResult.exception as? NbaApiExceptionModelDomain)?.let {
                        _event.emit(IPlayerDetailsScreenEvent.ShowToastMessage(it.toUiMessageString()))
                    }
                }
            }

            _screenState.update { it.copy(isSeasonsLoading = false) }
        }
    }

    fun proceedIntentAction(intent: IPlayerDetailsScreenUpdateIntent) = when (intent) {
        is IPlayerDetailsScreenUpdateIntent.NavigateToGameDetailsScreen -> navigateToGameDetailsScreen(
            intent.gameId
        )

        is IPlayerDetailsScreenUpdateIntent.NavigateToPreviousScreen -> navigateToPreviousScreen()
        is IPlayerDetailsScreenUpdateIntent.ProceedIsFollowedAction -> proceedIsFollowedAction()
        is IPlayerDetailsScreenUpdateIntent.UpdateSelectedSeason -> updateSelectedSeason(intent.newSeason)
    }

    private fun navigateToGameDetailsScreen(gameId: Int) {
        _event.emit(
            IPlayerDetailsScreenEvent.NavigateToGameDetailsScreen(
                game = GameModelDomain(id = gameId)
            )
        )
    }

    private fun navigateToPreviousScreen() {
        _event.emit(IPlayerDetailsScreenEvent.NavigateToPreviousPage)
    }

    private fun proceedIsFollowedAction() {
        viewModelScope.launch {
            updatePlayerIsFollowedUseCase.invoke(player = _screenState.value.player)
        }
    }

    private fun updateSelectedSeason(newSelectedSeason: SeasonModelDomain) {
        val isValueTheSame = _screenState.value.selectedSeason == newSelectedSeason
        _screenState.update { state ->
            state.copy(
                selectedSeason = if (!isValueTheSame) newSelectedSeason else null,
            )
        }
        if (!isValueTheSame) {
            loadPlayersStatisticsForSeason()
        } else {
            _statisticsJob?.cancel()
            _screenState.update { it.copy(playerStatistics = emptyList()) }
        }
    }

    private fun loadPlayersStatisticsForSeason() {
        _statisticsJob?.cancel()
        _statisticsJob = viewModelScope.launch {
            _screenState.value.selectedSeason?.let { season ->
                _screenState.update { it.copy(isPlayerStatisticsLoading = true) }
                when (
                    val res =
                        getPlayerStatisticsInSeasonUseCase.invoke(
                            season = season,
                            player = _screenState.value.player
                        )
                ) {
                    is CustomResultModelDomain.Success -> {
                        _screenState.update {
                            it.copy(playerStatistics = res.result)
                        }
                    }

                    is CustomResultModelDomain.Error -> {
                        _event.emit(
                            IPlayerDetailsScreenEvent.ShowToastMessage(res.exception.toUiMessageString())
                        )
                    }
                }
                _screenState.update { it.copy(isPlayerStatisticsLoading = false) }
            }
        }
    }

}