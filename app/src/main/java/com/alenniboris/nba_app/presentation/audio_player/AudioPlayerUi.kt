package com.alenniboris.nba_app.presentation.audio_player

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import java.util.concurrent.TimeUnit

@Composable
fun AudioPlayerUi() {

    val viewModel = koinViewModel<AudioPlayerVM>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val action by remember { mutableStateOf(viewModel::doAction) }

    Column {
        Text("Time = ${getTextTime(state.currentTime) + "/" + getTextTime(state.audioDuration)}")
        Button({ action("https://github.com/SergLam/Audio-Sample-files/raw/master/sample.mp3") }) {
            Text(
                text = if (state.isPlaying) "Stop" else "Play"
            )
        }
    }

}

private fun getTextTime(time: Long): String {
    val hours = TimeUnit.MILLISECONDS.toHours(time)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(time)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(time) % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}