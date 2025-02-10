package com.alenniboris.nba_app.presentation.test_moving_box

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun TestMovingBoxUi() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val parentBoxSize = 200.dp
        val childBoxSize = 30.dp

        Box(
            modifier = Modifier
                .size(parentBoxSize)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {

            var offsetX by remember { mutableStateOf(0f) }
            var offsetY by remember { mutableStateOf(0f) }

            Box(
                modifier = Modifier
                    .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                    .size(childBoxSize)
                    .background(Color.Red)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()

                            val maxOffset = ((parentBoxSize - childBoxSize) / 2).toPx()

                            offsetX =
                                (offsetX + dragAmount.x).coerceIn(-maxOffset, maxOffset)
                            offsetY =
                                (offsetY + dragAmount.y).coerceIn(-maxOffset, maxOffset)
                        }
                    }
            )
        }
    }
}
