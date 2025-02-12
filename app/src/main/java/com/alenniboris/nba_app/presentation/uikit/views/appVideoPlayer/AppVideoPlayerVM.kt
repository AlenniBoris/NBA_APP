package com.alenniboris.nba_app.presentation.uikit.views.appVideoPlayer

import androidx.lifecycle.ViewModel
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.concurrent.TimeUnit

data class VideoPlayerState(
    val player: ExoPlayer? = null,
    val isPlaying: Boolean = false,
    val videoDuration: Long = 0L,
    val isControlsVisible: Boolean = true,
    val isBuffering: Boolean = false,
    val isSeeking: Boolean = false,
    val currentTime: Float = 0f

) {
    private val currentTimeText: String
        get() {
            val hours = TimeUnit.MILLISECONDS.toHours(currentTime.toLong())
            val minutes = TimeUnit.MILLISECONDS.toMinutes(currentTime.toLong())
            val seconds = TimeUnit.MILLISECONDS.toSeconds(currentTime.toLong()) % 60
            return String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }

    private val videoDurationText: String
        get() {
            val hours = TimeUnit.MILLISECONDS.toHours(videoDuration)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(videoDuration)
            val seconds = TimeUnit.MILLISECONDS.toSeconds(videoDuration) % 60
            return String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }

    val timeText = "$currentTimeText / $videoDurationText"
}

class AppVideoPlayerVM : ViewModel() {


    private val _screenState = MutableStateFlow(VideoPlayerState())
    val screenState = _screenState.asStateFlow()

    fun proceedIntent(intent: IAppVideoPlayerIntent) = when (intent) {
        is IAppVideoPlayerIntent.UpdateIsPlaying -> updateIsPlaying(intent.isPlaying)
        is IAppVideoPlayerIntent.UpdateVideoDuration -> updateDurationTime(intent.newDuration)
        is IAppVideoPlayerIntent.UpdateIsControlsVisible -> updateIsControlsVisible(intent.isVisible)
        is IAppVideoPlayerIntent.UpdateIsBuffering -> updateIsVideoLoading(intent.isBuffering)
        is IAppVideoPlayerIntent.UpdateCurrentTime -> updateCurrentTime(intent.newValue)
        is IAppVideoPlayerIntent.UpdateIsSeeking -> updateIsSeeking(intent.newValue)
        is IAppVideoPlayerIntent.UpdateCurrentPlayer -> createExoPlayerForUrl(intent.newPlayer)
    }

    private fun createExoPlayerForUrl(newPlayer: ExoPlayer?) {
        _screenState.update { it.copy(player = newPlayer) }
    }

    private fun updateIsSeeking(newValue: Boolean) {
        _screenState.update { it.copy(isSeeking = newValue) }
    }

    private fun updateCurrentTime(newValue: Float) {
        _screenState.update { it.copy(currentTime = newValue) }
    }

    private fun updateIsVideoLoading(isLoading: Boolean) {
        _screenState.update { it.copy(isBuffering = isLoading) }
    }

    private fun updateIsControlsVisible(isVisible: Boolean) {
        _screenState.update { it.copy(isControlsVisible = isVisible) }
    }

    private fun updateDurationTime(newDuration: Long) {
        _screenState.update { it.copy(videoDuration = newDuration) }
    }

    private fun updateIsPlaying(isPlaying: Boolean) {
        _screenState.update { it.copy(isPlaying = isPlaying) }
    }

}