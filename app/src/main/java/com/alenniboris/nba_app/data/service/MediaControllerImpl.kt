package com.alenniboris.nba_app.data.service

import android.app.Application
import android.content.Intent
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.alenniboris.nba_app.domain.service.AudioPlayerData
import com.alenniboris.nba_app.domain.service.IMediaController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MediaControllerImpl(
    private val apl: Application,
    private val player: ExoPlayer
) : IMediaController {


    private val _playerState = MutableStateFlow(AudioPlayerData())
    override val playerState: StateFlow<AudioPlayerData> = _playerState

    override fun play(url: String) {
        val newMediaItem = MediaItem.fromUri(url)
        if (player.currentMediaItem != newMediaItem) {
            val intent = Intent(apl, AudioPlayerService::class.java).apply {
                putExtra("url", url)
            }
            player.setMediaItem(newMediaItem)
            player.prepare()
            player.play()
            apl.startService(intent)
        } else {
            player.play()
        }
        updateData()
    }

    override fun stop() {
        player.pause()
        updateData()
    }

    override fun updateData() {
        _playerState.update {
            it.copy(
                isPlaying = player.isPlaying,
                currentTime = player.currentPosition,
                audioDuration = if (player.currentMediaItem != null) player.duration else 0L
            )
        }
    }

}