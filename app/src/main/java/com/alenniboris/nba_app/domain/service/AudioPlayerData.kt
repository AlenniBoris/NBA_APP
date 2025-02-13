package com.alenniboris.nba_app.domain.service

data class AudioPlayerData(
    val isPlaying: Boolean = false,
    val currentTime: Long = 0L,
    val audioDuration: Long = 0L,
    val isSongBuffering: Boolean = false,
)
