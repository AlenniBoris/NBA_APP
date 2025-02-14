package com.alenniboris.nba_app.data.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.domain.service.IMediaController
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class AudioPlayerService : LifecycleService() {

    private var updateJob: Job? = null
    private val controller by inject<IMediaController>()

    override fun onCreate() {
        super.onCreate()
        startForeground(notificationId, createNotification())

        updateJob = lifecycleScope.launch {
            while (isActive) {
                updateNotification()
                delay(1_000)
            }
        }
    }

    override fun onDestroy() {
        updateJob?.cancel()
        super.onDestroy()
    }

    private fun createNotification(): Notification {
        val channelId = "audio_player_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, "audio player", NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
        return buildNotification()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        when (intent?.action) {
            actionPlay -> controller.startPlayer()
            actionPause -> controller.pausePlayer()
        }
        updateNotification()
        return START_STICKY
    }

    private fun buildNotification(): Notification {

        val isPlayerPlaying = controller.playerState.value.isPlaying

        val playPauseIntent = PendingIntent.getService(
            this,
            0,
            Intent(
                this,
                AudioPlayerService::class.java
            ).apply {
                action = if (isPlayerPlaying) actionPause else actionPlay
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val playPauseAction = NotificationCompat.Action(
            if (isPlayerPlaying) R.drawable.icon_video_to_stop else R.drawable.icon_video_to_play,
            if (isPlayerPlaying) "Stop" else "Play",
            playPauseIntent
        )

        val currentTime = controller.playerState.value.currentTime
        val audioDuration = controller.playerState.value.audioDuration

        val progress = ((currentTime.toFloat() / audioDuration) * 100).toInt()

        return NotificationCompat.Builder(this, "audio_player_channel")
            .setContentTitle("Playing Audio")
            .setSmallIcon(R.drawable.icon_video_to_play)
            .setContentText(
                getTextTime(currentTime) + "/" + getTextTime(audioDuration)
            )
            .addAction(playPauseAction)
            .setProgress(100, progress, false)
            .build()

    }

    private fun updateNotification() {
        val nManager = getSystemService(NotificationManager::class.java)
        nManager.notify(notificationId, buildNotification())
    }

    private fun getTextTime(time: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(time)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(time)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(time) % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    companion object {
        val notificationId = 1
        val actionPause = "ACTION_PAUSE"
        val actionPlay = "ACTION_PLAY"
    }

}