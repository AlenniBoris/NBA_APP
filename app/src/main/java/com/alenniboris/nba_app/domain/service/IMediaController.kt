package com.alenniboris.nba_app.domain.service

import kotlinx.coroutines.flow.StateFlow

interface IMediaController {

    val playerState: StateFlow<AudioPlayerData>

    fun play(url: String)

    fun stop()

    fun updateData()

}