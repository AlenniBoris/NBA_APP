package com.alenniboris.nba_app.presentation.screens.settings.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemHorizontalPadding
import com.alenniboris.nba_app.presentation.uikit.theme.GameColumnItemShape
import com.alenniboris.nba_app.presentation.uikit.theme.categoryItemColor
import com.alenniboris.nba_app.presentation.uikit.theme.settingsCardHeight

@Composable
fun SettingsPagerItem(
    content: @Composable (modifier: Modifier) -> Unit,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(settingsCardHeight)
            .background(
                color = categoryItemColor,
                shape = GameColumnItemShape
            )
            .padding(GameColumnItemHorizontalPadding)
            .clickable {
                onClick()
            }
    ) {
        content(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}