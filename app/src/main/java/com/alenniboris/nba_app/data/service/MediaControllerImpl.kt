package com.alenniboris.nba_app.data.service

import android.app.Application
import android.content.Intent
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.service.AudioPlayerData
import com.alenniboris.nba_app.domain.service.IMediaController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MediaControllerImpl(
    private val apl: Application,
    private val player: ExoPlayer,
    private val dispatchers: IAppDispatchers
) : IMediaController {

    private val _playerState = MutableStateFlow(AudioPlayerData())
    override val playerState: StateFlow<AudioPlayerData> = _playerState.asStateFlow()

    private val scope: CoroutineScope = CoroutineScope(dispatchers.Main + Job())

    init {
        scope.launch {
            while (isActive) {
                updateData()
                delay(1_000)
            }
        }
    }

    override fun startPlayer() {
        player.play()
    }

    override fun pausePlayer() {
        player.pause()
    }

    override fun clearMediaProcess() {
        player.clearMediaItems()
        player.release()
        val intent = Intent(apl, AudioPlayerService::class.java)
        apl.stopService(intent)
    }

    override fun play(url: String) {
        val newMediaItem = MediaItem.fromUri(url)
        if (player.currentMediaItem != newMediaItem) {
            player.setMediaItem(newMediaItem)
            player.prepare()
        }
        val intent = Intent(apl, AudioPlayerService::class.java)
        player.play()
        apl.startService(intent)
    }

    override fun stop() {
        player.pause()
        val intent = Intent(apl, AudioPlayerService::class.java)
        apl.stopService(intent)
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