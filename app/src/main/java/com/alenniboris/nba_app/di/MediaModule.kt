package com.alenniboris.nba_app.di

import androidx.media3.exoplayer.ExoPlayer
import com.alenniboris.nba_app.data.service.MediaControllerImpl
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.service.IMediaController
import com.alenniboris.nba_app.presentation.audio_player.AudioPlayerVM
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val MediaModule = module {

    single<ExoPlayer> { ExoPlayer.Builder(androidApplication()).build() }

    single<IMediaController> {
        MediaControllerImpl(
            apl = androidApplication(),
            player = get<ExoPlayer>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    viewModel<AudioPlayerVM> {
        AudioPlayerVM(
            mediaController = get<IMediaController>()
        )
    }

}