package com.alenniboris.nba_app.presentation.uikit.views.appVideoPlayer

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.presentation.uikit.theme.appTopBarElementsColor
import com.alenniboris.nba_app.presentation.uikit.theme.bodyStyle
import com.alenniboris.nba_app.presentation.uikit.views.AppEmptyScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
@Preview
fun AppVideoPlayer(
    modifier: Modifier = Modifier,
    videoUrl: String? = "",
    textString: String = "",
    textColor: Color = appTopBarElementsColor,
) {

    val videoPlayerVM = koinViewModel<AppVideoPlayerVM>()
    val state by videoPlayerVM.screenState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val proceedIntent by remember { mutableStateOf(videoPlayerVM::proceedIntent) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = textString,
            color = textColor,
            style = bodyStyle.copy(
                fontSize = 15.sp
            )
        )
        videoUrl?.let {

            val player = remember {
                state.player ?: ExoPlayer.Builder(context).build().apply {
                    setMediaItem(MediaItem.fromUri(Uri.parse(videoUrl)))
                    prepare()
                    videoPlayerVM.proceedIntent(IAppVideoPlayerIntent.UpdateCurrentPlayer(this))
                }
            }

            DisposableEffect(Unit) {
                onDispose {
                    player.release()
                }
            }

            LaunchedEffect(player) {
                snapshotFlow { state.isPlaying }.collect { isPlaying ->
                    if (isPlaying) player.play() else player.pause()
                }
            }

            LaunchedEffect(player) {
                player.addListener(object : Player.Listener {
                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        proceedIntent(IAppVideoPlayerIntent.UpdateIsPlaying(isPlaying))
                    }

                    override fun onPlaybackStateChanged(playbackState: Int) {
                        proceedIntent(
                            IAppVideoPlayerIntent.UpdateIsBuffering(
                                playbackState == Player.STATE_BUFFERING
                            )
                        )
                        if (playbackState == Player.STATE_READY) {
                            proceedIntent(IAppVideoPlayerIntent.UpdateVideoDuration(player.duration))
                        }
                    }
                })
            }

            LaunchedEffect(player) {
                while (true) {
                    if (!state.isSeeking) {
                        proceedIntent(IAppVideoPlayerIntent.UpdateCurrentTime(player.currentPosition.toFloat()))
                    }

                    delay(1_000)
                }
            }

            LaunchedEffect(state.isControlsVisible) {
                launch {
                    delay(3_000)
                    proceedIntent(IAppVideoPlayerIntent.UpdateIsControlsVisible(false))
                }
            }

            Box(Modifier.wrapContentHeight()) {

                AndroidView(
                    modifier = modifier
                        .clickable {
                            proceedIntent(
                                IAppVideoPlayerIntent.UpdateIsControlsVisible(
                                    true
                                )
                            )
                        },
                    factory = { context ->
                        PlayerView(context).apply {
                            this.player = player
                            useController = false
                            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                        }
                    },
                    update = { playerView -> playerView.player = player }
                )

                if (state.isControlsVisible) {
                    Column(
                        modifier = Modifier.align(Alignment.BottomCenter)
                    ) {
                        if (state.isBuffering) {
                            CircularProgressIndicator(
                                color = Color.White
                            )
                        } else {
                            Icon(
                                painter = painterResource(if (state.isPlaying) R.drawable.icon_video_to_stop else R.drawable.icon_video_to_play),
                                contentDescription = "",
                                tint = Color.White,
                                modifier = Modifier.clickable {
                                    proceedIntent(
                                        IAppVideoPlayerIntent.UpdateIsPlaying(
                                            !state.isPlaying
                                        )
                                    )
                                }
                            )
                        }

                        Slider(
                            value = state.currentTime,
                            onValueChange = {
                                proceedIntent(IAppVideoPlayerIntent.UpdateIsSeeking(true))
                                proceedIntent(IAppVideoPlayerIntent.UpdateCurrentTime(it))
                            },
                            onValueChangeFinished = {
                                player.seekTo(state.currentTime.toLong())
                                proceedIntent(IAppVideoPlayerIntent.UpdateIsSeeking(false))
                            },
                            valueRange = 0f..state.videoDuration.toFloat()
                        )

                        Text(
                            text = state.timeText,
                            color = Color.White
                        )
                    }
                }


            }

        } ?: AppEmptyScreen(
            modifier = modifier
        )
    }
}