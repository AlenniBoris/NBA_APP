package com.alenniboris.nba_app.presentation.uikit.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.alenniboris.nba_app.presentation.model.ActionImplementedUiModel
import com.alenniboris.nba_app.presentation.uikit.theme.CustomDropdownMenuBoxMinHeight
import com.alenniboris.nba_app.presentation.uikit.theme.CustomDropdownMenuItemMargin
import com.alenniboris.nba_app.presentation.uikit.theme.CustomDropdownMenuItemPadding
import com.alenniboris.nba_app.presentation.uikit.theme.CustomDropdownMenuItemTextSize
import com.alenniboris.nba_app.presentation.uikit.theme.DropDownMenuItemCornerShape
import com.alenniboris.nba_app.presentation.uikit.theme.FloatingActionButtonWidth
import com.alenniboris.nba_app.presentation.uikit.theme.bodyStyle
import com.alenniboris.nba_app.presentation.uikit.theme.dropdownItemColor
import com.alenniboris.nba_app.presentation.uikit.theme.dropdownItemTextColor
import kotlinx.coroutines.delay

@Composable
fun AppDropdownMenu(
    modifier: Modifier = Modifier,
    isMenuVisible: Boolean = false,
    onDismiss: () -> Unit = {},
    timeForCloseAnimateVisible: Long = 400,
    enter: EnterTransition = fadeIn() + slideInVertically(
        animationSpec = tween(1000),
        initialOffsetY = { -it }),
    exit: ExitTransition = fadeOut() + slideOutVertically(
        animationSpec = tween(timeForCloseAnimateVisible.toInt() + 200),
        targetOffsetY = { -it }),
    listOfItems: List<ActionImplementedUiModel> = emptyList(),
) {

    var isAnimateVisible by remember { mutableStateOf(false) }
    LaunchedEffect(isMenuVisible) {
        delay(if (isMenuVisible) 100 else timeForCloseAnimateVisible)
        isAnimateVisible = isMenuVisible
    }

    if (isMenuVisible || isAnimateVisible) {
        Box(
            modifier = modifier
        ) {
            Popup(onDismissRequest = onDismiss, properties = PopupProperties(focusable = true)) {
                Box(modifier = Modifier.heightIn(min = CustomDropdownMenuBoxMinHeight)) {
                    AnimatedVisibility(
                        visible = isMenuVisible && isAnimateVisible,
                        enter = enter,
                        exit = exit
                    ) {
                        Column {
                            listOfItems.forEach { item ->
                                CustomDropdownMenuItem(
                                    text = item.name,
                                    onClick = item.onClick
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
@Preview
private fun CustomDropdownMenuItem(
    text: String = "Category text",
    onClick: () -> Unit = {}
) {

    Box(
        modifier = Modifier
            .padding(CustomDropdownMenuItemMargin)
            .background(
                color = dropdownItemColor,
                shape = DropDownMenuItemCornerShape
            )
            .border(
                width = FloatingActionButtonWidth,
                color = dropdownItemTextColor,
                shape = DropDownMenuItemCornerShape
            )
            .padding(CustomDropdownMenuItemPadding)
            .clickable { onClick() }
    ) {
        Text(
            text = text,
            color = dropdownItemTextColor,
            style = bodyStyle.copy(fontSize = CustomDropdownMenuItemTextSize)
        )
    }

}

@Composable
@Preview
private fun PreviewUi() {
    AppDropdownMenu(
        isMenuVisible = true,
        listOfItems = listOf(
            ActionImplementedUiModel("1", {}),
            ActionImplementedUiModel("1", {}),
            ActionImplementedUiModel("1", {})
        )
    )
}