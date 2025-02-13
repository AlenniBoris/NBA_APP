package com.alenniboris.nba_app.data.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import com.alenniboris.nba_app.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class AudioPlayerService : LifecycleService() {

    private lateinit var mediaSession: MediaSession
    private val player: ExoPlayer by inject()
    private var updateJob: Job? = null

    override fun onCreate() {
        super.onCreate()
        mediaSession = MediaSession.Builder(this, player).build()
        startForeground(notificationId, createNotification())
        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                updateNotification()
            }
        })

        updateJob = lifecycleScope.launch {
            while (isActive) {
                updateNotification()
                delay(1_000)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSession.release()
        player.release()
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
            actionPlay -> player.play()
            actionPause -> player.pause()
        }
        updateNotification()
        return START_STICKY
    }

    private fun buildNotification(): Notification {

        val playPauseIntent = PendingIntent.getService(
            this,
            0,
            Intent(
                this,
                AudioPlayerService::class.java
            ).apply {
                action = if (player.isPlaying) actionPause else actionPlay
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val playPauseAction = NotificationCompat.Action(
            if (player.isPlaying) R.drawable.icon_video_to_stop else R.drawable.icon_video_to_play,
            if (player.isPlaying) "Stop" else "Play",
            playPauseIntent
        )

        val progress = ((player.currentPosition.toFloat() / player.duration) * 100).toInt()

        return NotificationCompat.Builder(this, "audio_player_channel")
            .setContentTitle("Playing Audio")
            .setSmallIcon(R.drawable.icon_video_to_play)
            .setContentText(getTextTime(player.currentPosition) + "/" + getTextTime(player.duration))
            .addAction(playPauseAction)
            .setProgress(100, progress, false)
            .build()

    }

    private fun updateNotification() {
        val nManager = getSystemService(NotificationManager::class.java)
        nManager.notify(notificationId, buildNotification())
    }

    override fun onBind(intent: Intent): IBinder? {
        return super.onBind(intent)
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