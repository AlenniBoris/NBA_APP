package com.alenniboris.nba_app.presentation.audio_player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.nba_app.domain.service.AudioPlayerData
import com.alenniboris.nba_app.domain.service.IMediaController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AudioPlayerVM(
    private val mediaController: IMediaController
) : ViewModel() {

    private val _state = MutableStateFlow(AudioPlayerData())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            mediaController.playerState.collect { data -> _state.update { data } }
        }
    }

    fun doAction(url: String) {
        if (_state.value.isPlaying) {
            mediaController.stop()
        } else {
            mediaController.play(url = url)
        }
    }

}