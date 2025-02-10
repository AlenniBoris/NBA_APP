package com.alenniboris.nba_app.presentation.test_moving_box

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
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

        var parentSize by remember { mutableStateOf(IntSize.Zero) }
        var parentCoords by remember { mutableStateOf(IntOffset.Zero) }

        Box(
            modifier = Modifier
                .width(200.dp)
                .height(250.dp)
                .background(Color.Black)
                .onGloballyPositioned { position ->
                    parentSize = position.size
                    parentCoords = position
                        .positionInRoot()
                        .toIntOffset()
                },
            contentAlignment = Alignment.Center
        ) {

            var childSize by remember { mutableStateOf(IntSize.Zero) }
            var childCoords by remember { mutableStateOf(IntOffset.Zero) }

            var offset by remember { mutableStateOf(IntOffset.Zero) }

            Box(
                modifier = Modifier
                    .padding(top = 50.dp, start = 10.dp)
                    .background(Color.Green)
                    .onGloballyPositioned { position ->
                        childSize = position.size
                        childCoords = position
                            .positionInRoot()
                            .toIntOffset()
                    }
            ) {
                Box(
                    modifier = Modifier
                        .offset { offset }
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                val newOffset = offset + dragAmount.toIntOffset()

                                val newPosition = childCoords + newOffset

                                if (
                                    (newPosition.x > parentCoords.x) &&
                                    (newPosition.x + childSize.width < parentCoords.x + parentSize.width) &&
                                    (newPosition.y > parentCoords.y) &&
                                    (newPosition.y + childSize.height < parentCoords.y + parentSize.height)
                                ) {
                                    offset = newOffset
                                }
                            }
                        }
                        .width(45.dp)
                        .height(60.dp)
                        .background(Color.Red)
                )
            }
        }
    }

}

fun Offset.toIntOffset(): IntOffset {
    return IntOffset(x = this.x.roundToInt(), y = this.y.roundToInt())
}