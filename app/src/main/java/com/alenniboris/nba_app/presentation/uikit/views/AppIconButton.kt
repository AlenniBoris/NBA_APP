package com.alenniboris.nba_app.presentation.uikit.views

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.nba_app.presentation.uikit.theme.appColor

@Composable
@Preview
fun AppIconButton(
    isAnimated: Boolean = false,
    isReplaceable: Boolean = false,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {},
    iconPainter: Painter = ColorPainter(appColor),
    replacementPainter: Painter = ColorPainter(appColor),
    tint: Color = Color(0xff050300),
    contentDescription: String = ""
) {

    var rotationAngle by remember { mutableFloatStateOf(0f) }
    val rotation by animateFloatAsState(targetValue = rotationAngle, label = "")
    var isClockwise by remember { mutableStateOf(true) }
    var painter by remember { mutableStateOf(iconPainter) }
    var isIconReplaced by remember { mutableStateOf(false) }

    IconButton(
        onClick = {
            if (isAnimated) {
                if (isClockwise) {
                    rotationAngle += 360f
                } else {
                    rotationAngle -= 360f
                }
                isClockwise = !isClockwise
            }
            if (isReplaceable) {
                painter = if (isIconReplaced) iconPainter else replacementPainter
                isIconReplaced = !isIconReplaced
            }
            onClick()
        },
        modifier = Modifier.clip(CircleShape),
        enabled = isEnabled
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.graphicsLayer(rotationZ = if (isAnimated) rotation else 0f)
        )
    }
}