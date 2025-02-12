package com.alenniboris.nba_app.presentation.uikit.views.appVideoPlayer

import androidx.media3.exoplayer.ExoPlayer

sealed interface IAppVideoPlayerIntent {

    data class UpdateVideoDuration(val newDuration: Long) : IAppVideoPlayerIntent

    data class UpdateIsPlaying(val isPlaying: Boolean) : IAppVideoPlayerIntent

    data class UpdateIsControlsVisible(val isVisible: Boolean) : IAppVideoPlayerIntent

    data class UpdateIsBuffering(val isBuffering: Boolean) : IAppVideoPlayerIntent

    data class UpdateCurrentTime(val newValue: Float) : IAppVideoPlayerIntent

    data class UpdateIsSeeking(val newValue: Boolean) : IAppVideoPlayerIntent

    data class UpdateCurrentPlayer(val newPlayer: ExoPlayer?) : IAppVideoPlayerIntent

}