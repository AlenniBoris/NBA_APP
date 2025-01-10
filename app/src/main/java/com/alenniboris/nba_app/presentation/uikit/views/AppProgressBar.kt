package com.alenniboris.nba_app.presentation.uikit.views

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.presentation.uikit.theme.TopBarDropdownMenuOpeningButton
import com.alenniboris.nba_app.presentation.uikit.theme.appTopBarElementsColor

@Composable
@Preview
fun AppProgressBar(
    modifier: Modifier = Modifier,
    iconTint: Color = appTopBarElementsColor
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = stringResource(R.string.progress_bar_animation_description)
    )


    Box(
        modifier = modifier
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer(rotationZ = rotationAngle),
            painter = painterResource(TopBarDropdownMenuOpeningButton),
            contentDescription = stringResource(R.string.progress_bar_description),
            tint = iconTint
        )
    }
}